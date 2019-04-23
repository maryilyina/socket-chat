package client_side

import java.net.Socket

class CmdClient(serverAddress: String, serverPort: Int): UIClient {

    private val socket = Socket(serverAddress, serverPort)
    private lateinit var client : Client
    private lateinit var username : String
    private var connectedToServer = false

    fun start() {
        client = Client(socket, this)
        username = askUsername()
        client.start(username)
    }

    override fun askUsername(): String {
        var name : String? = null
        while (name == null) {
            println("Please enter the username for the chat:")
            name = readLine()
        }
        return name
    }

    override fun connect() {
        connectedToServer = true
        Thread(this).start()
    }


    override fun addNewUser(name: String) {
        println("New user $name in chat")
    }

    override fun newMessageFromUser(name: String, msg: String) {
        println("$name: $msg")
    }

    override fun run() {
        println("Successfully connected to server. Enter your messages.")
        while (connectedToServer) {
            val messageToSend = readLine()
            if (messageToSend != null) {
                client.sendMessage(messageToSend)
            }
        }
    }
}