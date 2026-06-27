package proj.emirtemindarov

import io.ktor.http.ContentType
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import proj.emirtemindarov.dto.*
import proj.emirtemindarov.ollama.*

fun Application.configureRouting() {

    val ollama = OllamaClient()

    routing {

        get("/") {
            call.respondText("LLM Server")
        }

        post("/v1/responses") {

            val request = call.receive<ResponseRequest>()

            val messages = request.input.flatMap { item ->
                item.content.map {
                    OllamaMessage(
                        role = item.role,
                        content = it.text
                    )
                }
            }

            val ollamaResponse = ollama.chat(
                OllamaRequest(
                    model = "qwen3:8b",
                    messages = messages,
                    stream = false
                )
            )

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
                        input_tokens = 0,
                        output_tokens = 0,
                        total_tokens = 0,
                        output_tokens_details = ReasoningTokens(0)
                    )
                )
            )
        }
    }
}