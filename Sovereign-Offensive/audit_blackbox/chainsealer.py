import hashlib
import json
import time
from datetime import datetime

class ChainSealer:
    """
    Garant de l'intégrité souveraine. 
    Crée une chaîne de blocs (Ledger) inaltérable pour chaque saisie financière.
    """
    def __init__(self, storage_path="audit/blackbox.log"):
        self.storage_path = storage_path
        self.last_hash = "0000000000000000" # Hash de genèse

    def generate_seal(self, operator_id, target_imsi, amount, destination):
        """
        Génère un scellé cryptographique pour une opération de saisie.
        """
        operation_data = {
            "timestamp": datetime.now().isoformat(),
            "operator": operator_id,
            "target": target_imsi,
            "amount": amount,
            "destination_final": destination,
            "previous_hash": self.last_hash
        }
        
        # Création du Hash unique (SHA-256)
        raw_data = json.dumps(operation_data, sort_keys=True).encode()
        current_hash = hashlib.sha256(raw_data).hexdigest()
        
        self.last_hash = current_hash
        self.write_to_blackbox(operation_data, current_hash)
        
        return current_hash

    def write_to_blackbox(self, data, seal_hash):
        """
        Écrit l'entrée dans le journal d'audit immuable.
        """
        entry = {
            "data": data,
            "seal": seal_hash
        }
        with open(self.storage_path, "a") as f:
            f.write(json.dumps(entry) + "\n")
        print(f"[🛡️ AUDIT] Opération scellée. Hash: {seal_hash[:16]}...")

# --- INITIALISATION ---
auditor = ChainSealer()
