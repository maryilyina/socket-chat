package client_side

interface UIClient : Runnable {

    fun connect()
    fun askUsername() : String
    fun incomingMessage(name: String, msg: String)
    fun usernameExists()
    fun newUserInChat(name: String)
    fun userLeftChat(name: String)
    fun disconnect()
}
