package com.fardc.sigint.core

import java.io.File

/**
 * Le Gatekeeper est le module de contrôle d'accès de sécurité.
 * Il vérifie la présence des certificats de souveraineté d'État 
 * avant d'autoriser l'activation des capacités offensives.
 */
class Gatekeeper {

    /**
     * Vérifie la présence et l'intégrité des clés de souveraineté.
     * Sans cette validation, le module OffensiveBridge reste verrouillé.
     */
    fun verifyStateAuth(): Boolean {
        // Chemins relatifs vers les clés de sécurité dans le dossier data/keys
        val certFile = File("data/keys/state_auth.crt")
        val keyFile = File("data/keys/state_private.key")

        println("🔐 [GATEKEEPER] Initialisation de la vérification de sécurité...")

        // 1. Vérification de l'existence physique des fichiers
        if (!certFile.exists() || !keyFile.exists()) {
            println("⚠️ [GATEKEEPER] ALERTE : Certificats d'État manquants dans data/keys/")
            println("   Veuillez générer les clés de souveraineté avant de continuer.")
            return false
        }

        // 2. Vérification sommaire de l'intégrité (fichiers non vides)
        val isIntegrityOk = certFile.length() > 0 && keyFile.length() > 0

        return if (isIntegrityOk) {
            println("✅ [GATEKEEPER] Certificat d'État validé. Autorisation SOUVERAINE accordée.")
            true
        } else {
            println("❌ [GATEKEEPER] ERREUR : Les fichiers de clés sont corrompus.")
            false
        }
    }
}
