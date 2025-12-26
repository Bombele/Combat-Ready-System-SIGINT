import sys
import json
import requests
from datetime import datetime
from scapy.all import *

# CONFIGURATION SOUVERAINE
SIGINT_DATABASE_URL = "http://localhost:8080/api/v1/metadata/imsi_ip_map"
BLOCKCHAIN_NODE_URL = "https://blockchain.info/rawaddr/" # Exemple pour BTC
STATE_COIN_ADDRESS = "1FARDC..." # Adresse de saisie de l'État

class CryptoLinker:
    """
    Module de dé-anonymisation et de corrélation SIGINT/Blockchain.
    Croise les adresses IP des nœuds de transaction avec les métadonnées IMSI.
    """
    def __init__(self):
        self.active_targets = {}
        print(f"[!] CryptoLinker Initialisé - Mode Offensive Actif")

    def get_sigint_identity(self, ip_address):
        """
        Interroge la base de données SIGINT pour corréler une IP avec un IMSI/GPS.
        """
        try:
            response = requests.get(f"{SIGINT_DATABASE_URL}/{ip_address}", timeout=2)
            if response.status_code == 200:
                return response.json() # Retourne {imsi: "...", gps: "...", user_id: "..."}
        except Exception as e:
            return None
        return None

    def analyze_transaction(self, wallet_address):
        """
        Analyse l'historique d'un portefeuille et identifie les adresses IP d'origine.
        """
        print(f"[*] Analyse du portefeuille : {wallet_address}")
        try:
            # Récupération des données blockchain
            data = requests.get(f"{BLOCKCHAIN_NODE_URL}{wallet_address}").json()
            
            for tx in data.get('txs', []):
                relayed_by = tx.get('relayed_by') # IP du nœud ayant propagé la TX
                
                if relayed_by:
                    identity = self.get_sigint_identity(relayed_by)
                    if identity:
                        print(f"[🔴 TARGET IDENTIFIED] IP: {relayed_by} | IMSI: {identity['imsi']}")
                        self.flag_target_on_dashboard(wallet_address, identity)
                        return identity
        except Exception as e:
            print(f"[!] Erreur d'analyse Blockchain : {e}")
        return None

    def flag_target_on_dashboard(self, wallet, identity):
        """
        Envoie les données au Tactical Monitor (CCC) pour affichage de l'icône rouge.
        """
        payload = {
            "type": "CRYPTO_DEANON",
            "wallet": wallet,
            "imsi": identity['imsi'],
            "coords": identity['gps'],
            "timestamp": datetime.now().isoformat()
        }
        # Transmission au tableau de bord via webhook interne
        try:
            requests.post("http://localhost:5000/api/v1/dashboard/update", json=payload)
        except:
            pass

    def intercept_broadcast(self, pkt):
        """
        Analyse les paquets réseau en temps réel (Optical Tap) pour détecter
        des signatures de protocoles de portefeuilles (ex: Electrum, Bitcoin P2P).
        """
        if pkt.haslayer(TCP) and pkt.haslayer(Raw):
            payload = pkt[Raw].load.decode(errors='ignore')
            # Recherche de patterns de transactions ou de signatures de wallets
            if "blockchain.info" in payload or "electrum" in payload:
                src_ip = pkt[IP].src
                print(f"[!] Détection de trafic Crypto - IP Source: {src_ip}")
                self.analyze_transaction_from_ip(src_ip)

# --- POINT D'ENTRÉE OPÉRATIONNEL ---
if __name__ == "__main__":
    linker = CryptoLinker()
    
    # Mode 1 : Sniffing passif sur l'interface de l'Optical Tap
    print("[*] Lancement du sniffing sur l'interface SIGINT eth0...")
    sniff(iface="eth0", prn=linker.intercept_broadcast, store=0)
