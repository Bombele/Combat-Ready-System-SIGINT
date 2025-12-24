package services.dsp

import core.audit.MissionLogger
import services.dsp.ai_inference.SignalClassifier
import java.util.concurrent.atomic.AtomicBoolean

/**
 * SRC - SdrInterface
 * Interface de bas niveau pour le pilotage du matériel SDR (Software Defined Radio).
 */
object SdrInterface {

    private val isStreaming = AtomicBoolean(false)
    private var currentFrequency = 144.0 // MHz (Bande VHF par défaut)
    private var currentGain = 40 // dB

    /**
     * Initialise la connexion avec le driver (libusb / rtl-sdr)
     */
    fun startCapture(frequency: Double, gain: Int) {
        this.currentFrequency = frequency
        this.currentGain = gain
        
        if (isStreaming.get()) return
        
        MissionLogger.info("SDR_HARDWARE: Initialisation de l'antenne sur $frequency MHz (Gain: $gain dB)")
        isStreaming.set(true)

        // Lancement du thread de lecture haute performance
        Thread {
            runCaptureLoop()
        }.start()
    }

    /**
     * Boucle d'acquisition des échantillons IQ
     */
    private fun runCaptureLoop() {
        try {
            while (isStreaming.get()) {
                // 1. Capture d'un buffer d'échantillons IQ (Simulation d'appel JNI/Native)
                val buffer = captureRawIQ(4096) 

                // 2. Envoi direct au classificateur IA pour analyse en temps réel
                // On passe les coordonnées factices (à remplacer par GPSManager.getLastPosition())
                SignalClassifier.analyzeSignal(buffer, -1.66, 29.22)

                // 3. Petite pause pour éviter de saturer le CPU (ajustable selon le hardware)
                Thread.sleep(100) 
            }
        } catch (e: Exception) {
            MissionLogger.error("SDR_CRITICAL: Perte de connexion avec l'antenne: ${e.message}")
            isStreaming.set(false)
        }
    }

    private fun captureRawIQ(size: Int): ByteArray {
        // En production, cette méthode appellerait une bibliothèque native comme librtlsdr
        return ByteArray(size) { (0..255).random().toByte() }
    }

    fun stopCapture() {
        isStreaming.set(false)
        MissionLogger.info("SDR_HARDWARE: Antenne déconnectée.")
    }
}
