
package proj.emirtemindarov

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.jsonObject
import proj.emirtemindarov.response.*
import proj.emirtemindarov.ollama.*

fun Application.configureRouting() {

    val ollama = OllamaClient()

    routing {

        get("/") {
            call.respondText("LLM Server")
        }

        post("/v1/responses") {

            val request = call.receive<ResponseRequest>()

            // Преобразование входных сообщений
            val messages = request.input.flatMap { item ->
                item.content.map {
                    OllamaMessage(
                        role = item.role,
                        content = it.text
                    )
                }
            }

            // Извлечение JSON Schema из Responses API
            val schema = request.text
                ?.format
                ?.jsonObject
                ?.get("schema")

            // Отправка запроса в Ollama
            val ollamaResponse = ollama.chat(
                OllamaRequest(
                    model = "qwen3:8b",
                    messages = messages,
                    stream = false,
                    think = false,
                    format = schema,
                    options = OllamaOptions(
                        temperature = request.temperature,
                        num_ctx = 8192
                    )
                )
            )

            // Формирование ответа в формате Responses API
            call.respond(
                ResponseResponse(
                    output = listOf(
                        ResponseOutput(
                            id = "local",
                            type = "message",
                            content = listOf(
                                ResponseContent(
                                    type = "output_text",
                                    text = ollamaResponse.message.content
                                )
                            )
                        )
                    ),
                    usage = ResponsesUsage(
                        input_tokens = ollamaResponse.prompt_eval_count?.toLong() ?: 0L,
                        output_tokens = ollamaResponse.eval_count?.toLong() ?: 0L,
                        total_tokens = (
                                (ollamaResponse.prompt_eval_count ?: 0) +
                                        (ollamaResponse.eval_count ?: 0)
                                ).toLong(),
                        output_tokens_details = ReasoningTokens(0)  // FIXME сделать аналогично
                    )
                )
            )
        }
    }
}
