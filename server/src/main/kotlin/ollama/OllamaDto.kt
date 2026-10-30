
package proj.emirtemindarov.ollama

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// FIXME данные в файлах Response.. и OllamaDto кажется повторяют друг друга
@Serializable
data class OllamaRequest(
    val model: String,
    val messages: List<OllamaMessage>,
    val stream: Boolean = false,
    val think: Boolean = false,
    val format: JsonElement? = null,
    val options: OllamaOptions? = null
)

@Serializable
data class OllamaOptions(
    val temperature: Double,
    val num_ctx: Int
)

@Serializable
data class OllamaResponse(
    val message: OllamaMessage,
    val prompt_eval_count: Int? = null,
    val eval_count: Int? = null,
    val prompt_eval_duration: Long? = null,
    val eval_duration: Long? = null
)

@Serializable
data class OllamaMessage(
    val role: String,
    val content: String,
    val thinking: String? = null
)
