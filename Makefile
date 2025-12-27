# 🛡️ Makefile SOVEREIGN-CORE-PSC
# Orchestration tactique du moteur souverain

GRADLEW=./gradlew
JAR=build/libs/sigint-core-all.jar
TARGET=127.0.0.1

.PHONY: build verify launch audit stress clean lock

## 🔧 Compilation du noyau
build:
	$(GRADLEW) clean shadowJar --no-daemon

## 🛡️ Vérification du JAR
verify:
	@if [ -f "$(JAR)" ]; then \
		echo "✔️ JAR détecté : $(JAR)"; \
	else \
		echo "❌ Erreur : JAR non généré"; exit 1; \
	fi

## 🚀 Lancement du noyau
launch: build verify
	java -jar $(JAR)

## 🔍 Audit des ports ouverts
audit: build verify
	java -jar $(JAR) --audit $(TARGET)

## 💥 Test de résilience (stress test)
stress: build verify
	java -jar $(JAR) --stress $(TARGET)

## 🧹 Nettoyage
clean:
	rm -rf build/libs/*.jar

## 🔒 Verrouillage Gradle 8.2
lock:
	rm -rf gradle gradlew gradlew.bat .gradle
	gradle wrapper --gradle-version 8.2 --distribution-type bin
	chmod +x gradlew
	$(GRADLEW) -v | grep "Gradle 8.2" || \
		(echo "❌ Gradle 8.2 non détecté"; exit 1)