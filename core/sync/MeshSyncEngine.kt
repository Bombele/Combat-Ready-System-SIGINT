package core.sync

enum class Transport { WifiDirect, LoRa }

data class ThreatMessage(
    val id: String,
    val freqHz: Long,
    val modulation: String,
    val confidence: Double,
    val timestamp: Long
    // TODO: CBOR serialization
)

interface TransportAdapter {
    fun start()
    fun stop()
    fun send(msg: ThreatMessage): Boolean
    fun receive(handler: (ThreatMessage) -> Unit)
}

class MeshSyncEngine(
    private val adapter: TransportAdapter
) {
    fun start(handler: (ThreatMessage) -> Unit) = adapter.start().also { adapter.receive(handler) }
    fun stop() = adapter.stop()
    fun share(threat: ThreatMessage): Boolean = adapter.send(threat)
}
