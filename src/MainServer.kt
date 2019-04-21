import client_side.Client
import server_side.Server

fun main() {
    val port = 6759
    val server = Server(port)
    println("Starting server")
    server.start()
}