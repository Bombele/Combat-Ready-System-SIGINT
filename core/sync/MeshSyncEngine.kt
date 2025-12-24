package core.sync

import core.audit.MissionLogger
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean

/**
 * SRC - MeshSyncEngine
 * Moteur de synchronisation P2P pour transporter UnifiedMessage (BFT, SIGINT, etc.)
 */
object MeshSyncEngine {

    // File de messages avec priorisation
    private val messageQueue = PriorityBlockingQueue<UnifiedMessage>(100, compareBy { it.typePriority() })
    private val isRunning = AtomicBoolean(false)

    /**
     * Démarre le moteur Mesh
     */
    fun startEngine() {
        if (isRunning.get()) {
            MissionLogger.warning("MESH_ENGINE: Already running.")
            return
        }
        isRunning.set(true)
        MissionLogger.info("MESH_ENGINE: Starting...")

        Thread {
            while (isRunning.get()) {
                try {
                    val msg = messageQueue.take()
                    transmit(msg)
                } catch (e: Exception) {
                    MissionLogger.error("MESH_ENGINE_LOOP: ${e.message}")
                }
            }
        }.start()
    }

    /**
     * Arrête le moteur Mesh
     */
    fun stopEngine() {
        isRunning.set(false)
        MissionLogger.info("MESH_ENGINE: Stopped.")
    }

    /**
     * Ajoute un message dans la file
     */
    fun enqueueMessage(msg: UnifiedMessage) {
        messageQueue.put(msg)
        MissionLogger.info("MESH_QUEUE: Message enqueued (${msg.type}) from ${msg.senderId}")
    }

    /**
     * Transmission simulée (à remplacer par WifiDirectAdapter / P2P)
     */
    private fun transmit(msg: UnifiedMessage) {
        MissionLogger.info("MESH_TX: Transmitting ${msg.type} from ${msg.senderId}")
        // Ici tu brancheras l’adaptateur réseau réel
    }

    /**
     * Définition des priorités
     */
    private fun UnifiedMessage.typePriority(): Int {
        return when (this.type) {
            MessageType.SIGINT_THREAT -> 1   // Critique
            MessageType.TACTICAL_ORDER -> 2 // Important
            MessageType.BFT_POSITION -> 3   // Routine
            MessageType.SYSTEM_STATUS -> 4  // Info
        }
    }
}