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
kotlin
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
kotlin
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

## 12. Modes opérationnels – SIGINT Combat-Ready

Ce chapitre regroupe tous les profils de mission disponibles dans le système SIGINT combat-ready.
Chaque mode est conçu pour répondre à un contexte opérationnel spécifique et active/désactive des modules précis.

### 🔒 Fallback Mode – Détail complet

#### Objectif
Assurer la transmission même en cas de perte totale de réseau civil ou militaire.
Ce mode garantit que les unités SIGINT peuvent continuer à échanger des informations critiques
même lorsque toutes les infrastructures classiques (antenne relais, routeur, Internet) sont indisponibles.

#### Modules actifs
- core/sync/MeshSyncEngine.kt  
  → moteur de communication maillée, basé sur Wi-Fi Direct, permettant la création d’un réseau P2P autonome.  
- services/transmission/FallbackTransmitter.kt  
  → module de transmission de secours, capable d’utiliser des canaux alternatifs (SMS chiffré, HF analogique, ou tout support disponible).

#### SOP associée
- docs/SOP/transmission_SOP.md  
  → décrit les procédures standard pour activer le mode fallback, tester la continuité de transmission,
  et valider la réception des ThreatMessage en conditions dégradées.

#### Valeur opérationnelle (FARDC)
- **Résilience totale** : communication maintenue même en cas de brouillage ou destruction des infrastructures.  
- **Continuité de mission** : aucune perte de données critiques, même en environnement hostile.  
- **Interopérabilité** : permet aux unités de fusionner leurs données sans dépendre d’un point central.  
- **Institutionnalisation** : SOP documentée et intégrée dans le manuel, prête pour adoption officielle.

### 🔋 Low-Power Mode – Détail complet

#### Objectif
Économiser l’énergie en mission longue durée, lorsque les unités SIGINT doivent rester opérationnelles
pendant plusieurs jours sans possibilité de recharge immédiate.  
Ce mode optimise la consommation électrique tout en maintenant les fonctions essentielles.

#### Modules actifs
- core/power/LowPowerManager.kt  
  → module de gestion énergétique, réduit la fréquence CPU, désactive les capteurs non critiques et optimise les cycles de transmission.  
- ui/tactical/NightVisionTheme.kt  
  → interface visuelle adaptée aux opérations nocturnes, faible luminosité pour limiter la consommation et préserver la discrétion.

#### SOP associée
- docs/SOP/power_SOP.md  
  → décrit les procédures standard pour activer le mode basse consommation, vérifier l’autonomie restante,
  et ajuster les priorités de mission en fonction de l’énergie disponible.

#### Valeur opérationnelle (FARDC)
- **Autonomie prolongée** : permet de maintenir les opérations sur plusieurs jours en terrain hostile.  
- **Discrétion visuelle** : interface adaptée à la vision nocturne, réduit la signature lumineuse.  
- **Optimisation tactique** : désactive les modules non essentiels pour concentrer l’énergie sur la capture et la transmission critique.  
- **Survie opérationnelle** : garantit que même avec une batterie faible, les fonctions vitales (SIGINT, transmission) restent actives.

### 🕶️ Silent Ops Mode – Détail complet

#### Objectif
Permettre des opérations discrètes en limitant au maximum les traces numériques et visuelles.
Ce mode est conçu pour les missions où la furtivité est prioritaire : infiltration, observation, ou collecte
sans alerter l’ennemi.

#### Modules actifs
- ui/tactical/LowLightRenderer.kt  
  → interface visuelle adaptée aux environnements nocturnes, faible luminosité pour réduire la signature visuelle.  
- core/audit/MissionLogger.kt (journal minimal)  
  → enregistre uniquement les événements critiques, afin de limiter les traces exploitables tout en conservant une traçabilité minimale.

#### SOP associée
- docs/SOP/silent_ops_SOP.md  
  → décrit les procédures standard pour activer le mode Silent Ops, ajuster la luminosité de l’interface,
  et vérifier la journalisation minimale en Evidence Mode réduit.

#### Valeur opérationnelle (FARDC)
- **Furtivité numérique** : réduit la quantité de données générées et stockées.  
- **Discrétion visuelle** : interface adaptée aux opérations nocturnes, minimisant la détection par observation directe.  
- **Sécurité opérationnelle** : journalisation minimale, mais suffisante pour conserver une preuve en cas de débriefing.  
- **Adaptabilité tactique** : idéal pour missions d’infiltration ou observation prolongée sans révéler la présence SIGINT.

### 📑 Evidence Mode – Détail complet

#### Objectif
Collecter et tracer toutes les données critiques de mission avec un niveau de sécurité et de certification maximal.  
Ce mode est conçu pour les opérations où la preuve et l’auditabilité sont prioritaires : débriefing, certification institutionnelle, ou présentation devant état-major.

