package sigint.comint

import sigint.audit.LogManager
import sigint.core.EventBus

class ComintAnalyzer(private val decoder: ComintDecoder) {

    fun processSignal(frequencyMHz: Double, snrDb: Double) {
        val profile = decoder.selectProfile(frequencyMHz, snrDb)
        
        if (profile != null) {
            LogManager.info("COMINT_MATCH: Signal détecté sur ${frequencyMHz}MHz (Profil: ${profile.name})")
            
            // On publie l'événement sur le bus interne
            EventBus.publish("COMINT_DETECTION", mapOf(
                "freq" to frequencyMHz,
                "type" to profile.name,
                "modulation" to profile.modulation,
                "timestamp" to System.currentTimeMillis()
            ))
        } else if (snrDb > 25.0) {
            LogManager.warn("COMINT_UNKNOWN: Signal fort non identifié sur ${frequencyMHz}MHz")
        }
    }
}
// Extrait de l'intégration dans l'Analyzer
fun processCapture(buffer: ByteArray) {
    val snr = ComintUtils.estimateSnr(buffer)
    val power = ComintUtils.estimatePowerDbm(buffer)

    if (snr > 12.0) { // Seuil de détection tactique
        LogManager.info("DETECTED: Signal identifié à ${String.format("%.2f", power)} dBm (SNR: ${String.format("%.2f", snr)} dB)")
        // Lancer le décodage...
    }
}
class ComintAnalyzer(private val decoder: ComintDecoder) {

    fun handleIncomingSignal(buffer: ByteArray, freqMHz: Double) {
        val snr = ComintUtils.estimateSnr(buffer)

        if (snr > 15.0) { // Signal assez clair pour être décodé
            val result = decoder.decode(buffer, "NFM")
            
            if (result.startsWith("DATA_DECODED")) {
                LogManager.warn("ALERTE_INTEL: Texte intercepté sur $freqMHz MHz -> $result")
                // Envoi automatique vers le Mesh pour affichage sur la carte
            } else {
                LogManager.info("INTERCEPTION: Voix détectée sur $freqMHz MHz")
            }
        }
    }
}
