plugins {
    // Versions synchronisées pour éviter l'erreur SelfResolvingDependency
    kotlin("jvm") version "1.8.20" 
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.fardc.sigint"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    // Bibliothèques minimales pour la communication entre Kotlin et Python
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.shadowJar {
    archiveBaseName.set("sigint-core")
    archiveClassifier.set("all")
    
    manifest {
        // Définit le point d'entrée pour l'exécution du .jar
        attributes["Main-Class"] = "com.fardc.sigint.core.MainKt"
    }
}
