# ⚙️ Modes Opérationnels - Système de Renseignement de Combat (SRC)

Ce document centralise les profils de mission du système SIGINT. Chaque mode adapte la consommation d'énergie, la signature électromagnétique et la puissance de calcul du terminal.

---

## 🔒 1. Mode FALLBACK (Résilience Totale)
**Contexte :** Zone blanche, jungle dense, absence de couverture satellite/GSM.
- **Objectif :** Garantir la remontée d'alerte entre unités.
- **Modules Actifs :**
    - `core/sync/MeshSyncEngine.kt` : Synchronisation P2P via LoRa/Wi-Fi Direct.
    - `services/transmission/FallbackTransmitter.kt` : Protocoles bas débit.
- **SOP :** `docs/SOP/transmission_SOP.md`

## 🔋 2. Mode LOW-POWER (Endurance)
**Contexte :** Mission d'observation longue durée (> 48h) sans source de recharge.
- **Objectif :** Maximiser l'autonomie du terminal.
- **Actions du Système :**
    - Réduction du taux d'échantillonnage SDR (Squelch intelligent).
    - Activation de `ui/tactical/NightVisionTheme.kt` (pixels noirs = économie écran).
- **Modules :** `core/power/LowPowerManager.kt`

## 🕶️ 3. Mode SILENT OPS (Discrétion)
**Contexte :** Infiltration en zone contrôlée par l'ennemi.
- **Objectif :** Zéro émission et zéro fuite de lumière.
- **Actions du Système :**
    - Coupure de toutes les interfaces d'émission (Mode Avion strict).
    - Affichage `ui/tactical/LowLightRenderer.kt` (Rouge/Noir uniquement).
    - **Geofencing prédictif :** Alerte vibration si approche d'une zone rouge.

## 🛰️ 4. Mode FUSION & GÉOLOCALISATION (Fixation)
**Contexte :** Recherche active d'un PC commandement ennemi ou d'une batterie d'artillerie.
- **Objectif :** Transformer le signal en coordonnées de frappe.
- **Modules Actifs :**
    - `comint/geo/TDOA_Engine.kt` : Calcul de différence de temps d'arrivée.
    - `fusion_geo/` : Corrélation des données de 3+ capteurs Mesh.
- **SOP :** `docs/SOP/fusiongeo_SOP.md`

## 🤖 5. Mode IA - ANOMALY DETECTION (Veille Auto)
**Contexte :** Surveillance de zones à fort trafic radio (Villes/Axes routiers).
- **Objectif :** Filtrer le bruit civil pour ne détecter que l'hostile.
- **Modules Actifs :**
    - `services/dsp/ai_inference/AnomalyDetector.kt` : Classification par IA.
    - `data/signatures/fardc_threat_db.json` : Bibliothèque de menaces.

## ⚠️ 6. Mode PANIC & EVAC (Survie des Données) - [AJOUT]
**Contexte :** Risque de capture imminent ou perte de l'équipement.
- **Objectif :** Déni d'accès aux renseignements.
- **Action Critique :**
    - Déclenchement manuel ou par Geofencing de `TacticalWipeManager.kt`.
    - Effacement définitif des journaux de mission et clés de chiffrement.
