package components

import dev.fritz2.components.clickButton
import dev.fritz2.components.modal
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import styling.defaultButton

@ExperimentalCoroutinesApi
fun RenderContext.actionContent() {
    div {
        clickButton (defaultButton) {
            text("Login")
        } handledBy modal { content {loginDialog()} }

        clickButton (defaultButton){
            text("Register")
        } handledBy modal { content {registerDialog()}}
    }
}

