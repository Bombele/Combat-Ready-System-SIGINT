# 📘 Manuel Opérationnel – Combat-Ready System SIGINT

## 1. Introduction
Objectif : fournir un système SIGINT/ELINT combat-ready, modulaire, auditable et certifiable.
Contexte : conçu pour une unité mobile de guerre électronique, avec résilience en conditions dégradées.
Valeur stratégique : souveraineté nationale, transmission institutionnelle, alignement avec normes OTAN/ITU.

## 2. Architecture générale (mise à jour)

Modules principaux du dépôt :

- core/ : moteur central, sécurité, gestion des flux
- sigint/ : capture, classification et géolocalisation des signaux
- infra/ : transmission sécurisée, cyber résilience, fallback multi-canal
- specs/ : documentation technique, matrices de conformité, operational_spec
- docs/ : SOP, manuels opérateurs, rapports institutionnels
- data/ : signatures radio, bases de menaces (fardc_threat_db.json, rdc_rebel_nets.json), cartes offline (MBTiles)
- services/ : IA embarquée (SignalClassifier.kt, AnomalyDetector.kt), mesh et DSP
- integration/ : description des modules d’intégration et interfaces inter-systèmes
- tests/ : scénarios de test (panic_wipe_test.md, validation geofence, etc.)
- ui/ : interface opérateur (StatusHUD.kt, affichage terrain)
- makefile/ : scripts de compilation et déploiement
- Military License : licence spécifique pour usage militaire
- system_index.md : index global du système pour navigation et audit
- SIGINT_System_Manual.md : manuel technique du système
- manuel_op : manuel opérationnel en cours de rédaction

Flux opérationnel :
1. Capture radio (RTL-SDR, HackRF) via sigint/
2. Classification IA embarquée (TensorFlow Lite) via services/
3. Géolocalisation (TDOA/AoA) via fusion_geo/
4. Transmission sécurisée (mesh, SMS chiffré, HF analogique) via infra/
5. Audit et documentation automatique via specs/ et MissionLogger
6. Interface opérateur via ui/ pour retour terrain

## 3. Procédures Opérationnelles (SOP) – Mise à jour

### Mise en route
- Vérifier matériel : SDR (RTL-SDR, HackRF), antennes, batteries.
- Initialiser core/ et charger clés ZeroTrust.
- Vérifier disponibilité des bases de menaces dans data/ (fardc_threat_db.json, rdc_rebel_nets.json).
- Lancer l’interface opérateur (ui/StatusHUD.kt) pour affichage terrain.

### Capture et classification
- Activer module sigint/ pour interception radio.
- IA embarquée (services/SignalClassifier.kt via TensorFlow Lite) identifie modulation et type de signal (FM, AM, DMR, etc.).
- Détection anomalies via services/AnomalyDetector.kt.
- Géolocalisation via fusion_geo/ (TDOA/AoA).

### Transmission
- Priorité : réseau mesh sécurisé via infra/ et core/sync/WifiDirectAdapter.kt.
- Fallback : SMS chiffré ou HF analogique.
- Intégration automatique des ThreatMessage dans MeshSyncEngine.

### Sécurité
- Activer PanicHandler en cas de compromission.
- SensitiveStore.wipeAll() pour effacement complet ou Internal DoS.
- GeofenceManager : effacement automatique des clés hors zone autorisée.
- MissionLogger (core/audit/) : journal de mission enchaîné pour Evidence Mode.

### Audit et validation
- Vérification de l’intégrité des logs via MissionLogger.verifyIntegrity().
- Transmission des journaux chiffrés vers état-major.
- Tests de validation disponibles dans tests/ (panic_wipe_test.md, geofence validation).

### Interface opérateur
- Utiliser ui/StatusHUD.kt pour visualiser :
  - Statut du signal intercepté.
  - Alertes de zone (Geofence).
  - Logs critiques (Evidence Mode).
  - Messages Mesh reçus des camarades.

## 4. Sécurité et Résilience – Mise à jour

### SensitiveStore
- Fonction : effacement complet des données sensibles.
- Si l’effacement échoue → déclenche Internal DoS (corruption massive + CPU Burn + extinction forcée).
- Valeur : garantit la non-récupération absolue des secrets militaires.

### PanicHandler
- Fonction : autodestruction immédiate en cas de compromission ou capture.
- Intégration : appelé par TacticalWipeManager.
- Valeur : neutralise l’appareil avant exploitation par l’ennemi.

