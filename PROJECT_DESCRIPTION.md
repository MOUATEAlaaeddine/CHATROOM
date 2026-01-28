# Project Analysis: Futuristic Chatroom System

## Project Overview
This project is a high-performance, real-time chat application built using the **Spring Boot** framework. It implements a modern layered architecture to ensure scalability, security, and real-time responsiveness. The system allows users to create accounts, join specific chat rooms (via approval), and communicate instantly using **WebSocket** technology.

---

## Technical Architecture & Core Concepts

### 1. Real-Time Communication (Sockets)
The core of this application relies on **WebSockets** (specifically the STOMP protocol over SockJS). 
- Unlike traditional HTTP requests (request/response), WebSockets enable a persistent, full-duplex connection.
- This allows the server to "push" messages to clients instantly without them having to refresh the page.
- **Reference**: `WebSocketConfig.java` defines the communication endpoints, and `ChatController.java` handles the incoming message streams.

### 2. Data Persistence (NoSQL & Spring Data)
While traditional Java projects often use **JDBC** for relational databases, this project leverages **Spring Data MongoDB**. 
- It uses the **Repository Pattern**, which is the modern evolution of the **DAO (Data Access Object)** pattern often used in JDBC.
- Each `Repository` interface (e.g., `UserRepository`) abstracts the complex connection logic, providing standard methods for CRUD (Create, Read, Update, Delete) operations.
- The use of **MongoDB** allows for a flexible schema, ideal for chat messages and diverse user metadata.

### 3. Layered Design Pattern
The project strictly follows the **Controller-Service-Repository-Entity** pattern:
- **Entities**: Represent the database schema (The "Model").
- **Repositories**: Handle data access logic (Modern JDBC implementation).
- **Services**: Contain the business logic and session management.
- **Controllers**: Expose API endpoints and WebSocket mappings.

---

## File-by-File Detailed Breakdown

### Backend (Java / Spring Boot)

#### 📂 `config` (System Configuration)
- **`WebSocketConfig.java`**: Configures the **Socket** broker. It enables real-time messaging and defines the `/ws` endpoint for client connections.
- **`SecurityConfig.java`**: Implements **Spring Security**. It manages authentication (Login/Logout) and role-based access control (USER vs. ADMIN). It ensures passwords are encrypted.
- **`WebConfig.java`**: Handles Cross-Origin Resource Sharing (CORS) and static resource mapping.
- **`DataInitializer.java`**: Pre-fills the database with default admin accounts and initial chat rooms upon startup.

#### 📂 `entity` (Data Models / POJOs)
- **`User.java`**: Stores user credentials, roles, and profile information.
- **`Message.java`**: Represents a single chat message, including sender, content, timestamp, and room ID.
- **`ChatRoom.java`**: Defines a chat room entity with accessibility settings (public/private).
- **`JoinRequest.java`**: Manages the approval flow for rooms requiring permission.

#### 📂 `repository` (Persistence Layer)
*These interfaces extend `MongoRepository`, providing a high-level abstraction over traditional JDBC calls.*
- **`UserRepository.java`**: Database operations for user accounts.
- **`MessageRepository.java`**: Handles saving and retrieving chat history.
- **`ChatRoomRepository.java`**: Manages room creation and lookups.
- **`JoinRequestRepository.java`**: Persists join applications for moderation.

#### 📂 `service` (Business Logic)
- **`UserService.java`**: Handles registration, authentication logic, and user-related operations.
- **`ChatService.java`**: Manages the core chat logic, including message broadcasting and history retrieval.

#### 📂 `controller` (API & Socket Endpoints)
- **`AuthController.java`**: Manages the login/registration lifecycle.
- **`ChatController.java`**: The **WebSocket Handler**. It manages `/chat/sendMessage` and `/chat/addUser` socket events.
- **`UserController.java`**: Handles dashboard data and room join requests.
- **`AdminController.java`**: Provide tools for administrators to manage rooms and users.
- **`FileUploadController.java`**: Manages file uploads (like avatars or attachments).

#### 📂 `dto` (Data Transfer Objects)
- **`UserDTO.java`**, **`ChatRoomDTO.java`**, etc.: These objects are used to send specific data to the frontend without exposing the internal database structure, improving security and performance.

### Frontend (User Interface)
- **`index.html`**: The entry point (Login/Register).
- **`user_dashboard.html`**: The main interface for users to browse rooms and chat.
- **`admin_dashboard.html`**: Management console for administrative tasks.
- **`style.css`**: Defines the "Futuristic" visual identity using modern CSS variables and dark-mode aesthetics.
- **`main.js`**: The heart of the client-side logic. It uses **SockJS** and **stomp.js** to connect to the backend sockets and update the UI dynamically without page reloads.

---

## Conclusion
This project demonstrates a mastery of **Java Advanced** concepts:
1.  **Multi-threaded communication** via WebSockets.
2.  **Robust Data Persistence** using the Repository pattern.
3.  **Secure Authentication** using enterprise-grade security filters.
4.  **Full-stack Integration** by bridging a Spring Boot backend with a reactive JavaScript frontend.
