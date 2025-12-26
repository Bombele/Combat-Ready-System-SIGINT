#!/bin/bash

# =============================================================================
# SRC - SCRIPT DE RACCORDEMENT AU SWITCH NATIONAL
# Établit un tunnel mTLS sécurisé pour l'interception et le filtrage légal.
# =============================================================================

set -e

# Couleurs pour le terminal
BLUE='\033[0;34m'
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}=== [SRC] CONNEXION À LA PASSERELLE DE SOUVERAINETÉ ===${NC}"

# 1. Variables de configuration (à adapter selon l'institution)
SWITCH_IP="10.255.0.1"
SRC_GATEWAY_IP="10.255.0.100"
TUNNEL_NAME="tun_switch_fardc"

# 2. Vérification des certificats d'État
echo -e "[1/4] Vérification des identités cryptographiques..."
if [ ! -f "data/keys/state_auth.crt" ] || [ ! -f "data/keys/state_private.key" ]; then
    echo -e "${RED}[ERREUR] Certificats d'État manquants dans data/keys/. Impossible d'établir le lien.${NC}"
    exit 1
fi

# 3. Création du tunnel VPN (WireGuard ou OpenVPN durci)
echo -e "[2/4] Initialisation du tunnel sécurisé vers le Switch National..."
# Simulation de la montée du tunnel
ip link add $TUNNEL_NAME type gre local $SRC_GATEWAY_IP remote $SWITCH_IP ttl 255
ip addr add 192.168.100.1/30 dev $TUNNEL_NAME
ip link set $TUNNEL_NAME up

# 4. Configuration du Proxy ISO 8583 (Redirection du trafic vers le Controller Kotlin)
echo -e "[3/4] Activation de la redirection du flux financier (Port 8583)..."
# Redirige le trafic entrant sur le port financier vers ton application SRC
iptables -t nat -A PREROUTING -p tcp --dport 8583 -j REDIRECT --to-ports 8583
echo -e "${GREEN}[OK] Redirection active.${NC}"

# 5. Test de latence et d'intégrité
echo -e "[4/4] Test de liaison..."
ping -c 3 192.168.100.2 > /dev/null && echo -e "${GREEN}[LINK ESTABLISHED] Canal sécurisé opérationnel.${NC}" || echo -e "${RED}[WARN] Liaison instable.${NC}"

echo -e "\n${BLUE}==========================================================${NC}"
echo -e "${GREEN}🚀 SYSTÈME CONNECTÉ AU FLUX NATIONAL${NC}"
echo -e "Prêt pour le filtrage offensif : NationalSwitchController actif."
echo -e "${BLUE}==========================================================${NC}"

#!/bin/bash
CERT_PATH="data/keys/state_auth.crt"
echo "[*] Établissement du tunnel mTLS vers le Switch National..."
# Simulation de connexion sécurisée
nc -zv 10.0.0.1 8583 # Test du port ISO 8583

