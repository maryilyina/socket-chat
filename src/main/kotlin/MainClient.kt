import client_side.Client

fun main() {
    val port = 6759
    val client = Client("localhost", port)
    println("Starting client")
    client.start()
}