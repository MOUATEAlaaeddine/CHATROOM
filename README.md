<<<<<<< HEAD
# CHATROOM
CHATROOM 
=======
# CloudChat (Academic V3)

A robust, secure, and presentation-ready real-time chat application.

## 🌟 Key Features (The "Why")
- **Persistent Sessions**: Unlike previous versions, refreshing the page does NOT log you out. We use `HttpSession` to keep you authenticated.
- **Role-Based Access**:
    - **Admin**: Has a dedicated dashboard to manage rooms and approve/reject users.
    - **Student**: Has a separate dashboard to browse rooms and request access.
- **Security**: Passwords are hashed using **BCrypt** before storage.
- **Startup Safety**: A `DataInitializer` ensures the Admin account always exists on startup.

## 🚀 How to Run
1.  **MongoDB**: Ensure it is running on default port `27017`.
2.  **Run App**: `mvn spring-boot:run`
3.  **Browse**: Open `http://localhost:8080`.

## 🔑 Credentials
- **Student**:
    - Register a new account on the login page.

## 📂 Architecture Overview
- **Controllers**:
    - `AuthController`: Handles Login/Logout and Session Cookies.
    - `AdminController`: Protected endpoints for specific admin actions.
    - `UserController`: Protected endpoints for student actions.
- **Frontend**:
    - Three distinct HTML files (`index`, `admin_dashboard`, `user_dashboard`) ensure clear separation of concerns.

## 🎓 Defense Tips (For Professor)
- **"How is it secure?"** -> "We rely on Server-Side Sessions (`JSESSIONID` cookie) effectively. Passwords are salted and hashed with BCrypt. API endpoints check the user's Role from the session before executing logic."
- **"Why separate dashboards?"** -> "To enforce strict separation of duties. Admins and Students have completely different workflows."
>>>>>>> 2b87bf2 (chatroom V1)
