# 🌌 Futuristic Chat: Enterprise-Grade Real-Time System

Welcome to the **Futuristic Chatroom System**, a robust, full-stack application engineered for secure, real-time communication. This project demonstrates advanced Java concepts, including **Full-Duplex WebSockets**, **NoSQL Persistence**, and **Enterprise Security Architectures**.

---

## 🛡️ Security Suite (The "Secured" Architecture)
This application is designed with security as a first-class citizen. Every layer is protected using industry-standard protocols:

*   **🔒 Identity Protection**: All user passwords undergo **one-way cryptographic hashing** using the **BCrypt algorithm**. No plain-text passwords ever touch the database.
*   **🛂 Role-Based Access Control (RBAC)**: The system strictly segregates permissions between `ADMIN` and `USER` roles. Unauthorized cross-access is prevented at the kernel level.
*   **🍪 Session Integrity**: State is managed via secure, server-side **HTTP Sessions**. Secure `JSESSIONID` cookies prevent session hijacking.
*   **🛰️ Encapsulated WebSockets**: While real-time communication is open, access to specific room "Topics" is moderated. Users cannot broadcast to rooms without prior **Admin Approval**.
*   **🛡️ Spring Security Filter Chain**: Every HTTP request is intercepted and validated through a complex security filter chain, protecting against common vulnerabilities.

---

## 🚀 Visionary Features
- **⚡ Zero-Latency Messaging**: Real-time message broadcasting powered by **STOMP over WebSockets**.
- **📋 Management Console**: A dedicated Admin interface for real-time moderation and room management.
- **📁 Multimedia Ready**: Intelligent file handling and avatar management systems.
- **🎨 Futuristic UI/UX**: A dark-themed, reactive interface built for high engagement and modern aesthetics.
- **💾 Eternal Persistence**: Full chat history is preserved using **MongoDB**, ensuring zero data loss across restarts.

---

## 🏗️ Technical Architecture
The system follows a high-performance **Layered Design Pattern**:

1.  **Presentation Layer**: Reactive HTML5 & JavaScript (STOMP.js).
2.  **Controller Layer**: Spring Web & Message Controllers (The Bridge).
3.  **Service Layer**: Encapsulated Business Logic & Transaction Management.
4.  **Persistence Layer**: Spring Data MongoDB (High-Performance NoSQL).

---

## 🛠️ Installation & Deployment
### 1. Prerequisites
- **Java 17+**
- **Maven 3.8+**
- **MongoDB** (Running on default port `27017`)

### 2. Startup
```bash
# Clone the repository
git clone [repository-url]

# Build the project
mvn clean install

# Launch the application
mvn spring-boot:run
```
Once started, access the portal at: `http://localhost:8080`

---

## 📚 Academic Resources
I have prepared specialized documentation for project defense and onboarding:
*   📖 **[Project Analysis](file:///Users/alaamouate/Desktop/CHATROOM/PROJECT_DESCRIPTION.md)**: Deep dive into the logic and files.
*   🎤 **[Presentation Guide](file:///Users/alaamouate/Desktop/CHATROOM/PRESENTATION_GUIDE.md)**: Script and demo flow for the defense.

---
*Created with ❤️ By MOUATE ALAA EDDINE.*
