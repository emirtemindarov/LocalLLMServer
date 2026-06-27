package proj.emirtemindarov.ollama

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.serialization.encodeToString

class OllamaClient {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(json)
        }
    }

    suspend fun chat(request: OllamaRequest): OllamaResponse {
        val body = json.encodeToString(request)

        println(body)

        val response = client.post("http://localhost:11434/api/chat") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        val text = response.body<String>()

        return json.decodeFromString<OllamaResponse>(text)
    }
}