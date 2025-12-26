# 🛡️ SRC - Système de Renseignement de Combat v1.0
Unité de Capacité Offensive Numérique Souveraine (FARDC)
> Classification : SECRET DÉFENSE
> Usage : Forces Armées de la République Démocratique du Congo
> Description : Plateforme intégrée de SIGINT, BFT et Cyber-Offensive pour la supériorité informationnelle sur le théâtre d'opérations.
> 
## 🚀 Vue d'Ensemble
Le système SRC (Sentinel-Alpha) fusionne le renseignement électronique passif avec des capacités d'action directe sur les infrastructures adverses. Il permet de passer instantanément de la détection à la neutralisation.
## 🛠️ Capacités Majeures
### 1. SIGINT & Identité (Observation)
 * Interception Multimodale : Capture COMINT/ELINT via SDR (Software Defined Radio).
 * Identity Resolver : Corrélation temps réel entre adresses IP et identités IMSI via sondes GGSN/PGW.
 * BFT (Blue Force Tracking) : Géolocalisation des unités amies et marquage des menaces sur carte tactique.
### 2. Offensive Financière (Action)
 * Saisie Conservatoire : Détournement des flux financiers (Mobile Money/Banques) vers le compte souverain.
 * National Switch Hook : Filtrage massif des transactions via le protocole ISO 8583.
 * Crypto-De-anonymization : Identification des portefeuilles Bitcoin/ETH liés à des activités suspectes.
### 3. Offensive Infrastructure (Paralysie)
 * Tactical Ransom : Immobilisation des bases de données logistiques ennemies par chiffrement AES-256 GCM.
 * SCADA Disruptor : Coupure ciblée de l'alimentation des antennes télécoms et infrastructures critiques.
 * Anti-Forensics : Dissimulation des traces d'intrusion par manipulation des journaux système (Syslog/EventLog).
## 🏗️ Architecture Technique
Le système repose sur une architecture hybride Kotlin/Python assurant robustesse et agilité :
 * Core (Kotlin/Java) : Moteur décisionnel, validation PKI (Gatekeeper) et gestion de l'audit immuable.
 * Vectors (Python) : Modules offensifs spécialisés utilisant Scapy, NetfilterQueue et Pymodbus.
 * Security : Scellement cryptographique des logs (WORM) et protection par double signature.
📋 Protocole de Mise en Service
 * Initialisation : Lancer le script de raccordement réseau ./scripts/connect_switch.sh.
 * Authentification : Insertion des jetons matériels (Yubikey) par l'Officier EM et le Magistrat.
 * Engagement : Activation des vecteurs via le Tactical Dashboard.
## ⚠️ Avertissement Légal
L'utilisation de ce système est strictement réservée au cadre des opérations de défense nationale. Toute action offensive doit faire l'objet d'une autorisation formelle enregistrée dans la "Boîte Noire" d'audit immuable.
🏁 État du Dépôt
 * Version : v1.0.0 (Sentinel-Alpha)
 * Branche Stable : main
 * Audit : Validé (ComplianceMatrix-OK)
