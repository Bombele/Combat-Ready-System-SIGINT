package com.fardc.sigint.core
import java.io.File

class Gatekeeper {
    fun verifyStateAuth(): Boolean {
        val cert = File("data/keys/state_auth.crt")
        val key = File("data/keys/state_private.key")
        return cert.exists() && key.exists() // Simulation de validation PKI
    }
}
