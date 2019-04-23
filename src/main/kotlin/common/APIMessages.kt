package common

class APIMessages {

    companion object {
        const val ASSIGN_USERNAME         = "Assign username"
        const val CONNECTION_ESTABLISHED  = "Connection from server received"
        const val NEW_USER_IN_CHAT        = "New client connected"
        const val NEW_MESSAGE             = "New message from user"
        const val USERNAME_EXISTS         = "Username is already registered on server"
        const val DISCONNECT_REQUEST      = "Client disconnects from server"
        const val USER_DISCONNECTED       = "User left chat"
        const val STOP_SESSION            = "Stop communication and close socket"
    }
}