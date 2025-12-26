import socket

def simulate_bank_traffic():
    # Simulation d'un message ISO 8583 (Format simplifié pour test)
    # MTI 0200 (Transaction), DE 102 (RIB Cible), DE 04 (Montant 500,000)
    iso_packet = "0200" + "102015" + "PSC-SOUVERAIN-TARGET-RIB-99" + "04010" + "500000"
    
    print(f"[TEST] Envoi d'une transaction vers la cible...")
    try:
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect(("127.0.0.1", 8583))
            s.sendall(iso_packet.encode())
            print("[TEST] Paquet envoyé. Vérifiez le terminal du Cœur PSC.")
    except ConnectionRefusedError:
        print("[!] Erreur : Le Switch PSC n'écoute pas sur le port 8583.")

if __name__ == "__main__":
    simulate_bank_traffic()