#### Modules actifs
- core/audit/MissionLogger.kt  
  → journal complet, chiffré et signé, basé sur hachage enchaîné (Evidence Mode).  
- data/reports/anomaly_report.md  
  → rapport automatique des anomalies détectées, intégré dans la documentation pour audit.

#### SOP associée
- docs/SOP/evidence_SOP.md  
  → décrit les procédures standard pour activer le mode Evidence, vérifier l’intégrité des journaux,
  et transmettre les rapports chiffrés à l’état-major ou aux instances de certification.

#### Valeur opérationnelle (FARDC)
- **Traçabilité totale** : chaque événement est enregistré, chiffré et signé.  
- **Auditabilité** : logs inviolables utilisables comme preuve devant tribunal militaire ou certification OTAN.  
- **Institutionnalisation** : documentation complète et prête pour adoption officielle.  
- **Débriefing renforcé** : permet d’analyser chaque étape de la mission avec preuves vérifiables.

### 🛰️ Fusion & Géolocalisation Mode – Détail complet

#### Objectif
Localiser précisément un émetteur ennemi par triangulation et fusion de données SIGINT.  
Ce mode est conçu pour les opérations de repérage et neutralisation, en combinant plusieurs capteurs et unités pour obtenir une position exacte.

#### Modules actifs
- comint/geo/TDOA_Engine.kt  
  → moteur de calcul basé sur Time Difference of Arrival (TDOA), permettant la triangulation des signaux interceptés.  
- fusion_geo/  
  → module de fusion géospatiale, combine les données de plusieurs unités pour améliorer la précision de localisation.

#### SOP associée
- docs/SOP/fusion_geo_SOP.md  
  → décrit les procédures standard pour activer le mode fusion, synchroniser les unités SIGINT,
  et valider la triangulation par comparaison avec les cartes offline.

#### Valeur opérationnelle (FARDC)
- **Précision tactique** : localisation exacte des émetteurs ennemis, même en environnement complexe.  
- **Coordination multi-unités** : fusion des données de plusieurs opérateurs pour renforcer la fiabilité.  
- **Support direct aux opérations** : fournit des coordonnées exploitables pour neutralisation ou interception.  
- **Institutionnalisation** : SOP documentée, prête pour adoption officielle et certification.

### 🤖 IA Locale – Anomaly Detection Mode – Détail complet

#### Objectif
Identifier automatiquement les comportements radio suspects ou non répertoriés dans les bases de menaces.  
Ce mode permet une détection proactive des transmissions anormales, réduisant le temps de réaction des unités SIGINT.

#### Modules actifs
- services/dsp/ai_inference/AnomalyDetector.kt  
  → moteur d’inférence IA embarqué, basé sur Isolation Forest et autoencoder léger, optimisé pour terminaux tactiques.  
- data/signatures/anomalies.json  
  → base de signatures d’anomalies connues, enrichie en continu par les retours terrain et les détections IA.

#### SOP associée
- docs/SOP/anomaly_SOP.md  
  → décrit les procédures standard pour activer le mode Anomaly Detection, valider les alertes générées par l’IA,
  et transmettre les rapports aux unités voisines via MeshSyncEngine.

#### Valeur opérationnelle (FARDC)
- **Détection proactive** : identification des menaces non répertoriées dans les bases classiques.  
- **Réactivité accrue** : alerte immédiate transmise aux unités proches pour action rapide.  
- **Auditabilité** : chaque anomalie détectée est enregistrée dans MissionLogger (Evidence Mode).  
- **Institutionnalisation** : SOP documentée, prête pour adoption officielle et certification.

### Valeur opérationnelle (FARDC)
- Flexibilité : chaque mode correspond à un profil de mission spécifique.
- Institutionnalisation : modes documentés et reliés aux SOP pour adoption officielle.
- Sécurité : Evidence Mode et Silent Ops renforcent la traçabilité et la discrétion.
- Résilience : Fallback Mode et Low-Power Mode assurent continuité même en conditions dégradées.

## 13. Modes de combat opérationnel – Implémentation

Le système SIGINT combat-ready repose sur plusieurs modes opérationnels, chacun conçu pour répondre à un contexte tactique précis.  
Ces modes constituent la doctrine d’emploi du système et garantissent flexibilité, sécurité et efficacité sur le terrain.

---

### ### 🔒 Mode Panic Wipe – Détail complet

#### Objectif
Le Mode Panic Wipe est conçu pour protéger immédiatement toutes les données sensibles du système SIGINT en cas de compromission.  
Il s’active automatiquement lorsque l’unité sort de la zone géographique autorisée, lorsqu’une clé de détresse est saisie par l’opérateur, ou lorsqu’un sabotage matériel est détecté.  
Son rôle est de garantir qu’aucune information stratégique ne puisse tomber entre les mains adverses.

