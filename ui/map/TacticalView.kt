package ui.map

import bft.BFTService
import bft.gps.BFTPosition
import core.audit.MissionLogger
import services.dsp.ai_inference.SignalClassifier
import services.dsp.ai_inference.ThreatMessage
import ui.map.offline.MbtilesRenderer
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * SRC - TacticalView
 * Vue fusionnée BFT + SIGINT pour l'aide à la décision.
 */
class TacticalView(private val renderer: MbtilesRenderer) {

    /**
     * Rafraîchit l'affichage de la carte tactique
     */
    fun refreshDisplay() {
        try {
            // 1. Dessiner la carte de base (Offline)
            renderer.drawBaseMap()

            // 2. Dessiner les "BLUE FORCES" (BFT)
            val friends = BFTService.getAllFriendlyPositions()
            friends.forEach { pos ->
                renderer.drawIcon(
                    icon = "ICON_FRIENDLY_INFANTRY",
                    lat = pos.latitude,
                    lon = pos.longitude,
                    label = pos.unitName
                )
            }

            // 3. Dessiner les "RED THREATS" (SIGINT)
            val threats = SignalClassifier.getActiveThreats()
            threats.forEach { threat ->
                renderer.drawCircle(
                    centerLat = threat.latitude,
                    centerLon = threat.longitude,
                    radius = 500.0, // mètres
                    color = "RED_TRANSPARENT"
                )
                renderer.drawIcon(
                    icon = "ICON_SIGINT_DMR",
                    lat = threat.latitude,
                    lon = threat.longitude,
                    label = "${threat.type} (${threat.frequency}MHz)"
                )
            }

            // 4. Vérifier les intersections (alerte de proximité)
            checkIntersections(friends, threats)

        } catch (e: Exception) {
            MissionLogger.error("TacticalView refresh failed: ${e.message}")
        }
    }

    /**
     * Vérifie si une unité amie entre dans une zone de menace
     */
    private fun checkIntersections(friends: List<BFTPosition>, threats: List<ThreatMessage>) {
        friends.forEach { friend ->
            threats.forEach { threat ->
                val distance = haversine(friend.latitude, friend.longitude, threat.latitude, threat.longitude)
                if (distance <= 0.5) { // 500m = 0.5 km
                    MissionLogger.warning("ALERTE: ${friend.unitName} entre dans une zone rouge (${threat.type})")
                    renderer.drawAlert(
                        lat = friend.latitude,
                        lon = friend.longitude,
                        message = "⚠ ${friend.unitName} en zone de menace ${threat.type}"
                    )
                }
            }
        }
    }

    /**
     * Calcul de distance Haversine (km)
     */
    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Rayon de la Terre en km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = kotlin.math.sin(dLat / 2).pow(2.0) +
                kotlin.math.cos(Math.toRadians(lat1)) *
                kotlin.math.cos(Math.toRadians(lat2)) *
                kotlin.math.sin(dLon / 2).pow(2.0)
        val c = 2 * kotlin.math.atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }
}