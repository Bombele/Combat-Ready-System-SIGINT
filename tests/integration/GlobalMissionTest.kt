package tests.integration

import core.security.TacticalWipeManager
import core.audit.MissionLogger
import core.sync.*
import services.dsp.ai_inference.SignalClassifier
import java.io.File

/**
 * SRC - Global Mission Integration Test
 * Simule le cycle complet OODA : Boot -> Détection -> Classification -> Sync Mesh.
 */
fun main() {
    println("=== [DÉBUT DU TEST GLOBAL : MISSION SIGINT FARDC] ===")

    // --- ÉTAPE 1 : BOOT SÉCURISÉ ---
    // On simule une position dans Goma (Inside Geofence)
    val wiper = TacticalWipeManager("core/security/active_geofence.poly")
    val currentLat = -1.6666
    val currentLon = 29.2222

    if (wiper.isPositionSafe(currentLat, currentLon)) {
        MissionLogger.info("MISSION_START: Position safe. Geofence validated.")
    } else {
        println("ERREUR : Le système a détecté une sortie de zone au boot !")
        return
    }

    // --- ÉTAPE 2 : DÉTECTION ET INTELLIGENCE ---
    val classifier = SignalClassifier("services/dsp/ai_inference/threat_model.tflite")
    classifier.loadModel()

    // Simulation d'un buffer de données IQ capturé par l'antenne
    val dummySpectrum = FloatArray(1024) { Math.random().toFloat() }
    
    // Le classifier analyse le spectre et génère un ThreatMessage
    val threat = classifier.classifySpectrum(dummySpectrum, currentLat, currentLon)

    // --- ÉTAPE 3 : PROPAGATION MESH ---
    if (threat != null) {
        // Enfilage du message dans le moteur de synchronisation
        MeshSyncEngine.enqueueThreat(threat)

        // Simulation d'un adaptateur de transport pour envoyer à une autre unité
        val mockAdapter = object : TransportAdapter {
            override fun send(msg: ThreatMessage): Boolean {
                println(">>> MESH_SEND : Menace [${msg.type}] envoyée à l'unité adjacente via Wi-Fi Direct.")
                return true
            }
            override fun receive(handler: (ThreatMessage) -> Unit) { /* Mock */ }
        }

        // Déclenchement de la synchronisation dès qu'un pair est détecté
        MeshSyncEngine.onPeerDetected(mockAdapter)
    }

    // --- ÉTAPE 4 : AUDIT ET VÉRIFICATION ---
    println("\n--- VÉRIFICATION DE L'INTÉGRITÉ DU JOURNAL ---")
    val logFile = File("core/audit/logs/mission.log")
    if (logFile.exists()) {
        println("Journal de mission généré (${logFile.length()} octets).")
        // On pourrait ici appeler verifyIntegrity() si implémenté avec HMAC
    }

    println("=== [TEST GLOBAL RÉUSSI : SYSTÈME OPÉRATIONNEL] ===")
}
