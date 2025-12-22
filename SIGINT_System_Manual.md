# Manuel institutionnel consolidé – Système SIGINT

Ce document regroupe la description et la valeur institutionnelle de tous les modules du projet SIGINT.  
Il constitue une vue globale et homogène, garantissant transmission, auditabilité et adoption continentale.

---

## 📂 Modules

### infra/
- **Rôle** : déploiement, sécurité, traçabilité (Docker, K8s, Cosign, SBOM, CI/CD).
- **Valeur institutionnelle** : assure que le système est déployé de manière sécurisée et certifiable.

### services/
- **Rôle** : cœur opérationnel (gateway, capture, DSP, decode, classify, fusion_geo, audit, sync).
- **Valeur institutionnelle** : rend les flux SIGINT exploitables et audités.

### data/
- **Rôle** : référentiel technique (samples IQ, signatures, datasets, geo/maps, audit_data/export_templates).
- **Valeur institutionnelle** : garantit que les données sont auditées, certifiables et prêtes pour transmission.

### tests/
- **Rôle** : validation complète (unitaires, intégration, conformité, performance).
- **Valeur institutionnelle** : assure fiabilité, conformité et résilience du système.

### makefile/
- **Rôle** : artefact racine pour orchestration (build, deploy, test, audit, compliance).
- **Valeur institutionnelle** : centralise la reproductibilité et la traçabilité des opérations.

### specs/
- **Rôle** : constitution documentaire (policies, profils_mission, tests, functional, technical, compliance, operational).
- **Valeur institutionnelle** : définit les exigences et normes, garantissant conformité et adoption continentale.

### ui/
- **Rôle** : interfaces utilisateur (mobile, web, desktop, assets).
- **Valeur institutionnelle** : rend le système accessible, homogène et conforme.

### docs/
- **Rôle** : référentiel documentaire (manuels, guides, rapports, notes de conception).
- **Valeur institutionnelle** : assure transmission, auditabilité et crédibilité documentaire.

### sigint/
- **Rôle** : cœur opérationnel SIGINT (audit, comint, core, elint, fisint, sync).
- **Valeur institutionnelle** : capture, analyse, audit et synchronisation des flux électromagnétiques.

### integration/
- **Rôle** : sous‑module transversal documentant les interfaces entre modules.
- **Sous‑dossier interface/** : regroupe les spécifications des APIs, les points de contact et la cartographie des services.
- **Valeur institutionnelle** : assure traçabilité, interopérabilité et conformité des échanges inter‑modules.

---

## 🏛️ Synthèse institutionnelle

- **Crédibilité** : chaque module correspond à une fonction reconnue et auditable.  
- **Transmission** : documentation homogène et trilingue prête pour adoption continentale.  
- **Auditabilité** : conformité légale et institutionnelle intégrée dans chaque module.  
- **Résilience** : modules opérationnels garantissent continuité et reprise.  
- **Interopérabilité** : le module `integration/` assure cohérence et traçabilité des interfaces.  
- **Homogénéité** : l’ensemble forme une ossature claire et institutionnelle.  

---

✅ Avec ce manuel consolidé, le système SIGINT est présenté comme une **architecture complète et institutionnelle**, garantissant cohérence, transmission, certification et interopérabilité à l’échelle continentale.