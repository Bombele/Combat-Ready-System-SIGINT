cat << 'EOF' > fix_all.sh
#!/bin/bash
echo "🚀 Démarrage de la réparation souveraine..."

# 1. Nettoyage radical
rm -rf .gradle build gradlew gradle/ settings.gradle.kts build.gradle.kts

# 2. Création des fichiers de configuration stabilisés
echo 'rootProject.name = "SOVEREIGN-CORE-PSC"' > settings.gradle.kts

cat << 'EOG' > build.gradle.kts
plugins {
    kotlin("jvm") version "1.8.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}
repositories { mavenCentral() }
dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}
tasks.shadowJar {
    archiveBaseName.set("sigint-core")
    archiveClassifier.set("all")
    manifest { attributes["Main-Class"] = "com.fardc.sigint.core.MainKt" }
}
EOG

# 3. Téléchargement direct du binaire Gradle 8.2 (pour contourner la version 9.2.1)
echo "📥 Téléchargement de Gradle 8.2..."
wget https://services.gradle.org/distributions/gradle-8.2-bin.zip -P /tmp
unzip -d /tmp /tmp/gradle-8.2-bin.zip

# 4. Utilisation du Gradle 8.2 téléchargé pour créer le wrapper local
/tmp/gradle-8.2/bin/gradle wrapper --gradle-version 8.2

# 5. Finalisation
chmod +x gradlew
echo "✅ Système réparé. Lancement de la compilation..."
./gradlew shadowJar --no-daemon
EOF

# Lancement du script
chmod +x fix_all.sh
./fix_all.sh
