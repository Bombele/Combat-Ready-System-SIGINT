# =============================================================================
# SRC - MAKEFILE OPÉRATIONNEL (FARDC SIGINT SYSTEM)
# =============================================================================

# Chemins
SCRIPTS_DIR = scripts
DEPLOY_SCRIPT = infra/deployment/deploy_field.sh
BUILD_DIR = build
BIN = $(BUILD_DIR)/sigint-engine

.PHONY: all build harden deploy test rotate-keys clean-logs check-integrity help

all: build harden test

# --- [ PHASE 1 : CONSTRUCTION ] ---
build:
	@echo "🛠️  Compilation du système SRC..."
	@mkdir -p build
	kotlinc src/Main.kt core/**/*.kt bft/**/*.kt services/**/*.kt ui/**/*.kt -include-runtime -d build/sigint-engine.jar

# --- [ PHASE 2 : SÉCURISATION ] ---
harden:
	@echo "🛡️  Démarrage du durcissement tactique..."
	@bash $(SCRIPTS_DIR)/harden_binary.sh
	@# Sauvegarde de la signature pour integrity_check
	@sha256sum $(BIN) | cut -d ' ' -f1 > specs/integrity_signature.txt
	@echo "[OK] Binaire obscurci et signature d'intégrité générée."

rotate-keys:
	@bash $(SCRIPTS_DIR)/rotate_keys.sh

# --- [ PHASE 3 : VALIDATION ] ---
test:
	@echo "🧪 Lancement de la suite de tests d'intégration..."
	@bash $(SCRIPTS_DIR)/run_tests.sh

check-integrity:
	@bash $(SCRIPTS_DIR)/integrity_check.sh

# --- [ PHASE 4 : DÉPLOIEMENT ] ---
deploy:
	@echo "🚀 Déploiement sur terminal de combat..."
	@bash $(DEPLOY_SCRIPT) --zone KIVU_NORD --unit_id ALPHA-01

# --- [ UTILS ] ---
clean-logs:
	@bash $(SCRIPTS_DIR)/clean_logs.sh

clean:
	@echo "🧹 Nettoyage de l'espace de travail..."
	@rm -rf $(BUILD_DIR)
	@echo "[OK] Dossier build supprimé."

help:
	@echo "Usage : make [CIBLE]"
	@echo "  build            : Compile le binaire SIGINT"
	@echo "  harden           : Obscurcit le binaire et génère la signature"
	@echo "  test             : Lance les tests de la chaîne OODA"
	@echo "  deploy           : Prépare l'appareil pour le terrain"
	@echo "  rotate-keys      : Génère de nouvelles clés master/session"
	@echo "  check-integrity  : Vérifie si le binaire a été altéré"

