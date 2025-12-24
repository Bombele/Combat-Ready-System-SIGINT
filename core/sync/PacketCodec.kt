package core.sync

import bft.models.BFTPosition
import services.dsp.ai_inference.ThreatMessage
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.dataformat.cbor.CBORFactory

/**
 * SRC - PacketCodec
 * Encode/Décode les objets tactiques (BFT, SIGINT) pour transport Mesh.
 */
object PacketCodec {

    private val cborMapper = jacksonObjectMapper(CBORFactory())
    private val jsonMapper = jacksonObjectMapper()

    /**
     * Encode un objet en CBOR (par défaut).
     * Fallback JSON si CBOR échoue.
     */
    fun encodeCBOR(obj: Any): ByteArray {
        return try {
            cborMapper.writeValueAsBytes(obj)
        } catch (e: Exception) {
            jsonMapper.writeValueAsBytes(obj)
        }
    }

    /**
     * Décode un payload en objet typé.
     * Utilise CBOR, fallback JSON.
     */
    inline fun <reified T> decodeCBOR(payload: ByteArray): T? {
        return try {
            cborMapper.readValue(payload, T::class.java)
        } catch (e: Exception) {
            try {
                jsonMapper.readValue(payload, T::class.java)
            } catch (ex: Exception) {
                null
            }
        }
    }

    /**
     * Exemple : sérialiser une position BFT
     */
    fun encodeBFTPosition(pos: BFTPosition): ByteArray = encodeCBOR(pos)
    fun decodeBFTPosition(payload: ByteArray): BFTPosition? = decodeCBOR(payload)

    /**
     * Exemple : sérialiser une menace SIGINT
     */
    fun encodeThreat(threat: ThreatMessage): ByteArray = encodeCBOR(threat)
    fun decodeThreat(payload: ByteArray): ThreatMessage? = decodeCBOR(payload)
}