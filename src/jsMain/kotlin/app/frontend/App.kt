package app.frontend

import components.actionContent
import components.chat
import components.chatSelection
import dev.fritz2.components.*
import dev.fritz2.dom.html.render
import dev.fritz2.styling.style

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun main() {
    render("#chatapp") {
        appFrame({padding { "0" }}) { //TODO: sidebarToggle
            brand {h1 { +"fritz2 Realtime Chat" }}
            header{p {+"Name of User you're currently chatting with"}}
            nav { chatSelection() } // Insert chats
            main {
                chat() }
            actions { actionContent() }
        }
    }
}
