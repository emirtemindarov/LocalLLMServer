package proj.emirtemindarov.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ResponseRequest(
    val model: String,
    val temperature: Double,
    val input: List<InputItem>,
    val text: TextConfig? = null
)

@Serializable
data class TextConfig(
    val format: JsonElement? = null
)

@Serializable
data class InputItem(
    val role: String,
    val content: List<InputContent>
)

@Serializable
data class InputContent(
    val type: String,
    val text: String
)






@Serializable
data class ResponseResponse(
    val output: List<ResponseOutput>,   // message в OllamaDto.kt
    val usage: ResponsesUsage           // 4 поля под message в OllamaDto.kt
)

@Serializable
data class ResponseOutput(
    val id: String,
    val type: String,
    val content: List<ResponseContent>
)

@Serializable
data class ResponseContent(
    val type: String,
    val text: String? = null
)

@Serializable
data class ResponsesUsage(
    val input_tokens: Long,
    val output_tokens: Long,
    val total_tokens: Long,
    val output_tokens_details: ReasoningTokens
)

@Serializable
data class ReasoningTokens(
    val reasoning_tokens: Long
)



