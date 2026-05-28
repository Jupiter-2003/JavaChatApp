```md
# Java Multiplayer Chat Application

A terminal-based real-time multiplayer chat application built using Java sockets and multithreading.
The project was built incrementally to learn networking, concurrency, protocol design, and backend architecture concepts.

---

# Features

- Multi-client support
- Concurrent communication using threads
- Public room-based chat
- Private messaging
- Dynamic room creation
- Online user listing
- Room listing
- Command-based interaction
- Real-time message broadcasting

---

# Technologies Used

- Java
- TCP Sockets

---

# Supported Commands

| Command                         | Description             |
| ------------------------------- | ----------------------- |
| `/help`                         | Show available commands |
| `/list`                         | Show online users       |
| `/rooms`                        | Show available rooms    |
| `/room`                         | Show current room       |
| `/join <room>`                  | Join or create a room   |
| `/whisper <username> <message>` | Send private message    |
| `/exit`                         | Disconnect from server  |

---

# How To Run

## Compile

\`\`\`bash
javac -d out src/server/_.java src/client/_.java src/common/_.java src/_.java
\`\`\`

---

## Run Server

\`\`\`bash
java -cp out Main
\`\`\`

---

## Run Client

\`\`\`bash
java -cp out ClientMain
\`\`\`

Open multiple client terminals to test multiplayer communication.

---

# Concepts Used

This project demonstrates:

- Client-server architecture
- TCP socket programming
- Multithreading
- Blocking I/O
- Shared state management
- Command parsing
- Message routing
- Real-time communication systems
- Room-based scoped broadcasting
- Object-oriented architecture

---

# Future Improvements

Potential future upgrades include:

- JavaFX GUI
- Web frontend
- WebSockets
- File transfer
- Authentication
- Persistent chat history
- Database integration
- Cloud deployment

---

# Documentation

Detailed technical documentation is available in:

\`\`\`text
docs/project-documentation.md
\`\`\`
```
