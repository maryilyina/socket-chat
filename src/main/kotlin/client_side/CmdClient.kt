package client_side

const val STOP_MESSAGE = "/quit"

class CmdClient(private val client: Client): UIClient {

    private var connectedToServer = false

    override fun start() {
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

    override fun userLeftChat(name: String, unexpectedly: Boolean) {
        println("User $name left chat ${if (unexpectedly) "unexpectedly" else ""}")
    }

    override fun disconnect() {
        println("Disconnected from server.")
        connectedToServer = false
    }

    override fun connectionLost() {
        println("Cannot connect to server. Try again.")
    }
}
