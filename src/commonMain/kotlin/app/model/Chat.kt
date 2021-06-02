package app.model

import kotlinx.serialization.Serializable

@Serializable
data class Chat(val id: String, val username: String)
