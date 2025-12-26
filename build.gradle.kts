plugins {
    // Utilisation d'une version stable compatible avec Gradle 8.2
    kotlin("jvm") version "1.8.20" 
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

// ... reste du code précédemment fourni ...

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "com.fardc.sigint.core.MainKt"
    }
}
