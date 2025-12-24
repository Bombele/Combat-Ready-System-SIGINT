package core.sync

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Types de données circulant sur le réseau Mesh
 */
enum class MessageType {
    BFT_POSITION,      // Localisation amie
    SIGINT_THREAT,     // Détection ennemie IA
    TACTICAL_ORDER,    // Ordre de mission ou alerte
    SYSTEM_STATUS      // État de santé du kit (Batterie, Tamper)
}

/**
 * SRC - UnifiedMessage
 * Enveloppe unique pour le transport sécurisé entre BFT et SIGINT.
 * Payload polymorphe : peut contenir BFTPosition, ThreatMessage, etc.
 */
data class UnifiedMessage(
    val senderId: String,
    val type: MessageType,
    val timestamp: Long = System.currentTimeMillis(),
    val payload: ByteArray,     // Données sérialisées (CBOR/Protobuf)
    var signature: String = ""  // HMAC-SHA256
) {
    /**
     * Calcule et applique la signature pour garantir l'origine et l'intégrité
     */
    fun sign(secretKey: ByteArray) {
        val dataToSign = "$senderId:$type:$timestamp:${payload.contentToString()}"
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(secretKey, "HmacSHA256"))
        val hash = mac.doFinal(dataToSign.toByteArray())
        this.signature = hash.joinToString("") { "%02x".format(it) }
    }

    /**
     * Vérifie si le message reçu n'a pas été altéré
     */
    fun verify(secretKey: ByteArray): Boolean {
        val expectedSignature = signAndReturn(secretKey)
        return this.signature == expectedSignature
    }

    private fun signAndReturn(secretKey: ByteArray): String {
        val dataToSign = "$senderId:$type:$timestamp:${payload.contentToString()}"
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(secretKey, "HmacSHA256"))
        val hash = mac.doFinal(dataToSign.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}