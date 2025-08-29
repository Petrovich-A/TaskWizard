![](src/main/resources/static/logo.png)
# TaskWizard
This test assignment for potential employers demonstrates my practical skills as a Java developer. Built with Spring Boot, it focuses on task management and RESTful APIs. The project meets requirements by demonstrating clean, well-structured code, OOP design, test coverage, and proper exception handling.
## Table of Contents
- [About the Project](#about-the-project)
- [Tech Stack](#tech-stack)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
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

- **Java 17** ☕
- **Spring Boot**  
- **Spring Security (JWT Authentication)** 🔐  
- **PostgreSQL** 🐘  
- **Docker & Docker Compose** 🐳  
- **Gradle** 📦
- **OpenAPI & Swagger UI** 📄  
- **JUnit & Mockito (for testing)** 🧪  

---

## Requirements

Before starting, ensure you have installed:
- **Docker** and **Docker Compose** - [Download here](https://www.docker.com/products/docker-desktop/)

> Note: No need to install Java or Gradle separately - everything runs in containers.

## Getting Started

### Step 1: Create Environment Configuration

Create a file named `.env` in the project root directory with your configuration:

```env
DATASOURCE_USERNAME=your_username_here
DATASOURCE_PASSWORD=your_secure_password_here
HIBERNATE_DDL_AUTO=validate
LIQUIBASE_ENABLED=true
JWT_SECRET_KEY=your-random-secret-key-here
```

JWT_SECRET_KEY: Generate with:

```bash
[Convert]::ToBase64String((1..32 | ForEach-Object {Get-Random -Maximum 256}))
```

Use a strong, unique value. Don't expose it.

### Step 2: Start the Application

Run the following command to start all services:

```bash
docker compose up -d
```

### Step 3: Verify Application Status

Wait a moment for containers to initialize, then check if the application is running:

```bash
docker compose ps
```

### Health Check

```bash
curl http://localhost:8080/health
```

The application will be available at: **http://localhost:8080**

## 📖 API Documentation

Access the interactive API documentation:
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI specification:** http://localhost:8080/v3/api-docs

## 🔐 Key Endpoints

### Authentication
- `POST /api/v1/auth/sign-up` - User registration
- `POST /api/v1/auth/sign-in` - User login

### Tasks (Requires Authentication)
- `GET /api/v1/task/tasks` - Get paginated task list
- `GET /api/v1/task/sort` - Get sorted task list
- `GET /api/v1/task/{id}` - Get task by ID
- `POST /api/v1/task` - Create new task
- `PUT /api/v1/task/{id}` - Update task
- `DELETE /api/v1/task/{id}` - Delete task

## 🐛 Troubleshooting

### Check Application Logs
```bash
docker compose logs app -f
```

### Check Database Status
```bash
docker compose logs postgres
```

### Restart Specific Service
```bash
docker compose restart app
docker compose restart postgres
```

### Complete Reset and Clean Start
```bash
docker compose down -v
docker compose up -d --build
```

## 📊 Service Ports

- **Application API:** 8080 (http://localhost:8080)
- **PostgreSQL Database:** 5432

## 🔧 Development Mode

To run the application outside Docker for development:
```bash
# Requires JDK 17+ and Gradle installed
./gradlew bootRun
```

> Note: In development mode, ensure you have PostgreSQL running locally and update connection settings accordingly.

## Contact

👤 **Petrovich Alexandr**
Petrovich Alexandr - [@Petrovich Alexandr](https://www.linkedin.com/in/alexandr-petrovich/)

📩 a.piatrovich@gmail.com
