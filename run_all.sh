#!/bin/bash
# 1. Compilation
echo "[1/3] Compilation du Noyau Java/Kotlin..."
./gradlew shadowJar

# 2. Configuration Réseau
echo "[2/3] Configuration des interfaces tactiques..."
sudo chmod +x scripts/*.sh
sudo ./scripts/activate_combat_mode.sh

# 3. Lancement
echo "[3/3] Démarrage du Sovereign Core..."
java -jar build/libs/sigint-core-all.jar
