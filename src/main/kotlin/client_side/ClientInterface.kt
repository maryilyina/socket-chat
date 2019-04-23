package client_side

interface ClientInterface {

    fun addNewUser(name: String)

    fun askUsername() : String
    fun newMessageFromUser(name: String, msg: String)
}
