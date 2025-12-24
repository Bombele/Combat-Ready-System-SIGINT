package core.sync

import core.audit.MissionLogger
import java.util.*

/**
 * Structure de donnée compacte pour l'échange de menaces SIGINT
 */
data class ThreatMessage(
    val id: String = UUID.randomUUID().toString(),
    val type: String,        // ex: "MOTOROLA_DMR", "THURAYA_SAT"
    val frequency: Double,   // MHz
    val latitude: Double,
    val longitude: Double,
    val priority: Int,       // 1 (Haut) à 3 (Bas)
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * SRC - MeshSyncEngine
 * Gère la synchronisation Store-and-Forward entre unités SIGINT.
 */
object MeshSyncEngine {

    // Cache local des menaces non encore synchronisées
    private val localOutbox = mutableListOf<ThreatMessage>()
    // Historique pour éviter les doublons
    private val knownMessageIds = mutableSetOf<String>()

    /**
     * Ajoute une menace détectée par l'IA locale au cache de synchronisation
     */
    fun enqueueThreat(message: ThreatMessage) {
        if (!knownMessageIds.contains(message.id)) {
            localOutbox.add(message)
            knownMessageIds.add(message.id)
            MissionLogger.info("Threat enqueued for mesh sync: ${message.type} at ${message.frequency}MHz")
        }
    }

    /**
     * Fonction appelée lorsqu'un pair (autre soldat) est détecté via Wi-Fi Direct ou LoRa
     */
    fun onPeerDetected(adapter: TransportAdapter) {
        MissionLogger.info("Mesh peer detected. Synchronizing...")
        
        // 1. Envoyer nos messages locaux (Priorité 1 d'abord)
        localOutbox.sortedBy { it.priority }.forEach { msg ->
            val success = adapter.send(msg)
            if (success) {
                // Optionnel : marquer comme envoyé
            }
        }

        // 2. Recevoir les messages du pair
        adapter.receive { remoteMsg ->
            if (!knownMessageIds.contains(remoteMsg.id)) {
                processIncomingThreat(remoteMsg)
            }
        }
    }

    private fun processIncomingThreat(msg: ThreatMessage) {
        knownMessageIds.add(msg.id)
        MissionLogger.warning("New threat received via Mesh: ${msg.type} from nearby unit")
        // TODO: Mettre à jour la carte tactique (ui/map)
    }
}

/**
 * Interface pour les différents types de transport (Wi-Fi Direct, LoRa, Bluetooth)
 */
interface TransportAdapter {
    fun send(msg: ThreatMessage): Boolean
    fun receive(handler: (ThreatMessage) -> Unit)
}

import java.util.PriorityQueue

object MeshSyncEngine {
    // Utilisation d'une PriorityQueue pour que la priorité 1 (CRITICAL) sorte toujours en premier
    private val localOutbox = PriorityQueue<ThreatMessage> { m1, m2 -> m1.priority.compareTo(m2.priority) }
    private val knownMessageIds = mutableSetOf<String>()

    fun enqueueThreat(message: ThreatMessage) {
        synchronized(this) {
            if (!knownMessageIds.contains(message.id)) {
                localOutbox.add(message)
                knownMessageIds.add(message.id)
                MissionLogger.info("Priority ${message.priority} threat enqueued.")
            }
        }
    }

    fun onPeerDetected(adapter: TransportAdapter) {
        synchronized(this) {
            while (localOutbox.isNotEmpty()) {
                val msg = localOutbox.peek() // On regarde le plus prioritaire
                if (adapter.send(msg)) {
                    localOutbox.poll() // Succès : on le retire de la file
                    MissionLogger.info("Sync success: ${msg.id}")
                } else {
                    break // Échec d'envoi (pair déconnecté), on garde le reste pour plus tard
                }
            }
        }
    }
}

// Dans la fonction qui reçoit les messages du réseau
fun onMessageReceived(msg: UnifiedMessage) {
    // 1. Log et vérification sécurité (déjà fait)
    
    // 2. Envoi à l'interface pour mise à jour en temps réel
    FusionOverlay.processIncomingMessage(msg)
}
