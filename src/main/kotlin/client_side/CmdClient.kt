package client_side

import java.net.Socket

class CmdClient(serverAddress: String, serverPort: Int): ClientInterface {

    private val socket = Socket(serverAddress, serverPort)
    lateinit var client : Client
    lateinit var username : String

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

    override fun addNewUser(name: String) {
        println("New user $name in chat")
    }

    override fun newMessageFromUser(name: String, msg: String) {
        println("$name: $msg")
    }


}