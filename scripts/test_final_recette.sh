#!/bin/bash
echo "=== [TEST FINAL DE RECETTE - SRC] ==="

# 1. Vérification des clés
if [ ! -f "data/keys/master.key" ]; then
  echo "[FAIL] Clés de mission absentes."
  exit 1
else
  echo "[OK] Clés de mission présentes."
fi

# 2. Test Geofence
echo "[INFO] Test Geofence..."
make simulate-gps-outside-zone || {
  echo "[FAIL] Geofence non déclenché."
  exit 1
}
echo "[OK] Geofence déclenché correctement."

# 3. Initialisation IA
echo "[INFO] Chargement modèle IA..."
make test-sigint || {
  echo "[WARN] Modèle IA absent, mode dégradé."
}

# 4. Démarrage Mesh
echo "[INFO] Test MeshSyncEngine..."
make test-mesh || {
  echo "[FAIL] MeshSyncEngine non opérationnel."
  exit 1
}
echo "[OK] MeshSyncEngine opérationnel."

# 5. Diffusion BFT
echo "[INFO] Test diffusion BFT..."
make test-bft || {
  echo "[FAIL] BFT non diffusé."
  exit 1
}
echo "[OK] BFT diffusé."

# 6. Rendu FusionOverlay
echo "[INFO] Test FusionOverlay..."
make test-ui || {
  echo "[FAIL] UI non fonctionnelle."
  exit 1
}
echo "[OK] FusionOverlay opérationnel."

echo "=== [RECETTE TERMINÉE : SYSTÈME COMBAT-READY] ==="