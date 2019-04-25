package client_side

import common.*
import java.net.Socket

class Client(override val socket: Socket, private val ui: UIClient) : BaseClientActor() {

    lateinit var username: String

    override val registeredMessages =
        listOf(
            NewUserConnectedMessage(""),
            NewMessageFromUserMessage("", ""),
            ConnectionEstablishedMessage(),
            UsernameExistsMessage(),
            UserDisconnectedMessage("", false),
            StopSessionMessage(),
            ConnectionLostMessage()
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
                    ui.incomingMessage(name, msg)
            }
            is NewUserConnectedMessage -> {
                val name = params[0]
                if (name != username)
                    ui.newUserInChat(name)
            }
            is UsernameExistsMessage -> {
                ui.usernameExists()
                registerName(ui.askUsername())
            }
            is UserDisconnectedMessage -> {
                val name = params[0]
                val unexpectedly = params[1]
                ui.userLeftChat(name, unexpectedly.toBoolean())
            }
            is StopSessionMessage -> {
                ui.disconnect()
                socket.close()
            }
            is ConnectionLostMessage -> {
                ui.connectionLost()
            }
        }
    }

    override fun start() {
        super.start()
        registerName(ui.askUsername())
    }

    fun registerName(name: String) {
        username = name
        messageQueue.put(AssignUsernameMessage(username))
    }

    fun sendMessage(messageToSend: String) {
       messageQueue.put(NewMessageFromUserMessage(username, messageToSend))
    }

    fun requestDisconnect() {
        messageQueue.put(DisconnectRequestMessage())
        super.disconnect()
    }

}
