package core.audit

import java.io.File
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * SRC - MissionLogger
 * Journalise les événements critiques et assure l’intégrité des logs.
 */
object MissionLogger {

    private val logDir = File("core/audit/logs")
    private val logFile = File(logDir, "mission.log")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    // Clé secrète locale pour HMAC (Evidence Mode)
    private val secretKey = "FARDC_SECRET_KEY_2025".toByteArray()

    init {
        if (!logDir.exists()) logDir.mkdirs()
        if (!logFile.exists()) logFile.createNewFile()
    }

    private fun timestamp(): String = dateFormat.format(Date())

    private fun hmacSha256(data: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(secretKey, "HmacSHA256"))
        val hash = mac.doFinal(data.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    private fun sha256(data: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(data.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    fun info(event: String) {
        val entry = "[INFO] ${timestamp()} :: $event"
        append(entry)
    }

    fun warning(event: String) {
        val entry = "[WARNING] ${timestamp()} :: $event"
        append(entry)
    }

    fun critical(event: String) {
        val entry = "[CRITICAL] ${timestamp()} :: $event"
        append(entry)
    }

    private fun append(entry: String) {
        val signature = hmacSha256(entry)
        val integrity = sha256(entry)
        logFile.appendText("$entry | sig=$signature | hash=$integrity\n")
    }
}