![](src/main/resources/static/logo.png)
# TaskWizard
This test assignment for potential employers demonstrates my practical skills as a Java developer. Built with Spring Boot, it focuses on task management and RESTful APIs. The project meets requirements by demonstrating clean, well-structured code, OOP design, test coverage, and proper exception handling.
## Table of Contents
- [About the Project](#about-the-project)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Run Locally](#run-locally)
- [Contact](#contact)

---

## About the Project

This test assignment project for potential employers demonstrates my practical skills as a Java developer. It is a Java Spring Boot application focused on tasks management, highlighting my abilities in building RESTful APIs. The project meets the requirements by demonstrating clean, well-structured code, applying OOP principles in system design, including tests coverage and implementing proper exceptions handling.

The **Task Wizard** provides a secure and robust API for creating, editing, deleting, and viewing tasks. Each task contains a title, description, status (e.g., "pending", "in progress", "completed"), priority (e.g., "high", "medium", "low"), comments, as well as author and assignee information.

The system implements:

- **User authentication and authorization** using Spring Security with JWT tokens, ensuring secure access to the API.
- A **role-based access control** system with two roles: Administrator and User.
   - Administrators have full control over all tasks and comments, including creating, modifying, deleting tasks, assigning executors, and managing statuses and priorities.
   - Users can manage only their own tasks where they are assigned as executors, with permissions to update status and add comments.
- **Filtering and pagination** capabilities for retrieving tasks by author or assignee, improving API usability and performance.
- Comprehensive **input validation** and **error handling**, returning meaningful messages to clients.
- Full **API documentation** via OpenAPI and Swagger UI, facilitating easy exploration and integration.
- Docker Compose setup for seamless local development environment orchestration with PostgreSQL database.

This project showcases my ability to design and implement a real-world RESTful service with security, clean architecture, and developer-friendly features.

---

## Tech Stack

* Java 17+ ☕
* Spring Boot  
* Spring Security (JWT Authentication) 🔐  
* PostgreSQL (or MySQL) 🐘 / 🐬  
* Docker & Docker Compose 🐳  
* Maven / Gradle  
* OpenAPI & Swagger UI 📄  
* JUnit & Mockito (for testing) 🧪  

---

## Getting Started

### Prerequisites

- Java 17 or higher installed  
- Docker and Docker Compose installed  
- PostgreSQL or MySQL database (configured in `application.properties`)  
- Maven or Gradle (depending on project setup)  

### Setup

1. Clone the repository:

```bash
   git clone https://github.com/yourusername/task-management-system.git
   cd task-management-system
```

2. Configure your database connection in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskwizard
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
```

3. (Optional) Adjust JWT and security settings if needed.

---

## Run Locally

### Using Docker Compose (recommended for dev environment)

Start PostgreSQL and the application with Docker Compose:

```bash
docker-compose up --build
```

This will launch the database and the Spring Boot app.

---

### Running without Docker

1. Make sure your PostgreSQL/MySQL database is running locally and accessible.

2. Build the project:

```bash
./gradlew build
```

3. Run the application from IntelliJ IDEA or via command line:

```bash
java -jar target/task-management-system.jar
```

or run the main class directly from your IDE.

---

### Access Swagger UI

Once the app is running, open your browser and go to:

```
http://localhost:8080/swagger-ui.html
```

Here you can explore and test all API endpoints.

---

## Contact

👤 **Petrovich Alexandr**
Petrovich Alexandr - [@Petrovich Alexandr](https://www.linkedin.com/in/alexandr-petrovich/)

📩 a.piatrovich@gmail.com

---
🚀 Happy coding!
```