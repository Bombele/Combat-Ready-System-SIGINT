package com.fardc.sigint.core

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class OffensiveBridge {

    /**
     * Active le module de Saisie Conservatoire Numérique et gère 
     * la communication avec le script Python via un Socket.
     */
    fun startFinancialInterception() {
        println("🚀 Activation du module de Saisie Conservatoire Numérique...")

        // Lancement du script Python en arrière-plan
        val processBuilder = ProcessBuilder("python3", "vectors/financial/auto_seizure.py")
        processBuilder.inheritIO()
        val process = processBuilder.start()

        // Communication avec le script Python
        Thread {
            try {
                // Le port 8888 doit être le même que celui défini dans auto_seizure.py
                val serverSocket = ServerSocket(8888)
                println("[BRIDGE] En attente de données du module offensif sur le port 8888...")
                
                while (true) {
                    val clientSocket: Socket = serverSocket.accept()
                    val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                    val message = reader.readLine()
                    
                    if (message != null) {
                        println("[ALERTE OFFENSIVE] Données reçues : $message")
                        // Ici, on peut envoyer l'alerte vers l'Audit ou le Dashboard
                    }
                    clientSocket.close()
                }
            } catch (e: Exception) {
                println("❌ Erreur Bridge : ${e.message}")
            }
        }.start()
    }
}
