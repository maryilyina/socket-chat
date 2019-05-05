package client_side

import java.net.ConnectException
import java.net.Socket

class Client(private val serverAddress: String, private val serverPort: Int) {

    private lateinit var client: ClientActor

    fun start() {
        try {
            val socket = Socket(serverAddress, serverPort)
            client = ClientActor(socket, CmdClient(this))
            client.start()
        }
        catch (ex: ConnectException) {
            println("Unable to connect to $serverAddress:$serverPort")
        }
    }

    fun sendMessage(message: String) = client.sendMessage(message)

    fun requestDisconnect() = client.requestDisconnect()
}