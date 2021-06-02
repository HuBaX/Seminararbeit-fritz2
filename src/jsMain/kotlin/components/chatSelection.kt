package components

import app.model.Chat
import dev.fritz2.binding.RootStore
import dev.fritz2.components.box
import dev.fritz2.components.clickButton
import dev.fritz2.components.modal
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.FlexParams
import dev.fritz2.styling.params.Style
import kotlinx.coroutines.ExperimentalCoroutinesApi

object OpenedChats : RootStore<List<Chat>>(mutableListOf(Chat("1", ""))){

}

@ExperimentalCoroutinesApi
fun RenderContext.chatSelection() {
    val navStyle: Style<FlexParams> = {
        display { flex }
        direction { column }
        justifyContent { center }
    }

    val addChatButtonStyle: Style<BasicParams> = {
        margins {
            top { "15px" }
            bottom { "20px" }
        }
        background {
            color { "rgb(65 154 197)" }
        }
        radius { "0em" }
        fontSize { "1.5em" }
    }

    val chatList: Style<BasicParams> = {
        display { block }
        width { "100%" }
        textAlign { center }
        fontSize { huge }
    }

    val chatElement: Style<BasicParams> = {
        height { "50px" }
        hover {
            css("cursor: pointer")
            background { color { "#0a415b" } }
        }
    }

    box(navStyle) {
        clickButton(addChatButtonStyle) { text("Add new Chat") } handledBy modal({maxHeight { "50%" }
        overflowY { auto }
        maxWidth { "" }}) { content {addChatDialog()} }
        box(chatList) {
            OpenedChats.data.renderEach { chat ->
                box(chatElement) {
                    +"${chat.username}"
                }
            }
        }
    }
}

