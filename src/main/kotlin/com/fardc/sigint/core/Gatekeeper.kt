package com.psc.sovereign.core

class Gatekeeper {
    private val PSC_SIGNATURE = "PSC-SOUVERAIN-FARDC-2025"

    fun verifyHardwareToken(token: String): Boolean {
        return if (token == PSC_SIGNATURE) {
            println("[GATEKEEPER] Signature PSC validée.")
            true
        } else {
            println("[ALERTE] Jeton invalide détecté par le Gatekeeper.")
            false
        }
    }

    fun verifyCommand(signature: ByteArray): Boolean {
        return signature.isNotEmpty()
    }
}
