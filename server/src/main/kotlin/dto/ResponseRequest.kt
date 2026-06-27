package proj.emirtemindarov.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ResponseRequest(
    val model: String,
    val input: List<InputItem>,
    val text: TextConfig? = null,
    val temperature: Double = 0.01
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