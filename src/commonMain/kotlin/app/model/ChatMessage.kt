package app.model

import dev.fritz2.lenses.Lenses
import kotlinx.serialization.Serializable

@Lenses
@Serializable
data class ChatMessage(
    val text: String,
    val ownMessage:Boolean,
    val username: String,
    val type: MessageType = MessageType.MESSAGE,
)

enum class MessageType {
    MESSAGE, JOINING, LEAVING
}
