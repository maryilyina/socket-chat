package common

import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.util.concurrent.BlockingQueue

class MessageWriter(stream: OutputStream, val messageQueue: BlockingQueue<ChatMessage>) : Runnable {

    private val streamWriter = BufferedWriter(OutputStreamWriter(stream))
    private val connected = true
    private fun isConnected() = connected

    override fun run() {
        while (isConnected()) {
            if (messageQueue.isNotEmpty()) {
                val message = messageQueue.poll()

                streamWriter.write(message.messageHeader)
                streamWriter.newLine()
                for (param in message.params) {
                    streamWriter.write(param)
                    streamWriter.newLine()
                }
                streamWriter.flush()
                println("Writer: Sent message: '${message.messageHeader}'")
            }
        }
    }

}