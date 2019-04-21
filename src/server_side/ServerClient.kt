package server_side

import common.*
import java.net.Socket

class ServerClient(override val socket: Socket, val headServer: Server) : BaseClientActor(), MessageHandler {

    override val messageHandler = this

    lateinit var username : String

    override val registeredMessages = listOf(
        NewUserConnectedMessage(""),
        AssignUsernameMessage(""),
        NewMessageFromUserMessage("", "")
    ).map { it.messageHeader to it }.toMap()

    override fun handleMessage(message: ChatMessage, params: ArrayList<String>) {
        when (message) {
            is AssignUsernameMessage -> {
                val name = params[0]
                println("Asked to assign client to $name")

                if (!headServer.nameExists(name)) {
                    username = name
                    messageQueue.put(ConnectionEstablishedMessage())
                    headServer.registerNewUser(name, this)
                }

            }
        }
    }

    fun sendMessage(message: ChatMessage) {
        messageQueue.put(message)
    }
}
