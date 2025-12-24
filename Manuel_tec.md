##############################################################
# 📘 Manuel Technique & Mode d’Emploi – Combat Ready System SIGINT + BFT
##############################################################

## 1. Introduction
- **Objet** : Décrire le fonctionnement interne des fichiers techniques (Main.kt, scripts de sécurité, Makefile, etc.) et leur usage opérationnel, en intégrant la logique BFT.  
- **Public cible** : Ingénieurs, auditeurs, développeurs, officiers techniques.  
- **Complément du Manuel Opérationnel** : Ce document fusionne la logique interne avec les procédures terrain, pour une vision unifiée.  

--------------------------------------------------------------

## 2. Architecture Globale
- **Orchestrateur** : `Main.kt` → Secure Boot + COP.  
- **Scripts de sécurité** : durcissement (`harden_binary.sh`), rotation des clés (`rotate_keys.sh`), vérification d’intégrité (`integrity_check.sh`), nettoyage (`clean_logs.sh`).  
- **Tests et validation** : `run_tests.sh`.  
- **Automatisation** : `Makefile`.  
- **Onboarding** : `INSTALL.md`.  
- **COP/BFT** : Fusion GPS local + Mesh distant → carte tactique partagée.  

--------------------------------------------------------------

## 3. Workflow de Combat (Main.kt)
### 3.1 Vérification Air-Gapped
- Vérifie la présence et l’intégrité de `master.key`.  
- Refus de démarrage si absent ou corrompu.  

### 3.2 Protection Passive
- Instanciation immédiate de `TacticalWipeManager`.  
- GPS hors zone → auto-nettoyage.  

### 3.3 Modularité
- Chargement IA (`SignalClassifier`).  
- Si échec → mode dégradé avec BFT seul.  

### 3.4 Boucle COP/BFT
- `launchTacticalUI` fusionne :  
  - Données locales (GPS).  
  - Données distantes (Mesh).  
- Carte tactique mise à jour en continu.  

--------------------------------------------------------------

## 4. Modes de Fonctionnement
| Mode        | Condition                  | Fonctionnalités              |
|-------------|----------------------------|------------------------------|
| **Normal**  | Clés valides + IA chargée  | SIGINT + COP/BFT complet     |
| **Dégradé** | IA non chargée             | BFT seul, COP minimal        |
| **Refus**   | Clés absentes/corrompues   | Démarrage impossible         |
| **Auto-Wipe** | GPS hors zone            | Nettoyage complet            |

--------------------------------------------------------------

## 5. Scripts Techniques
- **`harden_binary.sh`** : durcissement des binaires.  
- **`rotate_keys.sh`** : rotation des clés de chiffrement.  
- **`integrity_check.sh`** : vérification SHA256 des fichiers critiques.  
- **`clean_logs.sh`** : purge sécurisée des journaux.  
- **`run_tests.sh`** : validation des modules (Air-Gap, GPS, IA, COP).  
- **`Makefile`** : automatisation (build, test, deploy, clean).  
- **`INSTALL.md`** : guide d’installation et onboarding.  

--------------------------------------------------------------

## 6. Procédures Terrain (fusion avec Manuel_Op.md)
- **Avant mission** : Vérifier clés, lancer système.  
- **Pendant mission** : Surveiller COP/BFT pour suivre alliés et signaux.  
- **En cas d’anomalie** :  
  - IA non disponible → basculer sur BFT.  
  - Hors zone → auto-nettoyage.  
- **Transmission** : COP/BFT assure une vision partagée entre unités.  

--------------------------------------------------------------

## 7. Annexes
- **Diagramme UML – Séquence Main.kt** : Secure Boot → Wipe → IA → COP.  
- **Schéma COP/BFT** : GPS local + Mesh distant → Carte tactique.  
- **Exemple Makefile** : `make build && make test`.  

##############################################################

##############################################################
# 📘 Manuel Technique & Mode d’Emploi – Module de Démonstration (run_demo.sh)
##############################################################

## 1. Objet
Le script `run_demo.sh` est conçu pour valider l’ensemble de la chaîne OODA en environnement de tester la fusion **BFT + SIGINT** et la résilience du système sans matériel SDR.  

--------------------------------------------------------------

## 2. Fonctionnalités Démontrées

### 2.1 Boot Sécurisé
- Vérifie la présence et l’intégrité des clés (`master.key`).  
- Vérifie la configuration du **Geofencing**.  
- Refus de démarrage si l’une des conditions est invalide.  

### 2.2 Fusion Visuelle (Demo Mode)
En mode `--demo-mode`, `Main.kt` alimente automatiquement le **FusionOverlay** avec :  
- 🔵 **Icône Bleue (BRAVO-02)** → Position alliée via BFT.  
- 🔴 **Cercle Rouge (Menace DMR)** → Signal SIGINT détecté.  
- 🟢 **Icône Verte (Opérateur)** → Position propre.  

### 2.3 Résilience du Mesh
- Simulation de réception d’un **UnifiedMessage**.  
- L’UI se met à jour automatiquement, sans intervention humaine.  
- Démonstration de la robustesse du réseau distribué.  

--------------------------------------------------------------

## 3. Procédure d’Utilisation
1. Compiler et préparer `Main.kt`.  
2. Lancer le script avec :  
   ```bash
   ./run_demo.sh --demo-mode