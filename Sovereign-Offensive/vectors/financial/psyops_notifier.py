import requests
import json
from datetime import datetime

class PsyOpsNotifier:
    """
    Module de Guerre Psychologique.
    Envoie des notifications ciblées aux réseaux ennemis après une saisie 
    pour briser la confiance entre les financeurs et les combattants.
    """
    def __init__(self, ccc_api_url="http://localhost:5000/api/v1/psyops"):
        self.ccc_api_url = ccc_api_url

    def dispatch_demoralization(self, target_imsi, amount, currency, operation_id):
        """
        Déclenche l'envoi de messages de démoralisation via les vecteurs SIGINT.
        """
        # Message type conçu pour créer la suspicion de trahison
        message = (
            f"RECU : La somme de {amount} {currency} a ete saisie par les forces de securite. "
            f"Ref: {operation_id}. Merci pour votre contribution involontaire a la Defense Nationale."
        )
        
        payload = {
            "target": target_imsi,
            "content": message,
            "type": "SMS_INJECTION",
            "priority": "HIGH"
        }

        try:
            # Envoi via l'interface SMS du système SIGINT (IMSI-Catcher ou Gateway)
            requests.post(self.ccc_api_url, json=payload, timeout=5)
            print(f"[🔥 PSYOPS] Message de démoralisation envoyé à l'IMSI {target_imsi}")
        except Exception as e:
            print(f"[!] Echec de l'envoi PsyOps : {e}")

# --- INTEGRATION POST-SAISIE ---
# Ce module est appelé automatiquement par auto_seizure.py après confirmation du hash
