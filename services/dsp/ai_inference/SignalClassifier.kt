package services.dsp.ai_inference

import core.audit.MissionLogger
import core.sync.MeshSyncEngine
import core.sync.MessageType
import core.sync.UnifiedMessage
import core.sync.PacketCodec

/**
 * Modèle de données pour une menace détectée
 */
data class ThreatMessage(
    val id: String,
    val type: String,      // ex: "DMR_ENCRYPTED", "VHF_ANALOG"
    val frequency: Double, // MHz
    val latitude: Double,
    val longitude: Double,
    val confidence: Float,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * SRC - SignalClassifier
 * Analyseur de spectre basé sur l'IA pour l'identification des menaces.
 */
object SignalClassifier {

    private var isModelLoaded = false

    /**
     * Charge le modèle TensorFlow Lite en mémoire
     */
    fun loadModel(modelPath: String): Boolean {
        return try {
            // Simulation du chargement TFLite Interpreter
            MissionLogger.info("SIGINT_IA: Modèle $modelPath chargé avec succès.")
            isModelLoaded = true
            true
        } catch (e: Exception) {
            MissionLogger.error("SIGINT_IA: Échec chargement modèle: ${e.message}")
            false
        }
    }

    /**
     * Analyse un échantillon IQ brut provenant de l'antenne SDR
     */
    fun analyzeSignal(iqData: ByteArray, currentLat: Double, currentLon: Double) {
        if (!isModelLoaded) return

        // --- SIMULATION D'INFÉRENCE IA ---
        // Dans un cas réel, on passerait iqData au modèle TFLite
        val detectionProbability = 0.92f // Simulation d'une détection forte

        if (detectionProbability > 0.85f) {
            val threat = ThreatMessage(
                id = "THR-${System.currentTimeMillis() % 10000}",
                type = "DMR_GOUV_OPFOR", // Type identifié par l'IA
                frequency = 446.03125,
                latitude = currentLat + 0.005, // Simulation d'un offset (position estimée)
                longitude = currentLon + 0.005,
                confidence = detectionProbability
            )

            broadcastThreat(threat)
        }
    }

    /**
     * Transforme la détection en UnifiedMessage et l'envoie au Mesh
     */
    private fun broadcastThreat(threat: ThreatMessage) {
        MissionLogger.warning("SIGINT_ALERT: Menace identifiée: ${threat.type} à ${threat.frequency} MHz")

        val payload = PacketCodec.encodeThreat(threat)
        val msg = UnifiedMessage(
            senderId = "SIGINT-NODE-01",
            type = MessageType.SIGINT_THREAT,
            payload = payload
        )

        // Envoi prioritaire au moteur de synchronisation
        MeshSyncEngine.enqueueMessage(msg)
    }
}
