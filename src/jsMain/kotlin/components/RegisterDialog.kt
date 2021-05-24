package components

import dev.fritz2.components.clickButton
import dev.fritz2.components.formControl
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import styling.defaultButton
import styling.dialogFormControl

@ExperimentalCoroutinesApi
fun RenderContext.registerDialog() {
    form() {
        method("dialog")

        //Username
        formControl(dialogFormControl) {
            label("Username")
            inputField { placeholder("Username")
                type("text")}
        }

        //E-Mail
        formControl(dialogFormControl) {
            label("E-Mail")
            inputField { placeholder("E-Mail")
                type("email")}
        }

        //Password
        formControl(dialogFormControl) {
            label("Password")
            inputField { placeholder("Password")
                type("password")}
        }

        div {
            clickButton(defaultButton) {
                text("Cancel")
                variant { outline }
            }
            clickButton(defaultButton) {
                text("Register")
            }
        } //Es gibt keine menu-Funktion

    }
}
