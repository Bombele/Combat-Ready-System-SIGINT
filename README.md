# SIGINT Combat-Ready System

## 🎯 Objectif

Ce projet évolue d’une **ossature logicielle auditable** vers un **système de renseignement de combat opérationnel**, conçu pour les FARDC et adapté aux environnements hostiles (connectivité limitée, diversité des équipements, menaces asymétriques).

---

## 🚀 Évolutions majeures

### 1. Edge Intelligence
- **IA embarquée (services/dsp/ai_inference/)** : classification automatique des signaux (AMC) avec modèles TFLite.  
- **Maillage de capteurs (services/mesh/)** : synchronisation P2P via LoRa/WiFi ad-hoc, sans dépendance satellite.  
- **SignalClassifier.kt** : identifie instantanément les signaux (VHF, DMR, satellite).  

### 2. Géolocalisation avancée
- **fusion_geo/** : triangulation multi-nœuds avec TDOA/AoA.  
- **Cartographie offline (data/maps/)** : intégration MBTiles pour usage en zone sans réseau.  
- **mbtiles_manager.kt** : gestionnaire des cartes topographiques locales.  

### 3. Sécurité et résilience
- **GeofenceManager.kt** : effacement automatique des clés si sortie de zone de mission.  
- **infra/cyber_resilience/** : modèles de menaces, plans de reprise, tests de robustesse.  
- **Anti-tamper** : protection binaire contre ingénierie inverse.  
- **PanicHandler.kt** : autodestruction des données sensibles en cas de capture.  
- **ZeroTrustManager.kt** : chiffrement au repos et rotation des clés.  
- **LowPowerManager.kt** : mode veille intelligente pour économiser l’énergie.  

### 4. Signatures et protocoles
- **data/signatures/** : enrichissement avec fréquences locales (radios Baofeng, Motorola).  
- **comint/decoder/** : support des protocoles DMR, P25.  
- **rdc_rebel_nets.json** : signatures spécifiques aux réseaux rebelles en RDC.  

### 5. Transmission sécurisée
- **Rapports compressés (Burst Transmission)** : CBOR/Zstd pour envoi via Starlink/Iridium.  
- **Auditabilité** : chaque transmission est journalisée et signée.  
- **FallbackTransmitter.kt** : transmission fallback via SMS chiffré ou HF analogique.  

### 6. Résilience – Multi‑canal fallback
- **Objectif** : garantir la transmission même en cas de perte totale de réseau.  
- **Implémentations** : SMS chiffré (AES + Base64), HF analogique (FSK simple), priorisation des données critiques.  
- **Institutionnalisation** : profil “Fallback Mode” documenté dans `specs/operational_spec.md` avec SOP associée.  
- **MeshSyncEngine.kt** : synchronisation P2P sans serveur central.  

### 7. IA locale – Détection d’anomalies
- **Objectif** : identifier automatiquement les comportements radio suspects.  
- **Implémentations** : module `AnomalyDetector.kt` dans `services/dsp/ai_inference/`, algorithmes légers (Isolation Forest, autoencoder TFLite).  
- **Exemples** : burst inhabituel, modulation rare, fréquence hors plage normale.  
- **Institutionnalisation** : enrichir `data/signatures/anomalies.json` et documenter dans `docs/reports/anomaly_report.md`.  

### 8. Institution – SOP & Certification
- **Objectif** : rendre le système transmissible et utilisable par les FARDC avec crédibilité internationale.  
- **Implémentations** :  
  - SOP dans `docs/SOP/` pour chaque module (capture, fusion_geo, mesh, transmission).  
  - Certification progressive alignée avec normes OTAN/ITU.  
  - Formation opérateurs avec manuels simplifiés et simulateurs.  
  - **compliance_matrix.md** : traçage des exigences de conformité.  
  - **training_manual.md** : guide simplifié pour opérateurs FARDC.  

---

## 🗺️ Roadmap d’évolution

| Phase | Focus | Ajout Majeur |
|-------|-------|--------------|
| Phase 1 : Mobilité | Optimisation Android | Drivers RTL-SDR/HackRF via DeviceManager.kt |
| Phase 2 : Fusion | TDOA / Géolocalisation | Corrélation multi-opérateurs pour localisation précise |
| Phase 3 : IA | Classification Auto | Identification automatique des menaces |
| Phase 4 : Transmission | SatCom / Burst | Rapports compressés via Starlink/Iridium |
| Phase 5 : Sécurité | Panic wipe & boot vérifié | Protection contre compromission physique |
| Phase 6 : Résilience | Multi-canal fallback | SMS chiffré / HF analogique, MeshSyncEngine |
| Phase 7 : IA locale | Détection anomalies | AnomalyDetector, signatures locales enrichies |
| Phase 8 : Institution | SOP & certification | Normes OTAN/ITU, compliance_matrix, training_manual |

---

## 🏛️ Valeur institutionnelle

- **Traçabilité** : changelog et system_index.md assurent suivi et transparence.  
- **Interopérabilité** : integration/ documente les interfaces inter-modules.  
- **Résilience** : cyber_resilience/, PanicHandler et MeshSyncEngine garantissent continuité et sécurité.  
- **Transmission continentale** : documentation homogène et certifiable.  
- **Opérabilité terrain** : Edge computing, mesh, DF, signatures locales, cartographie offline.  
- **Institutionnalisation** : SOP, certification et formation internationale pour adoption par les FARDC et partenaires.  

---

## 🎯 Fiche de Synthèse Tactique – Modes d’Activation

Cette fiche guide l’opérateur SIGINT dans le choix du mode adapté à sa mission.

### 🕶️ Infiltration (Opérations discrètes)
- **Activer :** Silent Ops Mode
- **Modules clés :**
  - ui/tactical/LowLightRenderer.kt (interface nocturne)
  - core/audit/MissionLogger.kt (journal minimal)
- **Effet :** Réduction des traces numériques et visuelles, transmission limitée.

### 👁️ Surveillance de Zone (Observation prolongée)
- **Activer :** Low-Power Mode + SignalClassifier
- **Modules clés :**
  - core/power/LowPowerManager.kt (veille intelligente)
  - services/dsp/ai_inference/SignalClassifier.kt (IA de détection)
- **Effet :** Économie d’énergie, IA déclenchée uniquement si RSSI > seuil (squelch intelligent).

### 🚨 Repli d’Urgence (Capture ou menace critique)
- **Activer :** Panic Wipe (TacticalWipeManager)
- **Modules clés :**
  - core/security/TacticalWipeManager.kt (effacement immédiat)
  - core/audit/MissionLogger.kt (log CRITICAL signé)
- **Effet :** Suppression des clés et données sensibles, extinction sécurisée.

### 📡 Patrouille & Partage (Coordination inter-unités)
- **Activer :** MeshSyncEngine
- **Modules clés :**
  - core/sync/MeshSyncEngine.kt (store-and-forward)
  - core/sync/WifiDirectAdapter.kt (transport opportuniste)
- **Effet :** Diffusion automatique des menaces entre unités, déduplication et priorisation.

---

✅ **Conseil terrain :**  
- Toujours vérifier que `active_geofence.poly` est chargé avant mission.  
- Surveiller la batterie : activer le squelch intelligent pour éviter une IA en continu.  
- En mode Evidence, conserver les logs pour certification et débriefing.