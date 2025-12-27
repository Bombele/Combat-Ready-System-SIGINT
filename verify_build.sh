#!/bin/bash
# Script de vérification de compilation Gradle 9

echo "🧹 Nettoyage du cache..."
./gradlew clean

echo "🚀 Lancement de la compilation avec analyse des dépréciations..."
# --warning-mode all permet de voir ce qu'il faudra corriger pour Gradle 10
./gradlew build --warning-mode all --stacktrace

if [ $? -eq 0 ]; then
    echo "🎉 SUCCESS: Le projet est opérationnel sous Gradle 9."
else
    echo "❌ ERROR: La compilation a échoué. Vérifie les logs."
    exit 1
fi
