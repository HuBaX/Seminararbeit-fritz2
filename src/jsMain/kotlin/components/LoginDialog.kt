package components


import app.model.L
import app.model.User
import dev.fritz2.binding.RootStore
import dev.fritz2.binding.SimpleHandler
import dev.fritz2.components.clickButton
import dev.fritz2.components.formControl
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.remote.getBody
import dev.fritz2.remote.http
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Response
import styling.defaultButton
import styling.dialogFormControl

@ExperimentalCoroutinesApi
fun RenderContext.loginDialog(closeHandler: SimpleHandler<Unit>) {

    val loginStore = object : RootStore<User>(User()){

        val authApi = http("http://localhost:8080/auth").acceptJson().contentType("application/json")

        val login = handle { model ->
            val response: Response = authApi.body(Json.encodeToString(model)).post("/login")
            if (response.status.equals(200)) {
                val user = Json.decodeFromString<User>(response.getBody())
                UsernameStore.update(user.username)
                LoggedInStore.update(true)
                closeHandler()
                ChatMessagesStore.join()
            }
            model
        }
    }

    val usernameStore = loginStore.sub(L.User.username)
    val passwordStore = loginStore.sub(L.User.password)

    form {
        method("dialog")
        //Username
        formControl(dialogFormControl) {
            label("Username")
            inputField(value = usernameStore) {
                placeholder("Username")
                type("text")
            }

        }

        //Password
        formControl(dialogFormControl) {
            label("Password")
            inputField(value = passwordStore) {
                placeholder("Password")
                type("password")
            }
        }

        div {
            clickButton(defaultButton) {
                text("Cancel")
                variant{ outline }
            } handledBy closeHandler
            clickButton(defaultButton) {
                text("Login")
            } handledBy loginStore.login
        }
    }
}
