package proj.emirtemindarov.ollama

import kotlinx.serialization.Serializable

@Serializable
data class OllamaRequest(
    val model: String,
    val messages: List<OllamaMessage>,
    val stream: Boolean = false,
    val think: Boolean = false
)

@Serializable
data class OllamaResponse(
    val message: OllamaMessage
)

@Serializable
data class OllamaMessage(
    val role: String,
    val content: String,
    val thinking: String? = null
)