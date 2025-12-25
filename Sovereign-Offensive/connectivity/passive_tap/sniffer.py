import scapy.all as scapy
from core.state_machine import ModeManager

class PassiveTap:
    def __init__(self, interface="eth0"):
        self.interface = interface

    def start_sniffing(self, filter_rule="tcp port 80 or port 443"):
        """Analyse le trafic en mode miroir (SPAN/TAP)."""
        print(f"[*] Capture passive lancée sur {self.interface}...")
        scapy.sniff(iface=self.interface, filter=filter_rule, prn=self.process_packet)

    def process_packet(self, packet):
        # Transmet au moteur de fusion SIGINT/Finance
        if packet.haslayer(scapy.Raw):
            payload = packet[scapy.Raw].load
            # Analyse des signatures financières ou cyber
            ModeManager.analyze_only(payload)
