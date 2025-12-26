plugins {
    id 'org.jetbrains.kotlin.jvm' version '1.9.0'
    id 'com.github.johnrengelman.shadow' version '8.1.1' // Plugin pour créer le JAR complet
    id 'application'
}

group = 'com.fardc.sigint'
version = '1.0.0'

repositories {
    mavenCentral()
    maven { url 'https://jpos.org/maven' } // Dépôt spécifique pour jPOS
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib"
    
    // --- Bibliothèques Financières (ISO 8583) ---
    implementation 'org.jpos:jpos:2.1.8'
    
    // --- Réseau et Utilitaires ---
    implementation 'io.netty:netty-all:4.1.94.Final'
    implementation 'com.google.code.gson:gson:2.10.1'
    
    // --- Tests ---
    testImplementation 'org.jetbrains.kotlin:kotlin-test'
}

application {
    mainClass = 'com.fardc.sigint.core.MainKt'
}

// Configuration du JAR "Shadow" (inclut toutes les dépendances)
shadowJar {
    archiveBaseName.set('sigint-core-all')
    archiveClassifier.set('')
    archiveVersion.set('')
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
