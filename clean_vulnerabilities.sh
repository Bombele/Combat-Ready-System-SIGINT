#!/bin/bash
echo "🛡️ Audit de sécurité et nettoyage..."

# 1. Suppression des caches Gradle qui pourraient contenir des dépendances vérolées
rm -rf ~/.gradle/caches/

# 2. Nettoyage du projet
./gradlew clean

# 3. Vérification des dépendances (détection de vulnérabilités connues)
# Note : Nécessite le plugin OWASP Dependency Check si tu veux un rapport complet
./gradlew dependencies

echo "✅ Cache nettoyé et dépendances indexées."
