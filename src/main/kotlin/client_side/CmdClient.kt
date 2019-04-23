package client_side

import java.net.Socket

const val STOP_MESSAGE = "/quit"

class CmdClient(serverAddress: String, serverPort: Int): UIClient {

    private val socket = Socket(serverAddress, serverPort)
    private lateinit var client : Client
    private var connectedToServer = false

    fun start() {
        client = Client(socket, this)
        client.start()
    }

    override fun connect() {
        connectedToServer = true
        Thread(this).start()
    }

    override fun run() {
        println("Successfully connected to server. Enter your messages. \nSend '/quit' message to disconnect")
        while (connectedToServer) {
            val messageToSend = readLine() ?: continue
            if (messageToSend == STOP_MESSAGE) client.requestDisconnect()
            else client.sendMessage(messageToSend)
        }
    }

    override fun askUsername(): String {
        var name : String? = null
        while (name == null) {
            println("Please enter the username for the chat:")
            name = readLine()
        }
        return name
    }

    override fun newUserInChat(name: String) {
        println("New user $name in chat")
    }

    override fun incomingMessage(name: String, msg: String) {
        println("$name: $msg")
    }

    override fun usernameExists() {
        println("Username is already registered. Please select a new one.")
    }

    override fun userLeftChat(name: String) {
        println("User $name left chat.")
    }

    override fun disconnect() {
        println("Disconnected from server.")
    }
}
