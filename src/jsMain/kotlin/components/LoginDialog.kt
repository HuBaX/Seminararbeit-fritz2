package components


import dev.fritz2.components.clickButton
import dev.fritz2.components.formControl
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import styling.defaultButton
import styling.dialogFormControl

@ExperimentalCoroutinesApi
fun RenderContext.loginDialog() {
    form {
        method("dialog")
        //Username
        formControl(dialogFormControl) {
            label("Username")
            inputField { placeholder("Username")
            type("text")
            }

        }

        //Password
        formControl(dialogFormControl) {
            label("Password")
            inputField { placeholder("Password")
            type("password")
            }
        }

        div {
            clickButton(defaultButton) {
                text("Cancel")
                variant{ outline }
            }
            clickButton(defaultButton) {
                text("Login")
            }
        }
    }
}
