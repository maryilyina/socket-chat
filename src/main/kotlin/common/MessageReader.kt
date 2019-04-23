package common

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MessageReader(
    inputStream: InputStream,
    private val messageHandler: MessageHandler,
    private val registeredMessages: Map<String, ChatMessage>
) : Runnable {

    private val streamReader = BufferedReader(InputStreamReader(inputStream))

    private fun isConnected() = true

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
                else throw Exception("Reader: Unknown type of message: $messageHeader")
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

}