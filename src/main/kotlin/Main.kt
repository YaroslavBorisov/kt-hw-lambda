

fun main(){
    ChatService.addChat("Чат 1")
    ChatService.addMessage(1,"Сообщение 1 в чате 1",1)
    ChatService.addChat("Чат 222")
    ChatService.addMessage(2,"Сообщение 1 в чате 2",1)
    ChatService.addMessage(2,"Сообщение 2 в чате 2",1)
    ChatService.addMessage(2,"Сообщение 3 в чате 2",2)
    ChatService.addChat("Чат 33333")
    ChatService.addMessage(3,"Сообщение 1 в чате 3",3)
    ChatService.deleteMessage(3,1)
    //ChatService.deleteMessage(3,1)

    println(ChatService.getChats())
    println("Непрочитанных чатов: " + ChatService.getUnreadChatsCount())
    println("Последние сообщения в чатах:\n" + ChatService.getLastMessages())

    println(ChatService.getUserMessages(2,1,1))
    println(ChatService.getChats())
}
