import client_side.CmdClient

fun main() {
    val port = 6759
    val client = CmdClient("localhost", port)
    println("Starting client")
    client.start()
}