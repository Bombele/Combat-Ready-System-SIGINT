#!/bin/bash
# Script opérationnel pour SOVEREIGN-CORE-PSC

echo "🚀 Phase 1 : Compilation du Core..."
./gradlew clean shadowJar

echo "🚀 Phase 2 : Déploiement du module de défense active..."
# On lance le JAR en passant l'argument 'cyber-ops' (si ton code le gère)
java -jar build/libs/sovereign-core.jar --mode=offensive &
PID=$!

echo "🚀 Phase 3 : Lancement des vecteurs de test (Nmap Audit)..."
# Scan de vulnérabilité sur ton propre service pour voir ce qu'il expose
nmap -sV --script=banner localhost -p 8080,8443 > build/reports/offensive/audit.log

echo "📊 Audit terminé. Rapport disponible dans build/reports/offensive/audit.log"

# Arrêt du service de test après audit
kill $PID
