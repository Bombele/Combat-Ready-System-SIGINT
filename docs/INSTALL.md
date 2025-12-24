# 🛡️ SRC - Guide d'Installation Rapide (Quick Start)

> **SYSTÈME DE RENSEIGNEMENT DE COMBAT (FARDC - SIGINT/BFT)**
> Ce guide permet de transformer un terminal vierge en station opérationnelle en < 5 minutes.

---

## 1. PRÉ-REQUIS SYSTÈME
- **OS :** Linux (Ubuntu/Debian) ou Android (Termux/Native).
- **Hardware :** Antenne SDR (RTL-SDR/HackRF), Module GPS, stockage chiffré.
- **Dépendances :** `make`, `gcc`, `openjdk-17`, `openssl`, `libusb`.

---

## 2. DÉPLOIEMENT INITIAL (≈ 3 minutes)

### A. Initialisation du dépôt
```bash
git clone [https://github.com/Bombele/Combat-Ready-System-SIGINT.git](https://github.com/Bombele/Combat-Ready-System-SIGINT.git)
cd Combat-Ready-System-SIGINT
chmod +x scripts/*.sh infra/deployment/*.sh

### B. Sécurisation et Cryptographie
​Générez les clés uniques pour la rotation de mission :

make rotate-keys

### C. Configuration de la Zone (Geofencing)
​Définit le périmètre de sécurité pour éviter le Panic Wipe automatique :

nano core/security/active_geofence.poly
# Format : latitude,longitude (un point par ligne)


## 3. COMPILATION & DURCISSEMENT (≈ 1 minute)
​Préparez le binaire avec les couches d'obscurcissement et d'intégrité :

make build    # Compilation des modules BFT/SIGINT
make harden   # Obscurcissement et signature d'intégrité


## 4. MISE EN SERVICE & COP (≈ 30 secondes)

### ​A. Test de la chaîne OODA
​Vérifiez que l'IA, le Mesh et la Sécurité communiquent :

make test

### B. Lancement de la Vue Tactique (COP)
​Visualisez la fusion BFT + SIGINT sur la carte hors-ligne :

./run_demo.sh  # Lance l'UI fusionnée

Légende COP :
​🔵 Bleu : Unités alliées (BFT).
​🔴 Rouge : Menaces détectées (SIGINT).
​🟢 Vert : Opérateur local & Limites de zone (Geofence).


## ​5. COMMANDES DE TERRAIN (MÉMENTO)
​Déploiement final : make deploy
​Vérification binaire : make check-integrity
​Nettoyage post-mission : make clean-logs
​URGENCE (WIPE) : Utilisez la "Clé de Détresse" via l'UI ou le script clean_logs.sh.

## ​⚠️ AVERTISSEMENT SÉCURITÉ
​PROTECTION ANTI-CAPTURE : Toute exécution ou déplacement de l'appareil hors de la zone définie dans active_geofence.poly déclenchera l'effacement immédiat et irréversible des données sensibles (signatures, clés, logs).
​VÉRIFIEZ VOTRE GPS AVANT TOUTE ACTIVATION.
