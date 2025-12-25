# --- Makefile SRC Combat-Ready ---

KOTLINC=kotlinc
BUILD_DIR=build/tests
SRC_SIGINT=sigint/**/*.kt core/sync/*.kt core/audit/*.kt

# 🚀 Recette COMINT
recette-comint:
	@echo "=== [RECETTE SIGINT - COMINT] ==="
	@$(KOTLINC) -d bin/ src/**/*.kt sigint/**/*.kt tests/**/*.kt
	@java -cp bin sigint.comint.tests.ComintTest
	@echo "=== [RECETTE COMINT TERMINÉE] ==="

# 🚀 Recette ELINT
recette-elint:
	@echo "=== [RECETTE SIGINT - ELINT] ==="
	@$(KOTLINC) -d bin/ src/**/*.kt sigint/**/*.kt tests/*.kt
	@java -cp bin sigint.elint.tests.ElintTest
	@echo "=== [RECETTE ELINT TERMINÉE] ==="

# 🚀 Recette FISINT
recette-fisint:
	@echo "=== [RECETTE SIGINT - FISINT] ==="
	@$(KOTLINC) -d bin/ src/**/*.kt sigint/**/*.kt tests/*.kt
	@java -cp bin sigint.fisint.tests.FisintTest
	@echo "=== [RECETTE FISINT TERMINÉE] ==="

# 🚀 Recette COMINT Capture
recette-comint-capture:
	@echo "=== [RECETTE COMINT CAPTURE] ==="
	@mkdir -p $(BUILD_DIR) data/logs data/audit
	@$(KOTLINC) $(SRC_SIGINT) -include-runtime -d $(BUILD_DIR)/comint-capture-test.jar
	@java -jar $(BUILD_DIR)/comint-capture-test.jar
	@echo "=== [RECETTE COMINT CAPTURE TERMINÉE] ==="

# 📊 Certification et Clôture de Mission
certifier-mission:
	@echo "=== [GÉNÉRATION DU RAPPORT DE MISSION SRC] ==="
	@$(KOTLINC) -d bin/ src/**/*.kt sigint/**/*.kt
	@java -cp bin sigint.audit.MissionReportGenerator
	@echo "=========================================================="
	@echo "🛡️  RAPPORT SIGNÉ DISPONIBLE DANS data/audit/"
	@echo "🚀 MISSION TERMINÉE : SYSTÈME PRÊT POUR RÉINITIALISATION"
	@echo "=========================================================="

# 🔥 RECETTE SIGINT FULL (Le Triptyque + Capture)
recette-sigint-full: recette-comint recette-elint recette-fisint recette-comint-capture
	@echo "=========================================================="
	@echo "🛡️  CHAÎNE ROEM 100% VALIDÉE : COMINT + ELINT + FISINT + CAPTURE"
	@echo "🚀 SYSTÈME SRC PRÊT POUR DÉPLOIEMENT SOUVERAIN"
	@echo "=========================================================="

# 🚀 Commande Ultime : Recette complète + Rapport
combat-ready: recette-sigint-full certifier-mission
	@echo "📊 Certification finale effectuée."
	@echo "🚀 SYSTÈME SRC COMBAT-READY : TOUS LES VOYANTS AU VERT."