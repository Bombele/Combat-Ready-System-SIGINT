# SIGINT Combat-Ready System

## 🎯 Objectif
Ce projet évolue d’une **ossature logicielle auditable** vers un **système de renseignement de combat opérationnel**, conçu pour les FARDC et adapté aux environnements hostiles (connectivité limitée, diversité des équipements, menaces asymétriques).

---

## 🚀 Évolutions majeures

### 1. Edge Intelligence
- **IA embarquée (services/dsp/ai_inference/)** : classification automatique des signaux (AMC) avec modèles TFLite.
- **Maillage de capteurs (services/mesh/)** : synchronisation P2P via LoRa/WiFi ad-hoc, sans dépendance satellite.

### 2. Géolocalisation avancée
- **fusion_geo/** : triangulation multi-nœuds avec TDOA/AoA.
- **Cartographie offline (data/maps/)** : intégration MBTiles pour usage en zone sans réseau.

### 3. Sécurité et résilience
- **GeofenceManager.kt** : effacement automatique des clés si sortie de zone de mission.
- **infra/cyber_resilience/** : modèles de menaces, plans de reprise, tests de robustesse.
- **Anti-tamper** : protection binaire contre ingénierie inverse.

### 4. Signatures et protocoles
- **data/signatures/** : enrichissement avec fréquences locales (radios Baofeng, Motorola).
- **comint/decoder/** : support des protocoles DMR, P25.

### 5. Transmission sécurisée
- **Rapports compressés (Burst Transmission)** : CBOR/Zstd pour envoi via Starlink/Iridium.
- **Auditabilité** : chaque transmission est journalisée et signée.

---

## 🗺️ Roadmap d’évolution

| Phase | Focus | Ajout Majeur |
|-------|-------|--------------|
| Phase 1 : Mobilité | Optimisation Android | Drivers RTL-SDR/HackRF via DeviceManager.kt |
| Phase 2 : Fusion | TDOA / Géolocalisation | Corrélation multi-opérateurs pour localisation précise |
| Phase 3 : IA | Classification Auto | Identification automatique des menaces |
| Phase 4 : Transmission | SatCom / Burst | Rapports compressés via Starlink/Iridium |

---

## 🏛️ Valeur institutionnelle

- **Traçabilité** : changelog et system_index.md assurent suivi et transparence.  
- **Interopérabilité** : integration/ documente les interfaces inter-modules.  
- **Résilience** : cyber_resilience/ et GeofenceManager garantissent continuité et sécurité.  
- **Transmission continentale** : documentation homogène et certifiable.  
- **Opérabilité terrain** : Edge computing, mesh, DF, signatures locales.  

---

✅ Ce README positionne ton projet comme une **architecture SIGINT combat-ready**, prête pour déploiement opérationnel et certification institutionnelle.