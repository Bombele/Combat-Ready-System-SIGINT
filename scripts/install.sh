#!/bin/bash

# =============================================================================
# SRC - SCRIPT D'INSTALLATION AUTOMATISÉE
# Cible : Terminaux Tactiques (Debian/Ubuntu/Kali)
# =============================================================================

set -e

echo -e "\033[0;34m[SRC] Initialisation de l'environnement de combat...\033[0m"

# 1. Mise à jour et dépendances système
echo "[1/4] Installation des paquets de base..."
sudo apt-get update
sudo apt-get install -y openjdk-17-jdk kotlin build-essential libusb-1.0-0-dev cmake git

# 2. Installation des drivers SDR (RTL-SDR / HackRF)
echo "[2/4] Configuration des drivers SDR..."
sudo apt-get install -y rtl-sdr librtlsdr-dev
# Ajout des règles udev pour permettre l'accès USB sans root
sudo bash -c 'echo "SUBSYSTEM==\"usb\", ATTRS{idVendor}==\"0bda\", ATTRS{idProduct}==\"2838\", MODE=\"0666\"" > /etc/udev/rules.d/20-rtlsdr.rules'
sudo udevadm control --reload-rules && sudo udevadm trigger

# 3. Préparation de l'arborescence SRC
echo "[3/4] Création des répertoires de données sécurisés..."
mkdir -p data/logs data/audit data/keys data/maps
mkdir -p bin/

# 4. Vérification de l'environnement Kotlin
echo "[4/4] Vérification de la chaîne de compilation..."
if command -v kotlinc >/dev/null 2>&1; then
    echo -e "\033[0;32m[OK] Kotlin est prêt.\033[0m"
else
    echo -e "\033[0;31m[ERREUR] Échec de l'installation de Kotlin.\033[0m"
    exit 1
fi

echo -e "\n\033[0;32m=== INSTALLATION TERMINÉE ===\033[0m"
echo "Prochaine étape : 'make rotate-keys' puis 'make combat-ready'"
