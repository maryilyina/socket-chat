package common

interface MessageHandler {
    fun handleMessage(message: ChatMessage, params: ArrayList<String>)
    fun handleMessage(message: ChatMessage) {
        handleMessage(message, arrayListOf())
    }
}
