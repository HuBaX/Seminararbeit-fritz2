package app.backend

import app.database.*
import app.model.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import java.io.File
import java.util.*

val userValidator = UserValidator


class ChatClient(val username: String, val session: DefaultWebSocketSession)
val clients: MutableList<ChatClient> = Collections.synchronizedList(mutableListOf())
fun Application.main() {
    val currentDir = File(".").absoluteFile
    environment.log.info("Current directory: $currentDir")

    install(ContentNegotiation) {
        json()
    }

    install(WebSockets)

    Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")

    database {
        SchemaUtils.create(UsersTable)
    }


    routing {
        get("/") {
            call.resolveResource("index.html")?.let {
                call.respond(it)
            }
        }

        static("/") {
            resources("/")
        }
        route("/auth") {
            post("/register") {
                val user = call.receive<User>()
                if (userValidator.isValid(user, UserCreationPhase.Registration)) {
                    call.respond(HttpStatusCode.Created, UserDB.add(user))
                }
            }
            post("/login") {
                val user = call.receive<User>()
                val foundUser = UserDB.find(user.username)
                if (foundUser != null) {
                    call.respond(foundUser.toUser())
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "user not found"))
                }

            }
        }

        webSocket {
            val session = this
            try {
                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text-> {
                            val json = frame.readText()
                            val msg = Json.decodeFromString<ChatMessage>(json)
                            when(msg.type) {
                                MessageType.JOINING -> {
                                    clients.add(ChatClient(msg.username, session))
                                }
                                MessageType.MESSAGE -> {
                                    clients.filter { it.username != msg.username }.forEach {it.session.send(Frame.Text(json))}
                                }
                                MessageType.LEAVING -> {
                                    clients.find { it.session == session }.let { client -> clients.remove(client) }
                                }
                            }
                        }
                        else -> call.respond(HttpStatusCode.BadRequest, "Content didn't match")
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }
}

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port) {
        main()
    }.start(wait = true)
}
