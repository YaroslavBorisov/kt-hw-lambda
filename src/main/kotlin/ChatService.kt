import kotlin.text.StringBuilder

data class Chat(
    val id: Int,
    val title: String,
    val messages: MutableList<Message> = mutableListOf<Message>(),
    var lastMessageId: Int = 0
)

data class Message(
    val id: Int,
    var date: Long = System.currentTimeMillis(),
    var text: String,
    val userId: Int,
    var isUnread: Boolean = true
)

class ChatNotFoundException(message: String) : RuntimeException(message)
class MessageNotFoundException(message: String) : RuntimeException(message)

object ChatService {
    private val chats = mutableListOf<Chat>()
    private var lastChatId: Int = 0

    fun clear() {
        chats.clear()
        lastChatId = 0
    }

    fun addChat(title: String): Int {
        chats.add(Chat(++lastChatId, title))
        return lastChatId
    }

    fun deleteChat(id: Int) {
        chats.remove(findChat(id))
    }

    private fun findChat(id: Int): Chat {
        val chatList = chats.filter { it.id == id }
        if (chatList.size == 0) throw ChatNotFoundException("Не найден чат с id=$id!")
        return chatList[0]
    }


    fun addMessage(chatId: Int, text: String, userId: Int): Int {
        val chat = findChat(chatId)
        Thread.sleep(2) // Задержка для гарантированного нового значения 'date' в сообщении
        chat.messages.add(Message(++chat.lastMessageId, text = text, userId = userId))
        return chat.lastMessageId
    }

    fun updateMessage(chatId: Int, messageId: Int, text: String) {
        val chat = findChat(chatId)
        val msg = findMessage(chat, messageId)
        msg.text = text
    }

    fun deleteMessage(chatId: Int, messageId: Int) {
        val chat = findChat(chatId)
        val msg = findMessage(chat, messageId)
        chat.messages.remove(msg)
    }


    private fun findMessage(chat: Chat, id: Int): Message {
        val msgList = chat.messages.filter { it.id == id }
        if (msgList.size == 0) throw MessageNotFoundException("Не найдено сообщение с id=$id в чате id=${chat.id}!")
        return msgList[0]
    }

    fun getUnreadChatsCount(): Int {
        return chats.filter {
            it.messages.count { it.isUnread } > 0
        }.count()
    }

    fun getChats(): List<Chat> {
        return chats
    }

    fun getLastMessages(): String {
        val sb = StringBuilder()
        chats.forEach {
            sb.append("Чат: ${it.title}\n")
                .append(if (it.messages.size == 0) "Нет сообщений" else it.messages.filter { msg: Message -> msg.date == it.messages.maxOf { it.date } })
                .append("\n")
        }
        return sb.toString()
    }

    fun getUserMessages(chatId: Int, userId: Int, count: Int): List<Message> {
        return findChat(chatId).messages.filter { it.userId == userId }.sortedByDescending { it.date }.subList(0, count).markAsRead()
    }

    fun List<Message>.markAsRead(): List<Message>
    {
        this.forEach {
            it.isUnread = false
        }
        return this
    }
}