### GeofenceManager
- Fonction : contrôle géographique via fichiers .poly.
- Si l’opérateur sort du périmètre autorisé (ex. Goma) → déclenche wipe automatique.
- Valeur : protection dynamique contre capture en zone rebelle.

### MissionLogger
- Fonction : journal de mission avec hachage enchaîné (Evidence Mode).
- Vérification : verifyIntegrity() détecte toute falsification.
- Valeur : preuve auditable et certifiable, utilisable en débriefing et tribunal militaire.

### ZeroTrustManager
- Fonction : chiffrement bout-en-bout des communications et des données.
- Valeur : aucune confiance implicite, chaque transaction est validée cryptographiquement.

### Cyber_resilience
- Fonction : redondance multi-canal (mesh, SMS, HF analogique).
- Autodiagnostic : surveillance continue de l’intégrité système.
- Valeur : maintien opérationnel même en conditions dégradées.

## 5. Intelligence Artificielle embarquée – Mise à jour

### SignalClassifier (services/SignalClassifier.kt)
- Fonction : identifier automatiquement les modulations radio (FM, AM, DMR, etc.).
- Technologie : TensorFlow Lite, optimisé pour terminaux tactiques à ressources limitées.
- Usage : chaque détection est loggée via MissionLogger (Evidence Mode).
- Valeur : transforme les ondes brutes en informations exploitables pour l’opérateur.

### AnomalyDetector (services/AnomalyDetector.kt)
- Fonction : détecter des transmissions suspectes ou inconnues.
- Méthodes : Isolation Forest, autoencoder léger.
- Usage : alerte immédiate envoyée via MeshSyncEngine.
- Valeur : identification proactive des menaces non répertoriées dans data/fardc_threat_db.json.

### Flux opérationnel IA
1. Capture radio via sigint/
2. SignalClassifier → classification modulation
3. AnomalyDetector → détection anomalies
4. MissionLogger → enregistrement sécurisé (hachage enchaîné)
5. WifiDirectAdapter → transmission ThreatMessage aux unités proches
6. UI (StatusHUD.kt) → affichage en temps réel pour l’opérateur

### Valeur opérationnelle (FARDC)
- **Réactivité** : classification et détection en temps réel.
- **Automatisation** : réduit la charge cognitive des opérateurs.
- **Auditabilité** : chaque détection est enregistrée et vérifiable.
- **Interopérabilité** : résultats intégrés dans MeshSyncEngine pour diffusion immédiate.

## 6. Formation et Certification – Mise à jour

### Documentation institutionnelle
- docs/ : contient les SOP (Standard Operating Procedures), manuels opérateurs, et guides de formation.
- specs/ : matrices de conformité, operational_spec, et documentation technique pour audit.
- SIGINT_System_Manual.md : manuel technique complet du système.
- manuel_op : manuel opérationnel en cours de rédaction (ce document).

### Manuel opérateur
- Procédures simplifiées pour usage terrain.
- Instructions pas-à-pas pour capture, classification, transmission et wipe.
- Interface UI (StatusHUD.kt) pour retour visuel immédiat.

### Compliance matrix
- Alignement avec normes OTAN et ITU.
- Vérification des exigences de sécurité, transmission et auditabilité.
- Documentation prête pour certification institutionnelle.

### Certification
- Audit interne via MissionLogger (Evidence Mode).
- Audit externe via specs/ et system_index.md.
- Transmission trilingue (FR/EN/ES) pour adoption continentale.

### Valeur opérationnelle (FARDC)
- Formation rapide des opérateurs grâce aux SOP et manuels simplifiés.
- Certification institutionnelle facilitée par documentation complète et auditable.
- Transmission et adoption au-delà du cadre national (continentalisation).

## 7. SensitiveStore.kt – Internal DoS

### Objectif
Garantir la non-récupération absolue des données sensibles en cas de compromission.
Si l’effacement classique échoue (fichiers verrouillés, erreurs I/O), le système déclenche un Déni de Service Interne (Internal DoS).

### Fonctionnement
1. Effacement récursif :
   - Suppression des répertoires critiques :
     - data/signatures/
     - core/audit/logs/
     - data/reports/
     - data/keys/

2. Internal DoS :
   - Corruption massive : écriture en boucle de fichiers temporaires pour saturer le contrôleur de stockage.
   - CPU Burn : tous les cœurs sollicités à 100% → épuisement batterie et extinction forcée.
   - Notification critique : message d’alerte affiché avant extinction.
   - Exit Process : arrêt brutal du système après saturation.

### Intégration
- TacticalWipeManager appelle SensitiveStore.wipeAll().
- Si wipeAll() retourne false → Internal DoS est lancé automatiquement en arrière-plan.

