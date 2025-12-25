#!/bin/bash

# =============================================================================
# SRC - FINAL DEPLOYMENT & CLEANUP SCRIPT
# Prépare le dépôt pour une publication sécurisée.
# =============================================================================

BLUE='\033[0;34m'
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}=== [SRC] PRÉPARATION DU DÉPÔT GITHUB ===${NC}"

# 1. Nettoyage des données sensibles locales
echo -e "[1/4] Suppression des fichiers temporaires et logs..."
rm -rf data/logs/*.log
rm -rf data/audit/*.json
rm -rf data/keys/*.key
rm -rf bin/
rm -rf build/
echo -e "${GREEN}[OK] Nettoyage effectué.${NC}"

# 2. Vérification de la structure conforme à la Matrix
echo -e "[2/4] Vérification de l'arborescence critique..."
REQUIRED_DIRS=("sigint/comint" "sigint/elint" "sigint/fisint" "sigint/audit" "core/security" "core/sync")

for dir in "${REQUIRED_DIRS[@]}"; do
    if [ -d "$dir" ]; then
        echo -e "${GREEN}[CONF] $dir est présent.${NC}"
    else
        echo -e "${RED}[ERREUR] Dossier manquant : $dir${NC}"
        exit 1
    fi
done

# 3. Création du .gitignore final
echo -e "[3/4] Mise à jour du .gitignore..."
cat <<EOF > .gitignore
# Build and binaries
bin/
build/
*.jar
*.class

# Mission Data (NE JAMAIS POUSSER)
data/logs/
data/audit/
data/keys/
data/maps/*.mbtiles

# System
.DS_Store
.idea/
.gradle/
EOF

# 4. Finalisation du README
echo -e "[4/4] Validation finale du README.md..."
if [ -f "README.md" ]; then
    echo -e "${GREEN}[OK] Documentation prête.${NC}"
else
    echo -e "${RED}[ATTENTION] README.md manquant !${NC}"
fi

echo -e "\n${BLUE}==========================================================${NC}"
echo -e "${GREEN}🚀 TON PROJET EST PRÊT POUR LE PUSH FINAL !${NC}"
echo -e "Commande suggérée : git add . && git commit -m 'Release v1.0-Combat-Ready' && git push"
echo -e "${BLUE}==========================================================${NC}"
