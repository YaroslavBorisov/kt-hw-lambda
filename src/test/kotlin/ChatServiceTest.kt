import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ChatServiceTest {
    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun addChatTest() {
        assertEquals(ChatService.addChat("Чат 1"), 1)
    }

    @Test(expected = MessageNotFoundException::class)
    fun deleteMessageExceptionTest() {
        ChatService.addChat("Чат 1")
        ChatService.addMessage(1,"Сообщение 1 в чате 1",3)
        ChatService.deleteMessage(1,5)
    }

    @Test(expected = ChatNotFoundException::class)
    fun addMessageExceptionTest() {
        ChatService.addMessage(3,"Сообщение 1 в чате 3",3)
    }

    @Test
    fun addMessageTest() {
        ChatService.addChat("Чат 1")
        assertEquals(ChatService.addMessage(1,"Сообщение 1 в чате 1",3), 1)
    }


}