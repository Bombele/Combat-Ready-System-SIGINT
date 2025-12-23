// core/security/GeofenceManager.kt
object GeofenceManager {
    private var activePolygon: List<Pair<Double, Double>> = emptyList()

    fun loadGeofence(polyFile: String) {
        val file = File(polyFile)
        if (file.exists()) {
            activePolygon = file.readLines().mapNotNull { line ->
                val parts = line.split(",")
                if (parts.size == 2) parts[0].toDouble() to parts[1].toDouble() else null
            }
        }
    }

    // Algorithme Ray Casting pour vérifier si la position est dans la zone
    fun isInAuthorizedZone(lat: Double, lon: Double): Boolean {
        if (activePolygon.isEmpty()) return true // Protection si pas de zone définie
        
        var isInside = false
        var j = activePolygon.size - 1
        for (i in activePolygon.indices) {
            val pi = activePolygon[i]
            val pj = activePolygon[j]
            if (((pi.second > lon) != (pj.second > lon)) &&
                (lat < (pj.first - pi.first) * (lon - pi.second) / (pj.second - pi.second) + pi.first)) {
                isInside = !isInside
            }
            j = i
        }
        return isInside
    }
}

fun main() {
    // Initialisation
    GeofenceManager.loadGeofence("core/security/active_geofence.poly")

    // Test 1 : Position au centre de Goma (devrait être INSIDE)
    val inGoma = GeofenceManager.isInAuthorizedZone(-1.6666, 29.2222)
    println("Position Goma Centre : ${if (inGoma) "AUTORISÉE" else "ALERTE WIPE"}")

    // Test 2 : Position à Sake (devrait être OUTSIDE)
    val inSake = GeofenceManager.isInAuthorizedZone(-1.6067, 29.0722)
    println("Position Sake (Hors Zone) : ${if (inSake) "AUTORISÉE" else "ALERTE WIPE"}")
}


