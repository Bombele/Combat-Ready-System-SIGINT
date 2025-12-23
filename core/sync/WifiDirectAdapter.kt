package core.sync

import android.content.Context
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pConfig
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

/**
 * Adaptateur pour la communication tactique sans infrastructure.
 * Gère la découverte des pairs et l'envoi/réception de messages ThreatMessage.
 */
class WifiDirectAdapter(private val context: Context) : TransportAdapter {

    private var manager: WifiP2pManager? = context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
    private var channel: WifiP2pManager.Channel? = manager?.initialize(context, context.mainLooper, null)

    override fun start() {
        // Lance la découverte des unités SIGINT à proximité
        manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() { /* Scan en cours */ }
            override fun onFailure(reason: Int) { /* Log erreur */ }
        })
        
        // Lance le serveur d'écoute pour recevoir les messages des camarades
        startIncomingServer()
    }

    private fun startIncomingServer() {
        thread(priority = Thread.NORM_PRIORITY) {
            val serverSocket = ServerSocket(8888)
            while (true) {
                val client = serverSocket.accept()
                val input = client.getInputStream().bufferedReader().readLine()
                // Ici, on transformerait le JSON/CBOR en ThreatMessage
                println("Reçu via Mesh: $input")
                client.close()
            }
        }
    }

    override fun send(msg: ThreatMessage): Boolean {
        // En combat, on envoie le message à tous les pairs connectés
        // Simulé ici par une écriture socket
        return try {
            thread {
                // Logique de connexion au pair et envoi
                // socket.outputStream.write(msg.toBinary())
            }
            true
        } catch (e: Exception) { false }
    }

    override fun stop() {
        manager?.stopPeerDiscovery(channel, null)
    }

    override fun receive(handler: (ThreatMessage) -> Unit) {
        // Handler pour traiter les messages reçus et les envoyer vers l'UI/Géo
    }
}
