package components

import app.model.ChatMessage
import app.model.MessageType
import dev.fritz2.binding.RootStore
import dev.fritz2.binding.watch
import dev.fritz2.components.*
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.values
import dev.fritz2.remote.body
import dev.fritz2.remote.websocket
import dev.fritz2.styling.p
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.FlexParams
import dev.fritz2.styling.params.Style
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object TextAreaInput : RootStore<String>("") {
    val getMessage = {ChatMessage(this.current, true, UsernameStore.current)}
}

object ChatMessagesStore : RootStore<List<ChatMessage>>(emptyList()) {
    val addOwnMessage = handle { list ->
        TextAreaInput.update("")
        val message = TextAreaInput.getMessage()
        if (message.text.isNotEmpty()) {
            session.send(Json.encodeToString(message))
            list.plus(message)
        } else {
            list
        }
    }

    val addTheirMessage = handle { list, message: ChatMessage ->
        list.plus(message.copy(ownMessage = false))
    }

    private val session = websocket("ws://localhost:8080").connect().also {
        it.messages.body.onEach {
            addTheirMessage(Json.decodeFromString(it))
        }.watch()
    }
    val join = handle { list ->
        session.send(Json.encodeToString(ChatMessage("", true, UsernameStore.current, MessageType.JOINING)))
        list
    }

    val leave = handle {list ->
        session.send(Json.encodeToString(ChatMessage("", true, UsernameStore.current, MessageType.LEAVING)))
        session.close()
        list
    }

}

@ExperimentalCoroutinesApi
fun RenderContext.chat() {

    val flexBoxConfig: Style<FlexParams> = {
        direction { column }
        justifyContent { center }
        minHeight { "100%" }
    }

    val chatWindow: Style<FlexParams> = {
        flex { grow { "1" } }
        justifyContent { flexEnd }
        alignItems { stretch }
    }

    val message: Style<FlexParams> = {
        maxWidth { "600px" }
        fontSize { "1.25rem" }
        margin { "0 auto 1rem" }
        padding { "0.5rem 1.5rem" }
    }

    val myMessage: Style<FlexParams> = {
        message()
        margins { right { "1em" } }
    }

    val theirMessage: Style<FlexParams> = {
        message()
        margins { left { "1em" } }
    }

    val messageParagraph: Style<BasicParams> = {
        radius { "1.15rem" }
        lineHeight { "1.25" }
        width { "100%" }
        padding { "0.5rem .875rem" }
        css("overflow-wrap: break-word;")
        after {
            height { "1rem" }
        }
        before {
            height { "1rem" }
        }
    }

    val myParagraph: Style<FlexParams> = {
        messageParagraph()
        alignItems { flexEnd }
        margins { left { auto } }
        background { color { "#248bf5" } }
        color { "#fff" }
        after {
            background { color { "#fff" } }
            width { "10px" }
        }
    }

    val theirParagraph: Style<FlexParams> = {
        messageParagraph()
        alignItems { flexStart }
        background { color { "#e5e5ea" } }
        color { "#000" }
        after {
            background { color { "#fff" } }
            width { "10px" }
        }
    }

    val chatInput: Style<BasicParams> = {
        display { flex }
        position { sticky { bottom { "0" } } }
    }

    val textInput: Style<BasicParams> = {
        margins {
            left { auto }
            right { auto }
        }
        flex { grow { "1" } }
    }

    val buttonInput: Style<BasicParams> = {
        height { auto }
    }

    flexBox(flexBoxConfig) {
        stackUp(chatWindow) {
            items {
                ChatMessagesStore.data.renderEach { message ->
                    if (message.ownMessage) {
                        box(myMessage) {
                            p(myParagraph) {
                                + message.text
                            }
                        }
                    } else {
                        box(theirMessage) {
                            p(theirParagraph) {
                                + message.text
                            }
                        }
                    }
                }
            }
        }
        box(chatInput) {
            textArea(textInput) {
                placeholder("Enter your message...")
                resizeBehavior { none }
                value(TextAreaInput.data)
                events {changes.values() handledBy TextAreaInput.update}
            }
            clickButton(buttonInput) {
                text("Send")
            } handledBy ChatMessagesStore.addOwnMessage
        }
    }
}
