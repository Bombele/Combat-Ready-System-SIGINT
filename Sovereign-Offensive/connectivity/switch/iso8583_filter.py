class ISO8583Filter:
    def __init__(self):
        self.threat_signatures = ["RIB_TERRO_001", "RIB_TERRO_002"]

    def process_iso_message(self, iso_msg):
        """Agit comme un pare-feu financier sur le switch national"""
        
        # Champ 102 : Account Identification 1 (Destination)
        destination_account = iso_msg.get_field(102)

        if destination_account in self.threat_signatures:
            return self.generate_rejection(iso_msg, reason_code="57") # 57 = Transaction not permitted
        
        return iso_msg # Autoriser la transaction

    def generate_rejection(self, original_msg, reason_code):
        """Modifie le code de réponse pour bloquer la transaction au niveau central"""
        rejected_msg = original_msg.copy()
        # Champ 39 : Response Code
        rejected_msg.set_field(39, reason_code)
        print(f"[SWITCH] Blocage transaction ISO8583 vers {original_msg.get_field(102)}")
        return rejected_msg
