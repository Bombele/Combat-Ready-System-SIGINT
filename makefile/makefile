# Makefile - Système SRC Combat-Ready

# Compilation et durcissement
build:
	@echo "[BUILD] Compilation du binaire..."
	@kotlinc -d bin/ src/**/*.kt

harden:
	@echo "[HARDEN] Application des routines d'obscurcissement..."
	@./scripts/harden_binary.sh

# Sécurité
rotate-keys:
	@echo "[SECURITY] Rotation des clés de mission..."
	@./scripts/rotate_keys.sh

check-integrity:
	@echo "[SECURITY] Vérification d'intégrité..."
	@./scripts/integrity_check.sh

clean-logs:
	@echo "[SECURITY] Nettoyage des logs..."
	@./scripts/clean_logs.sh

deploy:
	@echo "[DEPLOY] Déploiement terrain..."
	@./infra/deployment/deploy.sh

# Tests unitaires
test:
	@echo "[TEST] Simulation complète..."
	@./scripts/run_tests.sh

# 🚀 Test Final de Recette
recette:
	@echo "=== [TEST FINAL DE RECETTE - SRC] ==="
	@if [ ! -f "data/keys/master.key" ]; then \
		echo "[FAIL] Clés de mission absentes."; exit 1; \
	else \
		echo "[OK] Clés de mission présentes."; \
	fi
	@echo "[INFO] Test Geofence..."
	@./scripts/simulate_gps_outside_zone.sh || { echo "[FAIL] Geofence non déclenché."; exit 1; }
	@echo "[OK] Geofence déclenché correctement."
	@echo "[INFO] Chargement modèle IA..."
	@./scripts/test_sigint.sh || echo "[WARN] Modèle IA absent, mode dégradé."
	@echo "[INFO] Test MeshSyncEngine..."
	@./scripts/test_mesh.sh || { echo "[FAIL] MeshSyncEngine non opérationnel."; exit 1; }
	@echo "[OK] MeshSyncEngine opérationnel."
	@echo "[INFO] Test diffusion BFT..."
	@./scripts/test_bft.sh || { echo "[FAIL] BFT non diffusé."; exit 1; }
	@echo "[OK] BFT diffusé."
	@echo "[INFO] Test FusionOverlay..."
	@./scripts/test_ui.sh || { echo "[FAIL] UI non fonctionnelle."; exit 1; }
	@echo "[OK] FusionOverlay opérationnel."
	@echo "=== [RECETTE TERMINÉE : SYSTÈME COMBAT-READY] ==="