package sigint.fisint

import sigint.audit.LogManager
import sigint.core.EventBus

class FisintAnalyzer(private val profiles: List<FisintProfile>) {

    fun analyzeTraffic(observedCadenceMs: Long, detectedBand: String) {
        val match = profiles.find { 
            observedCadenceMs in (it.cadenceMs - 10)..(it.cadenceMs + 10) && it.frequencyBand == detectedBand 
        }

        if (match != null) {
            LogManager.info("FISINT_MATCH: Flux technique identifié -> ${match.name}")
            EventBus.publish("FISINT_DETECTION", mapOf(
                "type" to match.name,
                "period" to observedCadenceMs,
                "priority" to match.threatLevel
            ))
        } else {
            LogManager.info("FISINT_SCAN: Transmission périodique détectée ($observedCadenceMs ms) - Profil inconnu.")
        }
    }
}
