package components

import dev.fritz2.components.box
import dev.fritz2.components.formControl
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.Style
import kotlinx.coroutines.ExperimentalCoroutinesApi
import styling.dialogFormControl

@ExperimentalCoroutinesApi
fun RenderContext.addChatDialog() {
    val userList: Style<BasicParams> = {
        display { block }
        width { "100%" }
        textAlign { center }
        fontSize { large }
    }

    val userElement: Style<BasicParams> = {
        height { "30px" }
        hover {
            background {
                color { "whitesmoke" }
            }
            css("cursor: pointer")
        }
    }

    form {
        formControl(dialogFormControl) {
            label("Username")
            inputField { placeholder("Enter the User")
                type("text")}
        }
        box(userList) {
            box(userElement) {
                +"First User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }
            box(userElement) {
                +"Second User"
            }



        }
    }
}