#### Modules associés
- **TacticalWipeManager.kt** : cœur du mécanisme d’effacement, orchestre la suppression des données et des clés.  
- **GeofenceManager.kt** : vérifie la position de l’unité par rapport au périmètre défini dans `active_geofence.poly`.  
- **KeyVault** : gère et détruit les clés cryptographiques (master.key, session.key).  
- **SensitiveStore** : efface les répertoires critiques (signatures radio, rapports, journaux).  
- **MissionLogger.kt** : journalise chaque déclenchement et résultat du wipe en mode Evidence.

#### Procédures de déclenchement
1. **Sortie de zone (Geofence)** : si l’opérateur franchit le périmètre défini, le wipe est déclenché.  
2. **Clé de détresse** : saisie manuelle par l’opérateur en cas de capture imminente.  
3. **Tamper matériel** : détection d’une tentative de sabotage ou d’ouverture non autorisée du dispositif.  

#### SOP associée
- **docs/SOP/panicwipeSOP.md** : décrit les étapes d’activation, les tests de déclenchement et les vérifications post-effacement.  
- Inclut les scénarios de simulation pour valider la robustesse du mécanisme.

#### Valeur opérationnelle (FARDC)
- **Neutralisation immédiate** : aucune donnée exploitable ne subsiste après déclenchement.  
- **Sécurité stratégique** : protège les bases de signatures, clés et rapports sensibles.  
- **Traçabilité** : chaque effacement est enregistré dans MissionLogger pour audit et certification.  
- **Institutionnalisation** : SOP documentée, intégrée dans le manuel, prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : une unité SIGINT est encerclée et risque d’être capturée.  
- **Action** : l’opérateur saisit la clé de détresse.  
- **Résultat** : TacticalWipeManager efface immédiatement les clés, les signatures et les rapports, journalise l’événement, puis neutralise le système.

---

### 📑 Mode Evidence – Détail complet

#### Objectif
Le Mode Evidence est conçu pour assurer une traçabilité inviolable et certifiable de toutes les opérations critiques.  
Il garantit que chaque événement, chaque effacement et chaque détection est enregistré de manière sécurisée, chiffrée et signée.  
Ce mode est essentiel pour les missions où la preuve et l’auditabilité doivent être garanties, que ce soit pour un débriefing militaire, une certification institutionnelle ou une présentation devant un tribunal.

#### Modules associés
- **MissionLogger.kt** : journal complet basé sur hachage enchaîné et signature cryptographique.  
- **data/reports/anomaly_report.md** : rapport automatique des anomalies détectées, intégré dans la documentation pour audit.  
- **SensitiveStore & KeyVault (hooks)** : assurent que les effacements sont également tracés et certifiés.  

#### Procédures de fonctionnement
1. **Journalisation complète** : chaque événement est enregistré avec un horodatage et un hash lié au précédent.  
2. **Signature cryptographique** : chaque entrée est signée pour empêcher toute falsification.  
3. **Rotation des logs** : gestion automatique des fichiers pour éviter la surcharge et garantir la continuité.  
4. **Rapports d’anomalies** : intégration automatique des détections IA et des événements critiques dans les rapports.  

#### SOP associée
- **docs/SOP/evidence_SOP.md** : décrit les procédures pour activer le mode Evidence, vérifier l’intégrité des journaux et transmettre les rapports chiffrés à l’état-major ou aux instances de certification.  

#### Valeur opérationnelle (FARDC)
- **Traçabilité totale** : chaque action est enregistrée et vérifiable.  
- **Auditabilité inviolable** : logs utilisables comme preuve devant tribunal militaire ou certification OTAN.  
- **Débriefing renforcé** : permet d’analyser chaque étape de la mission avec preuves vérifiables.  
- **Institutionnalisation** : documentation complète et prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : une unité SIGINT intercepte une transmission suspecte.  
- **Action** : le SignalClassifier identifie la modulation et l’AnomalyDetector signale une anomalie.  
- **Résultat** : MissionLogger enregistre l’événement avec hash et signature, anomaly_report.md est généré, et l’état-major reçoit une preuve inviolable de la détection.

---

### 🌐 Mode MeshSync – Détail complet

#### Objectif
Le Mode MeshSync est conçu pour assurer la continuité des communications entre unités SIGINT, même en l’absence d’infrastructure civile ou militaire.  
Il repose sur une architecture maillée (mesh network) permettant le partage immédiat des données de menace (ThreatMessage) et la synchronisation des informations critiques.  
Ce mode est vital pour garantir la résilience des opérations en terrain hostile ou isolé.

