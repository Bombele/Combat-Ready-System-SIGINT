#!/bin/bash
echo "🛡️ Initialisation du module Cyber Offensive..."

# Mise à jour des outils de scan et réseau
sudo apt-get update && sudo apt-get install -y nmap hping3 curl jq

# Création de l'arborescence de sortie pour les rapports d'attaque/résilience
mkdir -p build/reports/offensive
mkdir -p src/main/resources/payloads

echo "✅ Environnement prêt."
