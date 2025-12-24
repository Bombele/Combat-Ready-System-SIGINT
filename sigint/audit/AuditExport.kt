package sigint.audit

import java.io.File
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object AuditExport {

    private val mapper = jacksonObjectMapper()

    fun writeJson(path: String, payload: Any, hmacKey: ByteArray?): Boolean {
        return try {
            val json = mapper.writeValueAsString(payload)
            File(path).writeText(json)
            hmacKey?.let { writeHmac("$path.sig", json.toByteArray(), it) }
            true
        } catch (e: Exception) { false }
    }

    private fun writeHmac(sigPath: String, data: ByteArray, key: ByteArray) {
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(key, "HmacSHA256"))
        val sig = mac.doFinal(data)
        File(sigPath).writeBytes(sig)
    }
}
