package sigint.comint

import sigint.audit.LogManager
import sigint.audit.AuditExport

object ComintAudit {
    fun logDetection(profile: ComintProfile, freq: Double) {
        val entry = mapOf("module" to "COMINT", "profile" to profile.name, "freq" to freq)
        LogManager.info("AUDIT_COMINT: Record stored for $freq MHz")
        AuditExport.writeJson("data/audit/comint_${System.currentTimeMillis()}.json", entry, null)
    }
}