#### Modules associés
- **MeshSyncEngine.kt** : moteur central de communication maillée, abstrait les différents transports.  
- **MessageEnvelope.kt (CBOR)** : format compact et standardisé pour encapsuler les ThreatMessage.  
- **TransportAdapter** : interface de transport adaptable (Wi‑Fi Direct, LoRa, ou autres).  
- **CRDT légère** : mécanisme de fusion des données pour éviter les conflits et assurer la cohérence entre unités.  

#### Procédures de fonctionnement
1. **Initialisation du réseau maillé** : chaque unité démarre son transport (Wi‑Fi Direct ou LoRa).  
2. **Diffusion des ThreatMessage** : les données interceptées sont encapsulées et partagées automatiquement.  
3. **Store‑and‑forward** : les messages sont stockés localement et retransmis dès qu’une connexion est disponible.  
4. **Fusion des données (CRDT)** : les informations reçues sont intégrées sans perte ni duplication.  

#### SOP associée
- **docs/SOP/transmission_SOP.md** : décrit les procédures pour activer MeshSync, tester la diffusion locale et valider la cohérence des données partagées.  

#### Valeur opérationnelle (FARDC)
- **Résilience des communications** : garantit le partage d’informations même en cas de brouillage ou destruction des infrastructures.  
- **Interopérabilité** : permet aux unités de communiquer sans dépendre d’un point central.  
- **Réactivité tactique** : diffusion immédiate des menaces détectées à toutes les unités connectées.  
- **Institutionnalisation** : SOP documentée, intégrée dans le manuel, prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : une unité SIGINT détecte une transmission suspecte en zone isolée.  
- **Action** : MeshSync encapsule la menace dans un MessageEnvelope et la diffuse via Wi‑Fi Direct.  
- **Résultat** : les unités voisines reçoivent l’alerte en temps réel, même sans réseau civil, et peuvent coordonner une réponse immédiate.

---

### 🤖 Mode IA – SignalClassifier – Détail complet

#### Objectif
Le Mode IA – SignalClassifier est conçu pour classifier automatiquement les signaux interceptés grâce à un modèle d’intelligence artificielle embarqué.  
Il permet d’identifier rapidement la modulation et la nature des transmissions radio, offrant un avantage tactique décisif en réduisant le temps nécessaire à l’analyse humaine.  
Ce mode constitue la première étape vers une détection proactive des anomalies et menaces radio.

#### Modules associés
- **SignalClassifier.kt** : charge et exécute un modèle TensorFlow Lite pour classification des spectres.  
- **Modèle IA (TFLite)** : fichier de modèle pré-entraîné, optimisé pour terminaux tactiques.  
- **Integration avec AnomalyDetector** : prépare les résultats pour être exploités par le module de détection d’anomalies.  

#### Procédures de fonctionnement
1. **Chargement du modèle IA** : vérification de la présence et de l’intégrité du fichier TFLite.  
2. **Classification des spectres** : analyse des données radio interceptées et attribution d’une modulation (ex. VHF_FM, AM, PSK).  
3. **Retour de confiance** : chaque classification est accompagnée d’un score de confiance.  
4. **Transmission des résultats** : les données classifiées sont envoyées vers MissionLogger et MeshSyncEngine pour diffusion.  

#### SOP associée
- **docs/SOP/anomaly_SOP.md** : décrit les procédures pour activer le SignalClassifier, valider les résultats et transmettre les classifications aux unités voisines.  

#### Valeur opérationnelle (FARDC)
- **Gain de temps** : classification immédiate des signaux sans intervention humaine.  
- **Préparation à la détection proactive** : résultats exploitables directement par l’AnomalyDetector.  
- **Interopérabilité** : classifications partagées via MeshSyncEngine pour coordination multi-unités.  
- **Institutionnalisation** : SOP documentée, intégrée dans le manuel, prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : une unité SIGINT intercepte un signal inconnu sur une fréquence VHF.  
- **Action** : le SignalClassifier analyse le spectre et identifie la modulation comme VHF_FM avec une confiance de 72 %.  
- **Résultat** : MissionLogger enregistre la classification, MeshSyncEngine diffuse l’information aux unités voisines, et l’AnomalyDetector est prêt à vérifier si le comportement est suspect.

---

### 🧪 Tests d’intégration
- **Scénarios validés** :  
  - Géofence hors zone → déclenchement du wipe.  
  - Clé de détresse → déclenchement du wipe.  
  - Tamper matériel → déclenchement du wipe.  
- **Résultats attendus** :  
  - Suppression des clés et signatures.  
  - Vidage des données sensibles.  
  - Journalisation CRITICAL dans MissionLogger.  

---

### Valeur stratégique globale
Ces modes de combat opérationnel assurent :  
- **Résilience** : continuité des missions même en conditions dégradées.  
- **Sécurité** : effacement automatique et traçabilité inviolable.  
- **Interopérabilité** : communication maillée entre unités.  
- **Innovation** : intégration de l’IA pour classification et détection proactive.