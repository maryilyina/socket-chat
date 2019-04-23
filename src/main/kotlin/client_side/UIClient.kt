package client_side

interface UIClient : Runnable {

    fun addNewUser(name: String)

    fun askUsername() : String
    fun newMessageFromUser(name: String, msg: String)
    fun connect()
    fun usernameUsed()
}
