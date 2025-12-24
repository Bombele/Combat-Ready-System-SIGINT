package sigint.audit

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

/**
 * SRC - MissionReportGenerator
 * Génère le bilan final de l'opération avec signature d'intégrité HMAC.
 */
object MissionReportGenerator {

    private val mapper = jacksonObjectMapper()
    private val sdf = SimpleDateFormat("yyyyMMdd_HHmm")

    fun generateFinalReport(missionId: String) {
        println("[REPORT] Compilation des données de mission : $missionId...")

        // Compilation des statistiques depuis les logs
        val reportData = mutableMapOf<String, Any>(
            "mission_metadata" to mapOf(
                "id" to missionId,
                "date" to sdf.format(Date()),
                "status" to "COMPLETED"
            ),
            "intelligence_summary" to mapOf(
                "comint_detections" to countOccurrences("COMINT_MATCH"),
                "elint_detections" to countOccurrences("ELINT_MATCH"),
                "fisint_detections" to countOccurrences("FISINT_MATCH")
            ),
            "threat_log" to extractRecentAlerts()
        )

        val reportPath = "data/audit/REPORT_${missionId}.json"
        
        // Utilisation de la clé secrète de mission pour signer le rapport
        val missionKey = File("data/keys/master.key").readBytes()
        
        val success = AuditExport.writeJson(reportPath, reportData, missionKey)

        if (success) {
            println("✅ Rapport AAR généré et signé : $reportPath")
        } else {
            println("❌ Échec de la génération du rapport.")
        }
    }

    private fun countOccurrences(pattern: String): Int {
        val logFile = File("data/logs/sigint_module.log")
        if (!logFile.exists()) return 0
        return logFile.readLines().count { it.contains(pattern) }
    }

    private fun extractRecentAlerts(): List<String> {
        val logFile = File("data/logs/sigint_module.log")
        if (!logFile.exists()) return emptyList()
        return logFile.readLines().filter { it.contains("MATCH") }.takeLast(20)
    }
}
