package server_side

import common.NewMessageFromUserMessage
import common.NewUserConnectedMessage
import common.StopSessionMessage
import common.UserDisconnectedMessage
import java.net.ServerSocket
import javax.net.ServerSocketFactory

class Server(private val port: Int) {

    private var clients = mutableMapOf<String, ServerClient>()
    private lateinit var socket : ServerSocket

    fun start() {
        socket = ServerSocketFactory.getDefault().createServerSocket(port)
        while (socket.isBound) {
            val clientSocket = socket.accept()

            val newClient = ServerClient(clientSocket, this)
            newClient.start()
        }
    }

    fun nameExists(username: String) = clients.containsKey(username)

    fun registerNewUser(username: String, client: ServerClient) {
        for (other in clients.values) {
            other.sendMessage(NewUserConnectedMessage(username))
        }

        clients[username] = client
        println("Registered user $username")
    }

    fun distributeNewMessage(username: String, msg: String) {
        for ((clientName, client) in clients) {
            if (clientName != username) {
                client.sendMessage(NewMessageFromUserMessage(username, msg))
            }
        }
    }

    fun excludeUser(username: String, unexpectedly: Boolean) {
        clients.remove(username)
        for (other in clients.values) {
            other.sendMessage(UserDisconnectedMessage(username, unexpectedly))
        }
    }

}