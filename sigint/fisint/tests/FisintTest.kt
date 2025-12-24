package sigint.fisint.tests

import sigint.fisint.FisintAnalyzer
import sigint.fisint.FisintProfile
import sigint.sync.SyncWorker
import core.sync.MeshSyncEngine
import core.audit.MissionLogger

object FisintTest {
    @JvmStatic
    fun main(args: Array<String>) {
        MissionLogger.info("=== [TEST FISINT MODULE] ===")

        MeshSyncEngine.startEngine()
        SyncWorker.startListening()

        val profiles = listOf(
            FisintProfile("DRONE_TELEMETRY_LINK", 100, "GFSK", "ISM_2.4GHZ", 3)
        )
        val analyzer = FisintAnalyzer(profiles)

        // Simulation d'une détection à 101ms (dans la marge de tolérance)
        println("[TEST] Simulation de télémétrie périodique : 101ms détectés...")
        analyzer.analyze(101, "ISM_2.4GHZ")

        Thread.sleep(1500)
        MeshSyncEngine.stopEngine()
        MissionLogger.info("=== [TEST FISINT COMPLETED] ===")
    }
}
