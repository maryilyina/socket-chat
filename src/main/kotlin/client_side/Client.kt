package client_side

import common.*
import java.net.Socket

class Client(override val socket: Socket, private val ui: UIClient) : BaseClientActor(), MessageHandler {
    override val messageHandler = this

    lateinit var username: String

    override val registeredMessages =
        listOf(
            NewUserConnectedMessage(""),
            NewMessageFromUserMessage("", ""),
            ConnectionEstablishedMessage()
        ).map { it.messageHeader to it }.toMap()

    override fun handleMessage(message: ChatMessage, params: ArrayList<String>) {
        when (message) {
            is ConnectionEstablishedMessage -> {
                ui.connect()
            }
            is NewMessageFromUserMessage -> {
                val name = params[0]
                val msg = params[1]
                if (name != username)
                    ui.newMessageFromUser(name, msg)
            }
            is NewUserConnectedMessage -> {
                val name = params[0]
                if (name != username)
                    ui.addNewUser(name)
            }
        }
    }

    fun start(name: String) {
        start()
        username = name
        messageQueue.put(AssignUsernameMessage(username))
    }

    fun sendMessage(messageToSend: String) {
       messageQueue.put(NewMessageFromUserMessage(username, messageToSend))
    }

}
