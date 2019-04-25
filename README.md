# Simple socket chat in Kotlin

This is a messenger with basic functionality - authenticating by name, sending messages, disconnecting from it. The server notifies chat participants on the event of new user joining chat, leaving chat, new message coming.

The chat is based on client-server actor communication via web-sockets. There is a list of messages used for communication between server and client as an API:

- ConnectionEstablishedMessage
- AssignUsernameMessage
- UsernameExistsMessage
- NewUserConnectedMessage
- NewMessageFromUserMessage
- DisconnectRequestMessage
- UserDisconnectedMessage
- ConnectionLostMessage
- StopSessionMessage

### Usage

Please run `src/main/MainServer.kt` to start server and `src/main/MainClient.kt`
