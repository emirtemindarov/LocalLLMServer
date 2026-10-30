
package proj.emirtemindarov.ollama

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OllamaClient {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val client = HttpClient(OkHttp) {
        expectSuccess = false

        install(HttpTimeout) {
            connectTimeoutMillis = 60_000
            socketTimeoutMillis = 1_200_000
            requestTimeoutMillis = 1_200_000
        }
    }

    suspend fun chat(request: OllamaRequest): OllamaResponse {
        val body = json.encodeToString(request)

        println("Ollama request:")
        println(body)

        val startTime = System.currentTimeMillis()

        val response = try {
            client.post("http://localhost:11434/api/chat") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        } catch (e: Exception) {
            println("Ollama connection error:")
            e.printStackTrace()
            throw e
        }

        val responseText = response.bodyAsText()

        println("Ollama HTTP status: ${response.status}")
        println("Ollama response time: ${System.currentTimeMillis() - startTime} ms")
        println("Ollama response:")
        println(responseText)

        if (!response.status.isSuccess()) {
            throw IllegalStateException(
                "Ollama HTTP ${response.status.value}: $responseText"
            )
        }

        return try {
            val ollamaResponse = json.decodeFromString<OllamaResponse>(responseText)

            println("Input tokens: ${ollamaResponse.prompt_eval_count}")
            println("Output tokens: ${ollamaResponse.eval_count}")
            println("Prompt evaluation: ${ollamaResponse.prompt_eval_duration} ns")
            println("Generation: ${ollamaResponse.eval_duration} ns")

            ollamaResponse
        } catch (e: Exception) {
            println("Ollama response parsing error:")
            e.printStackTrace()
            throw e
        }
    }
}
