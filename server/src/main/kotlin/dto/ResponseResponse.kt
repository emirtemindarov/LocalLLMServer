package proj.emirtemindarov.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseResponse(
    val output: List<ResponseOutput>,
    val usage: ResponsesUsage
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