### Valeur opérationnelle (FARDC)
- Garantie de non-récupération : même si l’ennemi bloque la suppression, les données deviennent irrécupérables.
- Réactivation du chiffrement matériel : extinction forcée → Full Disk Encryption reprend au redémarrage.
- Priorité maximale : les threads de sécurité passent avant toute tentative d’accès externe.
- Effet dissuasif : l’appareil devient une “brique” inutilisable, empêchant toute exploitation.

## 8. GeofenceManager – Sécurité géographique

### Objectif
Empêcher l’utilisation du système SIGINT en dehors d’une zone de mission autorisée.
Si l’opérateur sort du périmètre défini, le TacticalWipeManager déclenche automatiquement l’effacement des données sensibles.

### Zone de mission : Goma (Nord-Kivu)
- Fichier actif : core/security/active_geofence.poly
- Coordonnées du polygone :
  - A : (-1.6393, 29.1947) – Entrée Nord de Goma (axe Rutshuru)
  - B : (-1.6385, 29.2554) – Frontière Rwanda (Grande Barrière)
  - C : (-1.6888, 29.2562) – Port de Goma
  - D : (-1.7082, 29.2155) – Péninsule du lac Kivu
  - E : (-1.6853, 29.1824) – Aéroport/Sake
  - Fermeture : retour au point A pour boucler la géométrie

### Procédures de test
Script Kotlin rapide :
```kotlin
fun main() {
    GeofenceManager.loadGeofence("core/security/active_geofence.poly")

    // Test 1 : Centre de Goma → INSIDE
    val inGoma = GeofenceManager.isInAuthorizedZone(-1.6666, 29.2222)
    println("Position Goma Centre : ${if (inGoma) "AUTORISÉE" else "ALERTE WIPE"}")

    // Test 2 : Sake → OUTSIDE
    val inSake = GeofenceManager.isInAuthorizedZone(-1.6067, 29.0722)
    println("Position Sake : ${if (inSake) "AUTORISÉE" else "ALERTE WIPE"}")
}

## 9. MissionLogger.kt – Evidence Mode

### Objectif
Garantir l’intégrité et la non-falsification des journaux de mission.
Chaque événement est enregistré avec un hachage enchaîné (chained hashing).
Toute modification ou suppression brise la chaîne et est immédiatement détectée.

### Fonctionnement
1. Hachage SHA-256 :
   - Chaque entrée contient : HASH_ACTUEL | LEVEL | EVENT | TIMESTAMP | HASH_PRECEDENT.
   - Le hash est recalculé à partir du contenu + hash précédent.

2. RecoverLastHash() :
   - Au redémarrage, le système retrouve le dernier hash valide pour continuer la chaîne.
   - En cas de corruption, la chaîne est marquée comme compromise.

3. verifyIntegrity() :
   - Vérifie l’intégrité complète du journal.
   - Retourne false si une falsification est détectée.

### Exemple d’utilisation
```kotlin
MissionLogger.info("SIGINT capture: fréquence 29.222 MHz")
MissionLogger.critical("WIPE_TRIGGERED: sortie de zone Goma")
val integrityOk = MissionLogger.verifyIntegrity()
println("Intégrité du journal : ${if (integrityOk) "OK" else "COMPROMIS"}")

## 10. WifiDirectAdapter.kt – Communication Mesh

### Objectif
Permettre aux unités SIGINT de communiquer directement entre elles, sans dépendre d’infrastructures civiles (antennes relais, routeurs).
Le Wi-Fi Direct (P2P) crée un réseau maillé autonome, idéal pour les opérations en forêt, montagne ou zones hostiles.

### Fonctionnement
1. Découverte des pairs :
   - manager.discoverPeers() recherche automatiquement les unités SIGINT à proximité.
   - Portée typique : ~200m en terrain dégagé.

2. Serveur d’écoute :
   - ServerSocket(8888) reçoit les messages entrants.
   - Les données reçues sont sérialisées (JSON/CBOR) en ThreatMessage.

3. Envoi de messages :
   - send(msg: ThreatMessage) diffuse les alertes et données aux pairs connectés.
   - Format binaire compact pour minimiser la bande passante.

4. Arrêt et gestion :
   - stop() interrompt la découverte des pairs.
   - receive(handler) permet de traiter les messages reçus et les intégrer dans l’UI ou la géolocalisation.

