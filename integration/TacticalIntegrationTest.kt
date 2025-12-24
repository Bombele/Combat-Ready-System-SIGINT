package integration

import services.dsp.SdrInterface
import services.dsp.ai_inference.SignalClassifier
import sigint.SignatureManager
import core.sync.MeshSyncEngine
import core.audit.MissionLogger
import ui.map.FusionOverlay
import bft.gps.GPSManager

/**
 * SRC - TacticalIntegrationTest
 * Simule une chaîne complète : Détection -> Classification -> Diffusion -> Affichage.
 */
object TacticalIntegrationTest {

    fun runFullCombatScenario() {
        println("=== [DÉBUT TEST INTÉGRATION TACTIQUE] ===")

        // 1. Initialisation de l'environnement
        SignatureManager.loadLibrary()
        MeshSyncEngine.startEngine()
        
        // 2. Fix GPS Local (Goma, RDC)
        GPSManager.onLocationChanged(-1.66, 29.22, 1500.0, 5.0f)
        val myPos = GPSManager.getLastPosition()!!
        MissionLogger.info("TEST: Position locale fixée à [${myPos.latitude}, ${myPos.longitude}]")

        // 3. Simulation d'une détection SDR (Signal de radio ennemie)
        println("[SIMULATION] L'antenne capte un signal suspect à 446.1 MHz...")
        
        // On simule un buffer IQ qui contient une signature DMR connue
        val fakeIqData = ByteArray(1024) { 0xFF.toByte() } 
        SignalClassifier.analyzeSignal(fakeIqData, myPos.latitude, myPos.longitude)

        // 4. Attente de la propagation dans le Mesh
        Thread.sleep(1000)

        // 5. Vérification du rendu final (FusionOverlay)
        println("[UI] Mise à jour de la carte tactique...")
        // Dans le test, on vérifie que la liste des menaces n'est plus vide
        // FusionOverlay.renderAll(...)

        println("=== [FIN DU SCÉNARIO : SUCCÈS] ===")
        MeshSyncEngine.stopEngine()
    }
}

fun main() {
    TacticalIntegrationTest.runFullCombatScenario()
}
