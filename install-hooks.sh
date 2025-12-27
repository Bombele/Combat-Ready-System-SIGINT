#!/usr/bin/env bash
set -euo pipefail

HOOK_FILE=".git/hooks/pre-commit"

echo "⚙️ Installation du hook pre-commit..."

# Crée le dossier hooks s'il n'existe pas
mkdir -p .git/hooks

# Écrit le hook
cat > "${HOOK_FILE}" <<'EOF'
#!/usr/bin/env bash
set -euo pipefail

echo "🔒 Vérification Gradle Wrapper avant commit..."
scripts/setup-gradle-wrapper.sh
EOF

# Rend le hook exécutable
chmod +x "${HOOK_FILE}"

echo "✅ Hook pre-commit installé avec succès."