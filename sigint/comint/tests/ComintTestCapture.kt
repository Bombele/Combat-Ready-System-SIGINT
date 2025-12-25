package sigint.comint.tests

import sigint.comint.ComintCapture
import core.audit.MissionLogger

/**
 * SRC - ComintTestCapture
 * Valide les flux de capture en mode Simulation et SDR.
 */
object ComintTestCapture {

    @JvmStatic
    fun main(args: Array<String>) {
        MissionLogger.info("=== [TEST COMINT CAPTURE START] ===")

        // 1. Test du mode MOCK (Simulation)
        val mockCaptureInstance = ComintCapture(ComintCapture.Mode.MOCK)
        println("[TEST] Lancement capture MOCK sur 145.5 MHz...")
        val mockData = mockCaptureInstance.capture(145.5, 500)
        
        if (mockData.isNotEmpty()) {
            println("✅ MOCK SUCCESS: ${mockData.size} octets simulés capturés.")
        }

        // 2. Test du mode SDR (Hardware ready)
        val sdrCaptureInstance = ComintCapture(ComintCapture.Mode.SDR)
        println("[TEST] Lancement capture SDR sur 433.0 MHz...")
        val sdrData = sdrCaptureInstance.capture(433.0, 200)

        if (sdrData.isNotEmpty()) {
            println("✅ SDR INTERFACE SUCCESS: Buffer initialisé.")
        }

        MissionLogger.info("=== [TEST COMINT CAPTURE COMPLETED] ===")
    }
}
