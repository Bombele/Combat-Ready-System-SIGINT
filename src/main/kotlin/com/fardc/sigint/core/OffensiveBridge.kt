package com.fardc.sigint.core

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket

class OffensiveBridge(private val gatekeeper: Gatekeeper) {
    fun startListening() {
        val serverSocket = ServerSocket(8888) // Port local sécurisé
        println("[CORE] Offensive Bridge opérationnel sur le port 8888")

        while (true) {
            val client = serverSocket.accept()
            val reader = BufferedReader(InputStreamReader(client.getInputStream()))
            val request = reader.readLine() // Format: AUTH_KEY|TARGET|ACTION

            if (gatekeeper.verifyHardwareToken(request.split("|")[0])) {
                println("[CORE] Action validée : ${request.split("|")[2]}")
                client.getOutputStream().write("AUTHORIZED\n".toByteArray())
            } else {
                client.getOutputStream().write("DENIED\n".toByteArray())
            }
            client.close()
        }
    }
}
