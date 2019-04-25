package common

import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.SocketException
import java.util.concurrent.BlockingQueue

class MessageWriter(stream: OutputStream,
                    private val messageQueue: BlockingQueue<ChatMessage>,
                    private val handler: MessageHandler) : Runnable {

    private val streamWriter = BufferedWriter(OutputStreamWriter(stream))
    var connected = true
    private fun isConnected() = connected

    override fun run() {
        while (isConnected()) {
            if (messageQueue.isNotEmpty()) {
                val message = messageQueue.poll()
                if (message is StopSessionMessage) {
                    this.connected = false
                    handler.handleMessage(message)
                    break
                }

                try {
                    streamWriter.write(message.messageHeader)
                    streamWriter.newLine()
                    for (param in message.params) {
                        streamWriter.write(param)
                        streamWriter.newLine()
                    }
                    streamWriter.flush()
                    //println("Writer: Sent message: '${message.messageHeader}'")
                }
                catch(ex: SocketException) {
                    handler.handleMessage(ConnectionLostMessage())
                }
            }
        }
    }

}