package common

import java.net.Socket
import java.util.concurrent.LinkedBlockingQueue

abstract class BaseClientActor {

    protected var messageQueue = LinkedBlockingQueue<ChatMessage>()
    abstract val registeredMessages : Map<String, ChatMessage>
    abstract val socket: Socket
    abstract val messageHandler: MessageHandler

    open fun start() {
        val writer = MessageWriter(socket.getOutputStream(), messageQueue)
        val reader = MessageReader(socket.getInputStream(), messageHandler, registeredMessages)

        Thread(writer).start()
        Thread(reader).start()
    }

}