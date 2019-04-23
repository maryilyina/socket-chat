package common

import java.net.Socket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

abstract class BaseClientActor : MessageHandler {

    protected lateinit var messageQueue : BlockingQueue<ChatMessage>
    protected abstract val registeredMessages : Map<String, ChatMessage>
    protected abstract val socket: Socket

    private lateinit var writer : MessageWriter
    private lateinit var reader : MessageReader

    open fun start() {
        messageQueue = LinkedBlockingQueue<ChatMessage>()
        writer = MessageWriter(socket.getOutputStream(), messageQueue)
        reader = MessageReader(socket.getInputStream(), this, registeredMessages)

        Thread(writer).start()
        Thread(reader).start()
    }

    fun disconnect() {
        reader.connected = false
        messageQueue.put(StopSessionMessage())
        //socket.close()
    }
}