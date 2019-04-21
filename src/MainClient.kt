import client_side.Client
import client_side.CmdClient
import server_side.Server

fun main() {
    val port = 6759
    val client = CmdClient("localhost", port)
    println("Starting client")
    client.start()
}