package components


import app.model.*
import dev.fritz2.binding.RootStore
import dev.fritz2.binding.SimpleHandler
import dev.fritz2.components.clickButton
import dev.fritz2.components.formControl
import dev.fritz2.components.validation.WithValidator
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.remote.*
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Response
import styling.defaultButton
import styling.dialogFormControl


@ExperimentalCoroutinesApi
fun RenderContext.registerDialog(closeHandler: SimpleHandler<Unit>) {
    val registerStore = object : RootStore<User>(User()),
        WithValidator<User, UserCreationPhase> {
        override val validator = UserValidator
        val authApi = http("http://localhost:8080/auth").acceptJson().contentType("application/json")

        init {
            validate(UserCreationPhase.Input)
        }
        val register = handle { model ->
            if (validator.isValid(model, UserCreationPhase.Registration)) {
                val response: String = authApi.body(Json.encodeToString(model)).post("/register").getBody()
                val newUser = Json.decodeFromString<User>(response)
                UsernameStore.update(newUser.username)
                LoggedInStore.update(true)
                closeHandler()
                ChatMessagesStore.join()
                }
            model
        }


    }

    val usernameStore = registerStore.sub(L.User.username)
    val emailStore = registerStore.sub(L.User.email)
    val passwordStore = registerStore.sub(L.User.password)

    form {
        method("dialog")

        //Username
        formControl(dialogFormControl) {
            label("Username")
            labelStyle {
                Theme().formControl.label()
                color { info.main }
            }
            inputField(value = usernameStore) {
                placeholder("Username")
                type("text")
            }
        }

        //E-Mail
        formControl(dialogFormControl) {
            label("E-Mail")
            labelStyle {
                Theme().formControl.label()
                color { info.main }
            }
            helperText("Make sure this is correct. Otherwise we can't restore your password.")
            helperTextStyle {
                Theme().formControl.helperText()
                fontStyle { italic }
                color { secondary.main }
            }
            inputField(value = emailStore) {
                placeholder("E-Mail")
                type("email")
            }
        }

        //Password
        formControl(dialogFormControl) {
            label("Password")
            labelStyle {
                Theme().formControl.label()
                color { info.main }
            }
            helperText("Remember: the longer, the better!")
            helperTextStyle {
                Theme().formControl.helperText()
                fontStyle { italic }
                color { secondary.main }
            }
            inputField(value = passwordStore) { placeholder("Password")
                type("password")}
        }

        div {
            clickButton(defaultButton) {
                text("Cancel")
                variant { outline }
            } handledBy closeHandler
            clickButton(defaultButton) {
                text("Register")
            } handledBy registerStore.register
        }

    }
}
