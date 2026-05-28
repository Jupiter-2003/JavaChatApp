# Java Multiplayer Chat Application — Technical Documentation

# Overview

This project is a terminal-based real-time multiplayer chat application built using Java sockets and multithreading.

The application supports:

- Multiple clients simultaneously
- Usernames
- Public messaging
- Private messaging
- Commands
- Chat rooms/channels
- Real-time communication

The project was developed incrementally in phases to learn networking, concurrency, protocol design, and software architecture concepts.

---

# Core Architecture

The project follows a client-server architecture.

```text
Clients
↓
TCP Socket Connection
↓
Server
↓
Message Routing
↓
Rooms / Commands / Private Messages
```

The server acts as the central communication hub.

Clients never communicate directly with each other.

---

# Technologies Used

| Technology     | Purpose                    |
| -------------- | -------------------------- |
| Java           | Core language              |
| TCP Sockets    | Network communication      |
| Multithreading | Concurrent client handling |
| BufferedReader | Reading socket streams     |
| PrintWriter    | Sending socket streams     |
| ArrayList      | Client storage             |
| HashMap        | Room management            |

---

# Major Concepts Implemented

# 1. Client-Server Architecture

The application separates responsibilities between:

- Server → communication management
- Client → sending and receiving messages

The server continuously listens for incoming connections.

---

# 2. TCP Socket Programming

TCP sockets provide reliable two-way communication.

## Server Side

```java
ServerSocket serverSocket =
new ServerSocket(Constants.PORT);
```

## Client Side

```java
Socket socket =
new Socket("localhost", Constants.PORT);
```

---

# 3. Blocking I/O

Methods such as:

```java
reader.readLine()
```

pause execution until data becomes available.

This is called blocking I/O.

---

# 4. Multithreading

Each connected client gets its own thread.

```java
public class ClientHandler extends Thread
```

This allows multiple users to communicate simultaneously.

---

# 5. Shared State

The server maintains shared collections of:

- connected clients
- chat rooms

Example:

```java
public static ArrayList<ClientHandler> clients
```

---

# 6. Broadcasting

Messages are broadcast only to users inside the same room.

```java
for (ClientHandler client : roomClients)
```

This is called scoped broadcasting.

---

# 7. Command Parsing

Messages beginning with \`/\` are interpreted as commands.

Examples:

```text
/help
/list
/join music
/whisper username hello
```

---

# 8. Dynamic Room Management

Rooms are dynamically managed using:

```java
HashMap<String,
ArrayList<ClientHandler>>
```

This maps:

```text
room name → users in room
```

---

# Communication Flow

## Client Connection Flow

```text
Client starts
→ Connects to server
→ Server accepts connection
→ ClientHandler thread created
→ Username requested
→ User joins default room
```

---

## Public Messaging Flow

```text
User types message
→ Client sends message
→ Server receives message
→ Server routes message to room
→ Room users receive message
```

---

## Private Messaging Flow

```text
/whisper username message
→ Server parses command
→ Server locates target user
→ Private message delivered
```

---

## Room Join Flow

```text
/join music
→ User removed from current room
→ Room created if absent
→ User added to new room
→ Room join notification broadcast
```

---

# File Explanations

## ChatServer.java

Main server class.

Responsibilities:

- Start server socket
- Accept incoming clients
- Maintain global collections
- Create ClientHandler threads

---

## ClientHandler.java

Core networking and routing class.

Responsibilities:

- Read messages
- Broadcast messages
- Handle commands
- Manage rooms
- Send private messages
- Track usernames

---

## ChatClient.java

Client-side networking logic.

Responsibilities:

- Connect to server
- Send messages
- Receive messages
- Handle terminal input

---

## Constants.java

Stores global constants.

Example:

```java
public static final int PORT = 5000;
```

---

# Data Structures Used

## ArrayList

Used for:

- connected clients
- room user lists

---

## HashMap

Used for room management.

Example:

```java
HashMap<String,
ArrayList<ClientHandler>>
```

Purpose:

```text
room name → room users
```

---

## Object-Oriented Design

Each connected client is represented by a \`ClientHandler\` object containing:

- socket
- username
- room
- reader
- writer
- thread

This bundles all client-related data and behavior together.

---

# Concurrency Notes

The server uses one thread per client.

Benefits:

- simultaneous communication
- independent execution
- real-time interaction

Potential future improvements:

- synchronization
- thread-safe collections
- CopyOnWriteArrayList

---

# Supported Commands

| Command                     | Purpose              |
| --------------------------- | -------------------- |
| `/help`                     | Show commands        |
| `/list`                     | Show online users    |
| `/rooms`                    | Show available rooms |
| `/room`                     | Show current room    |
| `/join roomName`            | Join/create room     |
| `/whisper username message` | Send private message |
| `/exit`                     | Disconnect           |

---

# Future Improvements

Potential future upgrades:

- JavaFX GUI
- Web frontend
- WebSockets
- Authentication
- File transfer
- Persistent chat history
- Database integration
- Encryption
- Cloud deployment

---

# Key Learning Outcomes

By completing this project, the following concepts can be learned:

- Socket programming
- TCP networking
- Client-server architecture
- Multithreading
- Blocking I/O
- Shared state management
- Command parsing
- Message routing
- Dynamic room management
- Concurrent systems design
- Real-time communication systems
- Object-oriented architecture

---

# Final Notes

This project evolved from a basic socket connection into a fully functional terminal-based multi-room chat platform.

The project demonstrates strong intermediate-level software engineering concepts and forms an excellent foundation for future work in:

- backend engineering
- distributed systems
- multiplayer systems
- real-time applications
- communication platforms
