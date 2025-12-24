package sigint.sync

import core.sync.MeshSyncEngine
import core.sync.UnifiedMessage
import core.sync.MessageType
import core.sync.PacketCodec
import sigint.core.EventBus

object SyncWorker {
    fun startListening() {
        // On s'abonne aux détections COMINT pour les envoyer dans le Mesh global
        EventBus.subscribe<Map<String, Any>>("COMINT_DETECTION") { data ->
            val threatPayload = PacketCodec.encodeCBOR(data)
            
            val msg = UnifiedMessage(
                senderId = "SIGINT_UNIT_01",
                type = MessageType.SIGINT_THREAT,
                payload = threatPayload
            )
            
            MeshSyncEngine.enqueueMessage(msg)
        }
    }
}

