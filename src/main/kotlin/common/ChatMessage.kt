package common

open class ChatMessage(open val messageHeader: String, vararg val params: String) {

    fun paramsCnt() = params.size
    override fun toString() = messageHeader
}
