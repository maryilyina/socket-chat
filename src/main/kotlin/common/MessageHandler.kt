package common

interface MessageHandler {
    fun handleMessage(message: ChatMessage, params: ArrayList<String>)
}