### Valeur opérationnelle (FARDC)
- Indépendance totale : pas besoin de routeur ni d’antenne → communication directe entre soldats.
- Discrétion : émission intermittente → réduit la probabilité de détection par l’ennemi.
- Vitesse : transfert rapide de fichiers lourds (extraits audio, spectrogrammes).
- Résilience : chaque appareil devient un nœud du mesh → pas de point unique de défaillance.

### Procédures associées
- Activation : lancer WifiDirectAdapter.start() au début de la mission.
- Transmission : utiliser send() pour partager menaces ou logs.
- Réception : configurer receive(handler) pour traiter les alertes en temps réel.
- Arrêt : exécuter stop() en fin de mission ou lors d’un wipe.

## 11. SignalClassifier.kt – IA embarquée

### Objectif
Donner une intelligence embarquée au système : transformer les ondes radio interceptées en informations exploitables.
Le module SignalClassifier utilise TensorFlow Lite pour classifier automatiquement les modulations (FM, AM, DMR, etc.).

### Fonctionnement
1. Entrée : spectrogramme ou flux brut capturé par sigint/.
2. Traitement : modèle TensorFlow Lite optimisé pour terminaux tactiques.
3. Sortie : type de modulation identifié (FM, AM, DMR, etc.).
4. Intégration : chaque détection est loggée via MissionLogger (Evidence Mode).
5. Transmission : alertes envoyées automatiquement aux unités via WifiDirectAdapter.

### AnomalyDetector
- Fonction : détecter transmissions suspectes ou inconnues.
- Méthodes : Isolation Forest / autoencoder léger.
- Usage : alerte immédiate envoyée via MeshSyncEngine.
- Valeur : identification proactive des menaces non répertoriées dans data/fardc_threat_db.json.

### Flux opérationnel IA
1. Capture radio via sigint/
2. SignalClassifier → classification modulation
3. AnomalyDetector → détection anomalies
4. MissionLogger → enregistrement sécurisé (hachage enchaîné)
5. WifiDirectAdapter → transmission ThreatMessage aux unités proches
6. UI (StatusHUD.kt) → affichage en temps réel pour l’opérateur

### Valeur opérationnelle (FARDC)
- Réactivité : classification et détection en temps réel.
- Automatisation : réduit la charge cognitive des opérateurs.
- Auditabilité : chaque détection est enregistrée et vérifiable.
- Interopérabilité : résultats intégrés dans MeshSyncEngine pour diffusion immédiate.

## 12. Annexes – Glossaire, Index système, Changelog

### Glossaire
- SIGINT : Signals Intelligence (renseignement d’origine électromagnétique).
- ELINT : Electronic Intelligence (renseignement électronique).
- Evidence Mode : mode de journalisation inviolable basé sur hachage enchaîné.
- TacticalWipeManager : module déclenchant l’effacement automatique des données sensibles.
- ThreatMessage : format standardisé pour transmettre alertes et données via MeshSyncEngine.
- MeshSyncEngine : moteur de communication maillée basé sur Wi-Fi Direct.
- Internal DoS : mécanisme de corruption et saturation interne pour rendre l’appareil inutilisable.
- Geofence : périmètre géographique autorisé pour l’utilisation du système.
- ZeroTrust : modèle de sécurité où chaque transaction est validée cryptographiquement.

### Index système
- core/ : moteur central, sécurité, gestion des flux.
- sigint/ : capture et classification des signaux.
- infra/ : transmission sécurisée, cyber résilience.
- specs/ : documentation technique et matrices de conformité.
- docs/ : SOP, manuels opérateurs, rapports institutionnels.
- data/ : signatures radio, bases de menaces, cartes offline.
- services/ : IA embarquée (SignalClassifier, AnomalyDetector), DSP.
- integration/ : interfaces inter-systèmes.
- tests/ : scénarios de validation (panic wipe, geofence).
- ui/ : interface opérateur (StatusHUD).
- SIGINT_System_Manual.md : manuel technique.
- manuel_op : manuel opérationnel (ce document).

### Changelog
- v1.0 : Initialisation du manuel (Introduction, Architecture, SOP).
- v1.1 : Ajout SensitiveStore (Internal DoS).
- v1.2 : Ajout GeofenceManager (sécurité géographique).
- v1.3 : Ajout MissionLogger (Evidence Mode).
- v1.4 : Ajout WifiDirectAdapter (communication mesh).
- v1.5 : Ajout SignalClassifier + AnomalyDetector (IA embarquée).
- v1.6 : Mise à jour Architecture et SOP avec modules data, services, ui, tests.
- v1.7 : Ajout Annexes (Glossaire, Index système, Changelog).