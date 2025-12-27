plugins {
    // Versions verrouillées pour la stabilité sur Codespace
    kotlin("jvm") version "1.8.20" 
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.fardc.sigint"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}

tasks.shadowJar {
    archiveBaseName.set("sigint-core")
    archiveClassifier.set("all")
    manifest {
        // Point d'entrée vers la classe principale compilée
        attributes["Main-Class"] = "com.fardc.sigint.core.MainKt"
    }
}
