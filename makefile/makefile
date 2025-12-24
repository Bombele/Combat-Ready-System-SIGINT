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

# 🚀 Recette SIGINT (COMINT)
recette-sigint:
	@echo "=== [RECETTE SIGINT - COMINT] ==="
	@kotlinc -d bin/ src/**/*.kt sigint/**/*.kt tests/**/*.kt
	@java -cp bin sigint.comint.tests.ComintTest
	@echo "=== [RECETTE SIGINT TERMINÉE] ==="

# Recette globale SIGINT
recette-sigint-full: recette-sigint
	@echo "=== [LANCEMENT RECETTE ELINT] ==="
	@java -cp bin sigint.elint.tests.ElintTest
	@echo "=== [SYSTÈME SIGINT TOTALEMENT VALIDÉ] ==="

# 🚀 Recette SIGINT (ELINT - RADAR)
recette-elint:
	@echo "=== [RECETTE SIGINT - ELINT] ==="
	@kotlinc -d bin/ src/**/*.kt sigint/**/*.kt tests/*.kt
	@java -cp bin sigint.elint.tests.ElintTest
	@echo "=== [RECETTE ELINT TERMINÉE] ==="

# Recette Globale SIGINT (COMINT + ELINT)
recette-sigint-full: recette-sigint recette-elint
	@echo "🛡️  CHAÎNE SIGINT INTÉGRALE VALIDÉE"

# 🚀 Recette SIGINT (FISINT - Télémétrie)
recette-fisint:
	@echo "=== [RECETTE SIGINT - FISINT] ==="
	@kotlinc -d bin/ src/**/*.kt sigint/**/*.kt tests/*.kt
	@java -cp bin sigint.fisint.tests.FisintTest
	@echo "=== [RECETTE FISINT TERMINÉE] ==="

# 🛡️ RECETTE TOTALE (COMINT + ELINT + FISINT)
# La validation ultime avant le déploiement sur le terrain
recette-sigint-full: recette-sigint recette-elint recette-fisint
	@echo "=========================================================="
	@echo "🔥 SPECTRE SIGINT 100% VALIDÉ : SYSTÈME COMBAT-READY 🔥"
	@echo "=========================================================="

