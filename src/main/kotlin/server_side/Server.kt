package server_side

import common.NewMessageFromUserMessage
import common.NewUserConnectedMessage
import common.UserDisconnectedMessage
import java.net.ServerSocket
import javax.net.ServerSocketFactory

class Server(private val port: Int) : Runnable {

    private var clients = mutableMapOf<String, ServerClientActor>()
    private lateinit var socket : ServerSocket

    fun start() {
        socket = ServerSocketFactory.getDefault().createServerSocket(port)
        Thread(this).run()
    }

    override fun run() {
        while (socket.isBound) {
            val clientSocket = socket.accept()

            val newClient = ServerClientActor(clientSocket, this)
            newClient.start()
        }
    }


    fun nameExists(username: String) = clients.containsKey(username)

    fun registerNewUser(username: String, client: ServerClientActor) {
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