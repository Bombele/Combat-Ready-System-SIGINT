package bft.models

import java.util.UUID

data class BFTPosition(
    val unitId: String,          // Identifiant unique (ex: ALPHA-01)
    val unitType: String,        // Type (INFANTRY, TECH, DRONE)
    val latitude: Double,
    val longitude: Double,
    val altitude: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "ACTIVE" // ACTIVE, MEDIC, OUT_OF_AMMO
)

