package sigint.comint

import sigint.audit.LogManager
import sigint.core.EventBus

/**
 * SRC - ComintTranscriber
 * Analyse le texte décodé ou transcrit pour identifier des menaces critiques.
 */
object ComintTranscriber {

    // Liste de mots-clés haute priorité (Dictionnaire Tactique)
    private val TACTICAL_KEYWORDS = mapOf(
        "ATTAQUE" to 5,
        "POSITION" to 3,
        "REPLI" to 2,
        "CONTACT" to 4,
        "VISUEL" to 3,
        "EMBUSCADE" to 5
    )

    /**
     * Analyse une chaîne de caractères issue d'une transcription ou d'un décodage DATA
     */
    fun analyzeText(content: String, frequency: Double) {
        val upperContent = content.uppercase()
        var highestPriority = 0
        val detectedKeywords = mutableListOf<String>()

        // Recherche de mots-clés
        TACTICAL_KEYWORDS.forEach { (keyword, priority) ->
            if (upperContent.contains(keyword)) {
                detectedKeywords.add(keyword)
                if (priority > highestPriority) highestPriority = priority
            }
        }

        if (detectedKeywords.isNotEmpty()) {
            triggerTacticalAlert(detectedKeywords, highestPriority, frequency)
        }
    }

    private fun triggerTacticalAlert(keywords: List<String>, priority: Int, freq: Double) {
        val alertMessage = "MOTS-CLÉS DÉTECTÉS: ${keywords.joinToString(", ")} sur $freq MHz"
        LogManager.warn("COMINT_INTEL: $alertMessage (Priorité: $priority)")

        // Publication sur le bus d'événements pour mise à jour de la carte (UI)
        EventBus.publish("TACTICAL_ALARM", mapOf(
            "source" to "COMINT",
            "msg" to alertMessage,
            "priority" to priority,
            "timestamp" to System.currentTimeMillis()
        ))
    }
}

