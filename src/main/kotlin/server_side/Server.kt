package server_side

import common.NewUserConnectedMessage
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

    fun registerNewUser(name: String, client: ServerClient) {
        for (other in clients.values)
            other.sendMessage(NewUserConnectedMessage(name))

        clients[name] = client
        println("Registered user $name")
    }

}