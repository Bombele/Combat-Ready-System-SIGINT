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

### 6. Résilience – Multi‑canal fallback
- **Objectif** : garantir la transmission même en cas de perte totale de réseau.  
- **Implémentations** : SMS chiffré (AES + Base64), HF analogique (FSK simple), priorisation des données critiques.  
- **Institutionnalisation** : profil “Fallback Mode” documenté dans `specs/operational_spec.md` avec SOP associée.

### 7. IA locale – Détection d’anomalies
- **Objectif** : identifier automatiquement les comportements radio suspects.  
- **Implémentations** : module `AnomalyDetector.kt` dans `services/dsp/ai_inference/`, algorithmes légers (Isolation Forest, autoencoder TFLite).  
- **Exemples** : burst inhabituel, modulation rare, fréquence hors plage normale.  
- **Institutionnalisation** : enrichir `data/signatures/` avec anomalies typiques et documenter dans `docs/reports/anomaly_report.md`.

### 8. Institution – SOP & Certification
- **Objectif** : rendre le système transmissible et utilisable par les FARDC avec crédibilité internationale.  
- **Implémentations** :  
  - SOP dans `docs/SOP/` pour chaque module (capture, fusion_geo, mesh, transmission).  
  - Certification progressive alignée avec normes OTAN/ITU.  
  - Formation opérateurs avec manuels simplifiés et simulateurs.

---

## 🗺️ Roadmap d’évolution

| Phase | Focus | Ajout Majeur |
|-------|-------|--------------|
| Phase 1 : Mobilité | Optimisation Android | Drivers RTL-SDR/HackRF via DeviceManager.kt |
| Phase 2 : Fusion | TDOA / Géolocalisation | Corrélation multi-opérateurs pour localisation précise |
| Phase 3 : IA | Classification Auto | Identification automatique des menaces |
| Phase 4 : Transmission | SatCom / Burst | Rapports compressés via Starlink/Iridium |
| Phase 5 : Sécurité | Panic wipe & boot vérifié | Protection contre compromission physique |
| Phase 6 : Résilience | Multi-canal fallback | SMS chiffré / HF analogique |
| Phase 7 : IA locale | Détection anomalies | Identification trafic suspect |
| Phase 8 : Institution | SOP & certification | Normes OTAN/ITU, formation opérateurs |

---

## 🏛️ Valeur institutionnelle

- **Traçabilité** : changelog et system_index.md assurent suivi et transparence.  
- **Interopérabilité** : integration/ documente les interfaces inter-modules.  
- **Résilience** : cyber_resilience/ et GeofenceManager garantissent continuité et sécurité.  
- **Transmission continentale** : documentation homogène et certifiable.  
- **Opérabilité terrain** : Edge computing, mesh, DF, signatures locales.  
- **Institutionnalisation** : SOP et certification internationale pour adoption par les FARDC et partenaires.  

---

✅ Ce README positionne ton projet comme une **architecture SIGINT combat-ready**, prête pour déploiement opérationnel, certification institutionnelle et adoption continentale.