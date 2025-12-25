#!/bin/bash
echo "[*] Durcissement du système en cours..."

# Désactivation des services inutiles
systemctl stop bluetooth
systemctl disable avahi-daemon

# Configuration du Pare-feu (Drop par défaut)
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -A INPUT -i lo -j ACCEPT
iptables -A INPUT -m conntrack --ctstate ESTABLISHED,RELATED -j ACCEPT

echo "[OK] Système blindé pour opération offensive."
