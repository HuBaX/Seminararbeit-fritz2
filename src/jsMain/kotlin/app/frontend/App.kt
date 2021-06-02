package app.frontend

import components.actionContent
import components.chat
import components.chatSelection
import dev.fritz2.components.*
import dev.fritz2.dom.html.render
import dev.fritz2.routing.router

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun main() {
    val router = router("home")
    render("#chatapp") {
        router.data.render { route ->
            when(route) {
                "Other page" -> flexBox({
                    justifyContent { center }
                    alignItems { center }
                    width { "100%" }
                }) {
                    h1 { +"Welcome to another page! :)" }
                    clickButton {
                        text("Go back")
                    }.map { "home" } handledBy router.navTo
                }
                "home" -> appFrame({ padding { "0" } }) { //TODO: sidebarToggle
                    brand { h1 { +"fritz2 Realtime Chat" } }
                    header {
                        clickButton {
                            text("Other page")
                        }.map { "Other page" } handledBy router.navTo
                    }
                    nav { chatSelection() } // Insert chats
                    main { chat() }
                    actions { actionContent() }
                }
                else -> h1 { +"404 Not found :(" }
            }

        }
    }
}
