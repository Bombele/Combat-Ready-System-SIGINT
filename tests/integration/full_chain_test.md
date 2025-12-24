# 🏁 Scénario de Test : Chaîne de Renseignement SIGINT

**Objectif :** Valider la détection automatique d'une menace et sa propagation Mesh.

## 1. Pré-requis
- Fichier `active_geofence.poly` chargé (Zone Goma).
- Fichier `threat_model.tflite` présent dans `services/dsp/ai_inference/`.
- Deux instances du logiciel lancées (Nœud Alpha et Nœud Bravo).

## 2. Procédure de Test

### Étape A : Initialisation et Sécurité
1. Lancer le Nœud Alpha à la position GPS (-1.666, 29.222).
2. Vérifier que `TacticalWipeManager` ne déclenche pas le wipe (Zone Safe).
3. **Résultat attendu :** `MissionLogger` affiche `[INFO] System initialized in Evidence Mode`.

### Étape B : Détection et Classification
1. Injecter un `FloatArray` simulant une activité radio suspecte dans `SignalClassifier`.
2. Appeler `classifySpectrum()`.
3. **Résultat attendu :** `SignalClassifier` génère un `ThreatMessage` de type `VHF_FM`.

### Étape C : Propagation Mesh
1. Le Nœud Alpha appelle `MeshSyncEngine.enqueueThreat()`.
2. Le Nœud Bravo se connecte au Nœud Alpha via `WifiDirectAdapter`.
3. **Résultat attendu :** - Le Nœud Bravo affiche `[WARNING] New threat received via Mesh: VHF_FM`.
   - Le fichier `core/audit/logs/mission.log` du Nœud Bravo contient le log signé du message reçu.

## 3. Critères de Réussite
- [ ] Le message reçu par Bravo a le même ID que celui envoyé par Alpha.
- [ ] L'intégrité de la chaîne de log (HMAC) est vérifiée sur les deux nœuds.
- [ ] Aucun wipe n'a été déclenché par erreur.
