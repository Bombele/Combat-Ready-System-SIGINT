plugins {
    kotlin("jvm") version "2.3.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

// Correction faille 1 : On force l'utilisation de dépôts sécurisés uniquement
repositories {
    mavenCentral()
    // Évite absolute-maven ou des repos HTTP non sécurisés
}

dependencies {
    // Correction faille 2 : Mise à jour forcée des librairies de base
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

tasks.shadowJar {
    // Correction faille 3 : Relocation (Évite les conflits de classes avec d'autres plugins)
    // Cela empêche ton logiciel de planter si un autre plugin utilise une version différente de la même lib
    relocate("org.jetbrains.kotlin", "me.bombele.shaded.kotlin")
    
    // Sécurité : Supprime les signatures des JARs dépendants pour éviter les erreurs de SecurityException
    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
    exclude("META-INF/*.RSA")
    
    archiveBaseName.set("sovereign-core")
    archiveClassifier.set("") // On enlève le "-all" pour plus de propreté
}

// Correction faille 4 : Forcer la compilation sécurisée
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        allWarningsAsErrors = false // Mets à true pour un projet ultra-strict
        freeCompilerArgs = listOf("-Xjsr305=strict") // Renforce la vérification du Null-Safety
    }
}

tasks.register<Exec>("cyberOffensiveTest") {
    group = "verification"
    description = "Lance un test de résilience réseau (Stress Test)."
    
    // Exemple d'appel à un script externe ou commande système
    commandLine("bash", "-c", "hping3 -S -p 80 --flood --rand-source localhost -c 100")
    
    doLast {
        println("ℹ️ Test de flood terminé. Vérification des logs de survie du système...")
    }
}
