# Description du module Data

Le module `data/` constitue le référentiel technique et institutionnel du système SIGINT.  
Il regroupe les échantillons de signaux, les bases de signatures, les jeux de données pour classification, les données géographiques et les éléments nécessaires à l’audit.  
Ce module garantit la traçabilité, l’auditabilité et la transmission des informations.

---

## 📂 Structure

### samples_iq/
- **hf_voice_sample.iq** → Exemple COMINT voix HF.
- **radar_bandS_sample.iq** → Exemple ELINT radar bande S.
- **telemetry_bandL_sample.iq** → Exemple FISINT télémesure bande L.
- **test_metadata.json** → Métadonnées associées aux échantillons.

### signatures/
- **radar_signatures.json** → Signatures radar connues.
- **protocol_signatures.json** → Protocoles COMINT/FISINT.
- **emitter_profiles.yaml** → Profils émetteurs (fréquences, puissances, zones).
- **signature_index.md** → Documentation indexée.

### datasets/
- **training_set.csv** → Données pour classification.
- **validation_set.csv** → Données pour tests.
- **labels.json** → Labels associés aux signaux.

### geo/
- **maps/** → Cartes de référence.
- **geo_profiles.json** → Profils géographiques.
- **triangulation_samples.csv** → Données pour fusion géographique.

### audit_data/
- **compliance_logs.json** → Journaux de conformité.
- **audit_samples.json** → Exemples de flux audités.
- **export_templates/** → Modèles d’exports auditeurs.

---

## 🎯 Description des sous‑modules

- **samples_iq/** → fournit des échantillons IQ pour tester le pipeline DSP (FFT, filtrage, spectrogrammes).  
- **signatures/** → base de signatures radar et protocoles pour classification et reconnaissance.  
- **datasets/** → jeux de données enrichis pour entraînement et validation des moteurs de classification.  
- **geo/** → données géographiques pour triangulation et cartographie des émetteurs.  
- **audit_data/** → données de conformité et exemples d’exports pour auditeurs.  

---

## 🏛️ Valeur institutionnelle

- **Traçabilité** : chaque échantillon et signature est documenté et indexé.  
- **Auditabilité** : données de conformité prêtes pour validation externe.  
- **Transmission** : documentation claire pour adoption continentale.  
- **Plug‑and‑Play** : les échantillons IQ permettent de tester immédiatement le système sans matériel.  

---

✅ Avec ce module `data/`, ton logiciel SIGINT dispose d’un **référentiel technique et institutionnel complet**, garantissant traçabilité, auditabilité et transmission.