package app.model

import dev.fritz2.components.validation.*
import dev.fritz2.identification.RootInspector
import dev.fritz2.identification.inspect
import dev.fritz2.lenses.Lenses
import kotlinx.serialization.Serializable

@Lenses
@Serializable
data class User (
    val username: String = "",
    val email: String = "",
    val password: String = ""
)

enum class UserCreationPhase {
    Input,
    Registration
}

object UserValidator : ComponentValidator<User, UserCreationPhase>() {
    override fun validate(data: User, metadata: UserCreationPhase): List<ComponentValidationMessage> {
        val inspector = inspect(data)

        return validateUsername(inspector, metadata) +
                validateEmail(inspector, metadata) +
                validatePassword(inspector, metadata)

    }

    private fun addSuccessMessage(
        messages: MutableList<ComponentValidationMessage>,
        phase: UserCreationPhase,
        id: String
    ) {
        if (phase == UserCreationPhase.Input && !messages.any { it.isError() }) {
            messages.add(successMessage(id, ""))
        }
    }

    private fun validateUsername(
        inspector: RootInspector<User>,
        phase: UserCreationPhase
    ): List<ComponentValidationMessage> {
        val messages = mutableListOf<ComponentValidationMessage>()
        val username = inspector.sub(L.User.username)
        if (username.data.isBlank() && phase == UserCreationPhase.Registration) {
            messages.add(errorMessage(username.id, "Please choose a username."))
        } else if (username.data.length < 3) {
            messages.add(errorMessage(username.id, "Please choose a username with at least 3 characters."))
        }
        addSuccessMessage(messages, phase, username.id)
        return messages
    }

    private fun validateEmail(
        inspector: RootInspector<User>,
        phase: UserCreationPhase
    ): List<ComponentValidationMessage> {
        val messages = mutableListOf<ComponentValidationMessage>()
        val email = inspector.sub(L.User.email)
        if(phase == UserCreationPhase.Registration) {
            if (email.data.isBlank()) {
                messages.add(errorMessage(email.id, "Please enter your e-mail address."))
            } else if (!Regex("\\S+@\\S+\\.\\S+").matches(email.data)) {
                messages.add(errorMessage(email.id, "Please enter a valid e-mail address."))
            }
        }
        addSuccessMessage(messages, phase, email.id)
        return messages
    }

    private fun validatePassword(
        inspector: RootInspector<User>,
        phase: UserCreationPhase
    ): List<ComponentValidationMessage> {
        val messages = mutableListOf<ComponentValidationMessage>()
        val password = inspector.sub(L.User.password)
        if(password.data.isBlank() && phase == UserCreationPhase.Registration) {
            messages.add(errorMessage(password.id, "Please choose a password."))
        } else if (password.data.length < 8) {
            messages.add(warningMessage(password.id, "We recommend a password with at least 8 characters."))
        }
        addSuccessMessage(messages, phase, password.id)
        return messages
    }
}
