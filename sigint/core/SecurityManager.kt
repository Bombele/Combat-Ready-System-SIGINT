package sigint.core

import java.io.File

object SecurityManager {
    fun hasMissionKeys(): Boolean = File("data/keys/master.key").exists()
    fun isGeofenceConfigured(): Boolean = File("core/security/active_geofence.poly").exists()
}
