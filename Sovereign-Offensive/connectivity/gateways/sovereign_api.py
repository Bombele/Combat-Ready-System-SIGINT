import requests
from core.gatekeeper import Gatekeeper

class SovereignGateway:
    def __init__(self, institution_url, cert_path):
        self.base_url = institution_url
        self.certs = cert_path # Certificats d'État pour le VPN/TLS

    def get_account_status(self, account_id, auth_token):
        """GET /account/status : Observation du solde"""
        Gatekeeper.verify_session(auth_token)
        response = requests.get(f"{self.base_url}/account/status/{account_id}", cert=self.certs)
        return response.json()

    def redirect_transaction(self, tx_id, new_destination, auth_token):
        """PATCH /transaction/route : Modification de destination avant validation"""
        Gatekeeper.verify_session(auth_token)
        payload = {"destination_id": new_destination, "status": "REDIRECTED_BY_STATE"}
        response = requests.patch(f"{self.base_url}/transaction/route/{tx_id}", json=payload, cert=self.certs)
        return response.status_code == 200

    def lock_portfolio(self, account_id, auth_token):
        """PUT /account/lock : Gel immédiat des avoirs"""
        Gatekeeper.verify_session(auth_token)
        response = requests.put(f"{self.base_url}/account/lock/{account_id}", cert=self.certs)
        return response.json()
