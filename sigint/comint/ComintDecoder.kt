package sigint.comint

import sigint.audit.LogManager
import kotlin.math.atan2

/**
 * SRC - ComintDecoder
 * Décodeur multi-protocole pour les signaux interceptés.
 */
class ComintDecoder {

    /**
     * Point d'entrée pour le décodage selon la modulation détectée
     */
    fun decode(iqData: ByteArray, modulation: String): String {
        return when (modulation.uppercase()) {
            "NFM" -> decodeNFM(iqData)
            "FSK" -> decodeBurstData(iqData)
            else -> "MODULATION_INCONNUE"
        }
    }

    /**
     * Décodage Narrowband FM (Voix)
     * Principe : Calcul de la phase entre deux échantillons IQ successifs
     */
    private fun decodeNFM(iqData: ByteArray): String {
        LogManager.info("COMINT_DECODER: Traitement flux NFM (Voix)")
        
        // Simulation de démodulation FM par discrimination de phase
        // En réel, on calcule : freq_instantannée = atan2(I*Q' - Q*I', I*I' + Q*Q')
        
        return "VOICE_STREAM_DETECTED"
    }

    /**
     * Décodage FSK (Données numériques / Burst)
     * Utilisé pour les SMS radio, la télémétrie ou les identifiants d'unité.
     */
    private fun decodeBurstData(iqData: ByteArray): String {
        LogManager.info("COMINT_DECODER: Extraction données FSK")
        
        // Simulation d'extraction de bits (0 et 1) à partir des sauts de fréquence
        val decodedHex = "4F 50 46 4F 52 5F 55 4E 49 54" // "OPFOR_UNIT" en hex
        
        return "DATA_DECODED: $decodedHex"
    }
}
