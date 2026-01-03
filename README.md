# 🧠 AI Help Desk System  
Spring Boot | MySQL | Google Gemini AI

The AI Help Desk System is a backend application that uses **Google Gemini AI**
to automatically respond to user queries, generate support tickets, and store
complete conversation history in a database.

This project follows a clean **MVC architecture** and is designed to simulate
a real-world help desk or customer support system.

---

## 🚀 Features

- 🤖 AI-powered responses using Google Gemini
- 🎫 Automatic ticket creation
- 🗂️ Auto categorization of queries (AUTH, PAYMENT, TECHNICAL, GENERAL)
- ⚡ Priority detection (HIGH, MEDIUM, LOW)
- 💬 Full conversation history (User + AI messages)
- 📴 Offline fallback support when AI is unavailable
- 🗄️ MySQL database integration
- 🧱 Clean MVC architecture
- 🔌 RESTful APIs (Postman / Frontend ready)

---

## 🛠️ Tech Stack

- Java 22 
- Spring Boot 3.x  
- Spring Web  
- Spring Data JPA  
- MySQL  
- Google Gemini API  
- Maven  

---

## 📂 Project Structure (MVC)

src/main/java/com/mahaveer/myHelpDesk
│
├── controller
│ ├── HelpDeskController.java
│ └── ConversationController.java
│
├── service
│ ├── TicketService.java
│ ├── GeminiService.java
│ └── impl
│ ├── TicketServiceImpl.java
│ └── GeminiServiceImpl.java
│
├── repository
│ ├── TicketRepository.java
│ └── ConversationRepository.java
│
├── model
│ ├── Ticket.java
│ └── Conversation.java
│
├── dto
│ └── TicketRequestDto.java
│
├── util
│ └── CategoryPriorityUtil.java
│
└── MyHelpDeskApplication.java

yaml
Copy code

---

## ⚙️ Configuration

### `application.yml`

```yaml
server:
  port: 8080

spring:
  application:
    name: myHelpDesk

  datasource:
    url: jdbc:mysql://localhost:3306/helpdesk_db
    username: root
    password: root

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  ai:
    google:
      genai:
        api-key: ${GOOGLE_KEY}
        project-id: ${GOOGLE_PROJECT_ID}
        location: us-central1
🔑 Environment Variables
Windows
cmd
Copy code
setx GOOGLE_KEY your_gemini_api_key
setx GOOGLE_PROJECT_ID your_google_project_id
Linux / macOS
bash
Copy code
export GOOGLE_KEY=your_gemini_api_key
export GOOGLE_PROJECT_ID=your_google_project_id
▶️ Run the Application
bash
Copy code
mvn clean spring-boot:run
The application will start at:

arduino
Copy code
http://localhost:8080
🔌 API Endpoints
🎫 Create Ticket and Ask AI
bash
Copy code
POST /api/helpdesk/ask
Request Body

json
Copy code
{
  "userName": "Mahesh",
  "query": "login issue urgent"
}
💬 Get Conversation History
bash
Copy code
GET /api/conversation/{ticketId}
🗄️ Database Tables
Ticket Table
id

user_name

user_query

category

priority

ai_response

offline_mode

created_at

Conversation Table
id

ticket_id

message

sender (USER / AI)

created_at

🧠 Application Flow

User Request
   ↓
Controller
   ↓
Service Layer
   ↓
Google Gemini AI
   ↓
Ticket Saved
   ↓
Conversation Saved (User + AI)
   ↓
Response Returned
📌 Future Enhancements
Prompt template (.st) for controlled AI responses

Multi-message chat per ticket

Admin dashboard

JWT-based authentication

Retry queue for offline AI requests

👨‍💻 Author
Jitendra Kumar
Java | Spring Boot | Backend Developer

⭐ Support
If you find this project useful, please give it a ⭐ on GitHub.
