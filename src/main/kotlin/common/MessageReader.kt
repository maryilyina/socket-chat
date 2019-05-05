package common

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MessageReader(
    inputStream: InputStream,
    private val registeredMessages: Map<String, ChatMessage>,
    private val messageHandler: MessageHandler
) : Runnable {

    private val streamReader = BufferedReader(InputStreamReader(inputStream))

    var connected = true
    private fun isConnected() = connected

    override fun run() {
        try {
            while (isConnected()) {
                val messageHeader = streamReader.readLine()
                //println("Reader: Received message $messageHeader")

                if (messageHeader in registeredMessages) {
                    val messageType = registeredMessages.getValue(messageHeader)
                    val params = arrayListOf<String>()
                    for (i in 0 until messageType.paramsCnt())
                        params.add(streamReader.readLine())

                    messageHandler.handleMessage(messageType, params)
                }
                else {
                    println("Reader: Unknown type of message: $messageHeader")
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

}