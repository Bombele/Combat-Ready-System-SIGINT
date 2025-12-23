
package core.security

import java.io.File
import java.security.SecureRandom
import kotlin.system.exitProcess

// Modes opérationnels
enum class MissionMode { Fallback, LowPower, SilentOps, Evidence }

// Résultat des vérifications
data class SecurityStatus(
    val inAuthorizedZone: Boolean,
    val distressKeyEntered: Boolean,
    val tamperDetected: Boolean
)

object GeofenceManager {
    // Charge un polygone de mission depuis core/security/active_geofence.poly
    fun isInAuthorizedZone(lat: Double, lon: Double): Boolean {
        // TODO: parser le .poly et effectuer point-in-polygon
        // Placeholder: toujours vrai tant que pas implémenté
        return true
    }
}

object KeyVault {
    private val secureRandom = SecureRandom()
    private val keyFiles = listOf(
        "data/keys/master.key",
        "data/keys/session.key"
    )

    fun shredKeys(): Boolean {
        var ok = true
        keyFiles.forEach { path ->
            val f = File(path)
            if (f.exists()) {
                // Overwrite puis delete (best-effort)
                val bytes = ByteArray(f.length().toInt())
                secureRandom.nextBytes(bytes)
                try {
                    f.writeBytes(bytes)
                    f.delete()
                } catch (e: Exception) { ok = false }
            }
        }
        return ok
    }
}

object SensitiveStore {
    private val sensitivePaths = listOf(
        "data/signatures/",
        "core/audit/logs/",
        "data/reports/"
    )

    fun wipeAll(): Boolean {
        var ok = true
        sensitivePaths.forEach { path ->
            try {
                val f = File(path)
                if (f.exists()) {
                    f.walkBottomUp().forEach { it.delete() }
                }
            } catch (e: Exception) { ok = false }
        }
        return ok
    }
}

object MissionLogger {
    private val logDir = File("core/audit/logs")
    init { if (!logDir.exists()) logDir.mkdirs() }

    fun critical(event: String) {
        val f = File(logDir, "mission.log")
        f.appendText("[CRITICAL] $event | ts=${System.currentTimeMillis()}\n")
        // TODO: signature et hash (Evidence Mode)
    }

    fun info(event: String) {
        val f = File(logDir, "mission.log")
        f.appendText("[INFO] $event | ts=${System.currentTimeMillis()}\n")
    }
}

class TacticalWipeManager(
    private val mode: MissionMode
) {
    // Hooks externes (UI, capteurs, clavier détresse)
    fun evaluateStatus(lat: Double, lon: Double, distressKeyEntered: Boolean, tamperDetected: Boolean): SecurityStatus {
        val inZone = GeofenceManager.isInAuthorizedZone(lat, lon)
        return SecurityStatus(inZone, distressKeyEntered, tamperDetected)
    }

    fun enforce(status: SecurityStatus) {
        val mustWipe = (!status.inAuthorizedZone) || status.distressKeyEntered || status.tamperDetected
        if (mustWipe) {
            MissionLogger.critical("PANIC_WIPE_TRIGGERED: inZone=${status.inAuthorizedZone}, distress=${status.distressKeyEntered}, tamper=${status.tamperDetected}")
            val keysOk = KeyVault.shredKeys()
            val dataOk = SensitiveStore.wipeAll()
            MissionLogger.critical("PANIC_WIPE_RESULT: keys=$keysOk, data=$dataOk")
            // Option: kill process ou basculer en Safe Mode
            exitProcess(0)
        } else {
            MissionLogger.info("SECURE_STATUS_OK: inZone=${status.inAuthorizedZone}")
        }
    }
}