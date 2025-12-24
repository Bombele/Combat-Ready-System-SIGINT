package sigint.comint

import sigint.audit.LogManager
import sigint.core.EventBus

class ComintAnalyzer(private val decoder: ComintDecoder) {

    fun processSignal(frequencyMHz: Double, snrDb: Double, rawIqSize: Int) {
        val profile = decoder.selectProfile(frequencyMHz, snrDb)
        
        if (profile != null) {
            LogManager.info("COMINT_MATCH: Signal détecté sur ${frequencyMHz}MHz (Profil: ${profile.name})")
            
            // On publie l'événement en interne
            EventBus.publish("COMINT_DETECTION", mapOf(
                "freq" to frequencyMHz,
                "type" to profile.name,
                "modulation" to profile.modulation
            ))
        } else if (snrDb > 20.0) {
            LogManager.warn("COMINT_UNKNOWN: Signal fort non identifié sur ${frequencyMHz}MHz")
        }
    }
}
