package client_side

import common.*
import java.net.Socket

class Client(override val socket: Socket, val ui: ClientInterface) : BaseClientActor(), MessageHandler {
    override val messageHandler = this

    lateinit var username: String

    fun start(name: String) {
        start()
        username = name
        messageQueue.put(AssignUsernameMessage(username))
    }

    override val registeredMessages =
        listOf(
            NewUserConnectedMessage(""),
            NewMessageFromUserMessage("", ""),
            ConnectionEstablishedMessage()
        ).map { it.messageHeader to it }.toMap()

    override fun handleMessage(message: ChatMessage, params: ArrayList<String>) {
        when (message) {
            is NewUserConnectedMessage -> {
                val name = params[0]
                if (name != username)
                    ui.addNewUser(name)
            }
            is NewMessageFromUserMessage -> {
                val name = params[0]
                val msg = params[1]
                if (name != username)
                    ui.newMessageFromUser(name, msg)
            }
            is ConnectionEstablishedMessage -> {
                println("Successfully connected to server.")
            }
        }
    }

}
