#!/bin/bash

# =============================================================================
# SRC - SCRIPT DE DÉPLOIEMENT TACTIQUE
# Objectif : Compiler, Durcir et Packager le système pour le terrain.
# =============================================================================

set -e # Arrêt immédiat en cas d'erreur

# Couleurs pour le terminal
BLUE='\033[0;34m'
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}=== [DÉPLOIEMENT SYSTÈME SRC] ===${NC}"

# 1. Nettoyage des anciennes builds et des logs de test
echo -e "[1/5] Nettoyage de l'environnement..."
rm -rf build/
mkdir -p build/dist
rm -f core/audit/logs/*.log

# 2. Compilation de l'ossature complète (Kotlin/Java)
echo -e "[2/5] Compilation des modules (SIGINT, BFT, CORE)..."
# Note : kotlinc est utilisé ici pour compiler tout le projet vers un JAR exécutable
kotlinc src/*.kt core/**/*.kt bft/**/*.kt sigint/*.kt services/**/*.kt ui/**/*.kt \
        -include-runtime -d build/dist/sigint-engine.jar

# 3. Application du Hardening (Obscurcissement & Intégrité)
echo -e "[3/5] Durcissement du binaire..."
# On simule l'appel au script de hardening que nous avons déjà défini
./scripts/harden_binary.sh build/dist/sigint-engine.jar

# 4. Signature du Package
echo -e "[4/5] Signature cryptographique du déploiement..."
sha256sum build/dist/sigint-engine.jar > build/dist/checksum.txt
echo -e "${GREEN}[OK] Checksum généré.${NC}"

# 5. Préparation de la structure de données de terrain
echo -e "[5/5] Packaging des ressources critiques..."
cp -r data/ build/dist/
cp docs/INSTALL.md build/dist/

echo -e "\n${GREEN}=== [DÉPLOIEMENT RÉUSSI] ===${NC}"
echo -e "Le paquet prêt pour le terrain se trouve dans : ${BLUE}build/dist/${NC}"
echo -e "Prêt à être transféré sur le terminal tactique via USB blindée."
