package src

import core.security.TacticalWipeManager
import core.audit.MissionLogger
import bft.gps.GPSManager
import services.dsp.ai_inference.SignalClassifier
import core.sync.MeshSyncEngine
import ui.map.FusionOverlay
import core.sync.PacketCodec
import java.io.File

/**
 * SRC - Point d'entrée unique du système de combat.
 * Orchestration : Sécurité -> Capteurs -> Mesh -> UI.
 */
object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        println("=== [INITIALISATION SYSTÈME SRC - FARDC] ===")

        // 1. PHASE DE SÉCURITÉ (Condition sine qua non)
        val geofenceFile = "core/security/active_geofence.poly"
        val wipeManager = TacticalWipeManager(geofenceFile)
        
        MissionLogger.info("SYSTEM_BOOT: Vérification des protocoles de sécurité...")

        // Vérification de la présence des clés de mission
        if (!File("data/keys/master.key").exists()) {
            MissionLogger.critical("BOOT_FAILURE: Clés de mission absentes. Déclenchement WIPE préventif.")
            wipeManager.executeEmergencyWipe("NO_KEYS_FOUND")
            return
        }

        // 2. INITIALISATION DES CAPTEURS (SIGINT & GPS)
        val classifier = SignalClassifier("services/dsp/ai_inference/threat_model.tflite")
        if (!classifier.loadModel()) {
            MissionLogger.warning("SIGINT_IA: Modèle absent. Analyse spectrale réduite.")
        }

        // 3. ACTIVATION DU RÉSEAU MESH (Transport BFT/SIGINT)
        try {
            MeshSyncEngine.startEngine()
            MissionLogger.info("MESH_NET: Moteur de synchronisation actif.")
        } catch (e: Exception) {
            MissionLogger.critical("MESH_FAILURE: Impossible d'initialiser le réseau Mesh.")
        }

        // 4. LANCEMENT DE L'INTERFACE TACTIQUE (COP)
        println("[OK] Système opérationnel. Lancement de la Vue Fusionnée...")
        launchTacticalUI()
    }

    private fun launchTacticalUI() {
        // Démarre la boucle de rendu de la FusionOverlay
        // En conditions réelles, cela lancerait l'activité Android ou la fenêtre Desktop
        while (true) {
            val myPos = GPSManager.getLastPosition()
            if (myPos != null) {
                // Traitement des données entrantes et mise à jour de la carte
                FusionOverlay.renderAll(/* TacticalCanvas context */)
            }
            Thread.sleep(1000) // Rafraîchissement 1Hz pour économie batterie
        }
    }
}

package com.fardc.sigint.core

fun main(args: Array<String>) {
    println("🛡️ Project Sovereign Core - Initialisation...")
    
    val gatekeeper = Gatekeeper()
    if (gatekeeper.verifyStateAuth()) {
        println("✅ Authentification État-Major validée.")
        val bridge = OffensiveBridge()
        bridge.startFinancialInterception()
    } else {
        println("❌ Échec de l'authentification. Système verrouillé.")
    }
}
