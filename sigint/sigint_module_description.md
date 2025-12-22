# Description du module SIGINT

Le module `sigint/` constitue le **cœur opérationnel** du système SIGINT.  
Il regroupe les sous‑systèmes COMINT, ELINT, FISINT, Audit, Core et Sync, chacun dédié à une fonction clé du renseignement électromagnétique.  
Ce module est responsable de la capture, de l’analyse, de l’audit et de la synchronisation des flux.

---

## 📂 Structure

### audit/
- Gestion des journaux, conformité et exports audités.
- Documentation des processus d’audit et transmission institutionnelle.

### comint/
- Communications Intelligence (voix, données, protocoles).
- Capture, décodage et transcription des communications.

### core/
- Noyau du système : DeviceManager, Gateway, pipeline DSP.
- Relie et orchestre les autres sous‑modules.

### elint/
- Electronic Intelligence (radars, signatures, classification).
- Analyse et reconnaissance des signaux radar.

### fisint/
- Foreign Instrumentation Signals Intelligence (télémesures).
- Décodage et analyse des signaux de télémesure.

### sync/
- Synchronisation offline‑first.
- Reprise après perte réseau et continuité opérationnelle.

---

## 🎯 Description des sous‑modules

- **audit/** → assure la traçabilité et la conformité institutionnelle.  
- **comint/** → capture et analyse des communications voix/données.  
- **core/** → fournit le noyau technique et relie les autres sous‑modules.  
- **elint/** → classification et analyse des signaux radar.  
- **fisint/** → analyse des télémesures étrangères.  
- **sync/** → garantit la résilience et la continuité en contexte dégradé.  

---

## 🏛️ Valeur institutionnelle

- **Crédibilité** : chaque sous‑module correspond à une discipline reconnue du SIGINT.  
- **Auditabilité** : le sous‑module `audit/` garantit conformité et transmission institutionnelle.  
- **Résilience** : le sous‑module `sync/` assure continuité même en cas de perte réseau.  
- **Transmission** : documentation et modularité facilitent adoption continentale.  
- **Centralité** : `core/` est le noyau qui relie tous les autres sous‑modules.  

---

✅ Avec ce module `sigint/`, le projet dispose d’un **cœur opérationnel complet et institutionnel**, garantissant capture, analyse, audit et synchronisation des flux électromagnétiques.