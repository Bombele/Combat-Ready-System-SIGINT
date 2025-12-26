import redis
import time

class IdentityResolver:
    """
    Base de données ultra-rapide (In-Memory) reliant les IMSI aux IP assignées.
    Alimenté par les logs DHCP des opérateurs ou l'interception GGSN/PGW.
    """
    def __init__(self):
        # Utilisation de Redis pour une réponse en micro-secondes
        self.db = redis.Redis(host='localhost', port=6379, db=0)

    def update_mapping(self, ip, imsi, gps_coords):
        """Met à jour le lien lors d'une nouvelle session data"""
        data = {"imsi": imsi, "gps": gps_coords, "last_seen": time.time()}
        self.db.set(ip, json.dumps(data), ex=3600)  # Expiration après 1h d'inactivité

    def resolve(self, ip):
        """Récupère l'identité physique à partir de l'IP"""
        res = self.db.get(ip)
        return json.loads(res) if res else None
