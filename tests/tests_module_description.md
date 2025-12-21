# Description du module Tests

Le module `tests/` assure la validation complète du système SIGINT.  
Il couvre les tests unitaires, l’intégration bout‑en‑bout, la conformité légale et institutionnelle, ainsi que la performance et la résilience.  
Ce module garantit que chaque composant est fiable, auditable et prêt pour certification.

---

## 📂 Structure

### unit/
- **test_device_manager.kt** → Vérifie détection SDR (plug‑and‑play).
- **test_gateway_manager.kt** → Vérifie agrégation multi‑SDR.
- **test_sync_engine.kt** → Vérifie synchronisation offline.
- **test_dsp_pipeline.kt** → Vérifie traitement signal (FFT, filtrage).
- **test_decoder_manager.kt** → Vérifie décodages conformes.
- **test_classifier_engine.kt** → Vérifie classification signatures.

### integration/
- **test_comint_flow.kt** → Capture → Decode → Transcribe → Analyze.
- **test_elint_flow.kt** → Capture → Classify → Analyze.
- **test_fisint_flow.kt** → Capture → Decode → Analyze.
- **test_gateway_sync.kt** → Gateway + Sync offline.
- **test_ui_integration.kt** → Vérifie UI (mobile/web) avec services.

### compliance/
- **test_opa_policies.rego** → Vérifie règles OPA.
- **test_compliance_matrix.kt** → Vérifie matrice conformité.
- **test_audit_exports.kt** → Vérifie exports auditeurs.
- **test_sbom_cosign.sh** → Vérifie signatures et SBOM.

### performance/
- **test_latency_benchmark.kt** → Mesure latence capture → analyse.
- **test_throughput.kt** → Mesure flux multi‑SDR.
- **test_cpu_memory_usage.kt** → Benchmarks ressources.
- **test_resilience_offline.kt** → Vérifie reprise après perte réseau.

### test_index.md
- Documentation indexée des tests.

---

## 🎯 Description des sous‑modules

- **unit/** → tests unitaires pour chaque composant (DeviceManager, Gateway, Sync, DSP, Decode, Classify).  
- **integration/** → tests bout‑en‑bout pour valider les flux COMINT, ELINT, FISINT et l’intégration UI.  
- **compliance/** → tests de conformité légale et institutionnelle (OPA, audit, SBOM, signatures).  
- **performance/** → benchmarks de latence, débit, consommation ressources et résilience offline.  
- **test_index.md** → documentation indexée pour auditeurs et développeurs.  

---

## 🏛️ Valeur institutionnelle

- **Crédibilité** : chaque module est validé par des tests unitaires et d’intégration.  
- **Auditabilité** : conformité légale et institutionnelle vérifiée automatiquement.  
- **Résilience** : performance et reprise offline testées et documentées.  
- **Transmission** : documentation indexée pour adoption continentale et certification.  

---

✅ Avec ce module `tests/`, ton logiciel SIGINT est **validé, auditable et certifiable**, prêt pour déploiement institutionnel.