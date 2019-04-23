package common

class AssignUsernameMessage(username: String) :
    ChatMessage(APIMessages.ASSIGN_USERNAME, username)

class NewUserConnectedMessage(username: String):
    ChatMessage(APIMessages.NEW_USER_IN_CHAT, username)

class NewMessageFromUserMessage(username: String, message: String) :
    ChatMessage(APIMessages.NEW_MESSAGE, username, message)

class ConnectionEstablishedMessage :
    ChatMessage(APIMessages.CONNECTION_ESTABLISHED)

class UsernameExistsMessage :
    ChatMessage(APIMessages.USERNAME_EXISTS)