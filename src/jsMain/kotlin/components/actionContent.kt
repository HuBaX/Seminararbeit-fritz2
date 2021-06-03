package components

import dev.fritz2.binding.RootStore
import dev.fritz2.components.clickButton
import dev.fritz2.components.flexBox
import dev.fritz2.components.modal
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.styling.p
import kotlinx.coroutines.ExperimentalCoroutinesApi
import styling.defaultButton

object LoggedInStore : RootStore<Boolean>(false) {
    val logOut = handle { flag ->
        ChatMessagesStore.update(emptyList())
        ChatMessagesStore.leave()
        !flag
    }
}

object UsernameStore : RootStore<String>("")

@ExperimentalCoroutinesApi
fun RenderContext.actionContent() {
    div {
        LoggedInStore.data.render { isLoggedIn ->
            if (isLoggedIn) {
                flexBox({justifyContent { spaceBetween }}) {
                    p({margin { "10px" }}) { +"You're logged in as ${UsernameStore.current}" }
                    clickButton(defaultButton) {
                        text("Logout")
                    } handledBy LoggedInStore.logOut
                }
            } else {
                clickButton(defaultButton) {
                    text("Login")
                } handledBy modal { closeHandler ->
                    content { loginDialog(closeHandler) }
                    size { small }
                }

                clickButton(defaultButton) {
                    text("Register")
                } handledBy modal { closeHandler ->
                    content { registerDialog(closeHandler) }
                    size { small }
                }

            }
        }
    }
}

