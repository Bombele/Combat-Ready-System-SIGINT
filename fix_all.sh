# 1. Suppression de la version incomplète du script
rm -f fix_all.sh

# 2. Création de la version opérationnelle
cat << 'EOF' > fix_all.sh
#!/bin/bash
echo "🚀 Démarrage de la reconstruction souveraine..."

# Nettoyage des conflits
rm -rf .gradle build gradlew gradle/ settings.gradle.kts build.gradle.kts

# Configuration du noyau
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

# Téléchargement forcé de Gradle 8.2 pour briser le cycle Gradle 9
echo "📥 Acquisition du moteur Gradle 8.2..."
wget https://services.gradle.org/distributions/gradle-8.2-bin.zip -P /tmp
unzip -o -d /tmp /tmp/gradle-8.2-bin.zip

# Initialisation du Wrapper local
/tmp/gradle-8.2/bin/gradle wrapper --gradle-version 8.2

# Lancement de la compilation avec isolation totale
chmod +x gradlew
./gradlew shadowJar --no-daemon -Porg.gradle.java.installations.auto-detect=false
EOF

# 3. Lancement du processus
chmod +x fix_all.sh
./fix_all.sh
