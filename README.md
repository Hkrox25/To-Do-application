# To-Do Application

A Spring Boot and Thymeleaf todo application with PostgreSQL persistence, task priorities, categories, completion tracking, editing, soft deletion, undo support, validation, toast notifications, and an animated WebGL background.

## Features

- Create tasks with a title, priority, and category
- Validate task input with Jakarta Bean Validation
- Edit existing tasks
- Mark tasks as complete or active
- Separate active and completed task sections
- Display overall completion progress
- Show task metadata:
  - Priority
  - Category
  - Creation time
- Soft-delete tasks and restore them with **Undo**
- Custom delete-confirmation modal
- Success and error toast notifications
- Responsive dark UI built with Tailwind CSS
- Animated WebGL background
- PostgreSQL persistence through Spring Data JPA

## Technology stack

- Java 17
- Spring Boot 4.1.1
- Spring MVC
- Spring Data JPA
- Thymeleaf
- PostgreSQL
- Lombok
- Tailwind CSS CDN
- WebGL
- Maven Wrapper

## Project structure

```text
src/
├── main/
│   ├── java/com/hkrox/todoproj/
│   │   ├── controller/
│   │   │   └── TaskController.java
│   │   ├── dto/
│   │   │   └── TaskForm.java
│   │   ├── models/
│   │   │   ├── Priority.java
│   │   │   └── Task.java
│   │   ├── repository/
│   │   │   └── TaskRepository.java
│   │   └── services/
│   │       ├── TaskNotFoundException.java
│   │       └── TaskService.java
│   └── resources/
│       ├── templates/
│       │   ├── tasks.html
│       │   └── edit-task.html
│       └── application.properties
└── test/
    └── java/com/hkrox/todoproj/
        └── ToDoProjApplicationTests.java
```

## Prerequisites

Install the following before running the application:

- Java 17 or newer
- PostgreSQL
- Git

Verify Java and PostgreSQL are available:

```bash
java -version
psql --version
```

## Database setup

Create a PostgreSQL database/schema for the application. The default configuration expects:

```text
Database: postgres
Schema: todo-app
Username: postgres
```

Configure the database password locally. Do not commit database passwords to GitHub.

The recommended production-style configuration uses environment variables:

```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

For local development, set these variables in your shell before starting the application.

### PowerShell

```powershell
$env:DATABASE_URL='jdbc:postgresql://localhost:5432/postgres?currentSchema="todo-app"'
$env:DB_USERNAME='postgres'
$env:DB_PASSWORD='your-local-password'
```

### Windows Command Prompt

```cmd
set DATABASE_URL=jdbc:postgresql://localhost:5432/postgres?currentSchema="todo-app"
set DB_USERNAME=postgres
set DB_PASSWORD=your-local-password
```

## Run locally

From the project root, run:

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux or macOS

```bash
./mvnw spring-boot:run
```

Open the application at:

```text
http://localhost:8080/tasks
```

## Run tests

### Windows

```powershell
.\mvnw.cmd test
```

### Linux or macOS

```bash
./mvnw test
```

## Main routes

| Method | Route | Purpose |
|---|---|---|
| `GET` | `/tasks` | Display tasks |
| `POST` | `/tasks` | Create a task |
| `GET` | `/tasks/{id}/edit` | Display the edit form |
| `POST` | `/tasks/{id}/edit` | Update a task |
| `POST` | `/tasks/{id}/toggle` | Toggle completion |
| `POST` | `/tasks/{id}/delete` | Soft-delete a task |
| `POST` | `/tasks/{id}/restore` | Restore a deleted task |

## Task defaults

New tasks receive these defaults when values are not supplied:

```text
Priority: MEDIUM
Category: General
Created time: current date and time
Completed: false
```

The entity lifecycle callbacks preserve an existing `createdAt` value and apply defaults during persistence.

## Deployment notes

This is a server-rendered Spring Boot application and should be deployed to a Java-compatible platform such as Render, Railway, Fly.io, AWS, Azure, or Google Cloud.

Vercel is not a direct hosting target for the Spring Boot server. If using Vercel, host the frontend separately and deploy this Spring Boot application and PostgreSQL database elsewhere.

Before deployment:

1. Use a managed PostgreSQL database.
2. Set `DATABASE_URL`, `DB_USERNAME`, and `DB_PASSWORD` as platform secrets.
3. Never commit credentials or local configuration files.
4. Use database migrations instead of relying on `ddl-auto=update`.
5. Add authentication before allowing multiple users to access the application.

## Current limitations

- Tasks are not yet associated with authenticated users.
- The current test suite contains only a Spring context-load test.
- Search, filtering, sorting, due dates, reminders, and pagination are not yet implemented.
- Production authentication and authorization still need to be added.

## Future improvements

- User registration and login with Spring Security
- User-owned tasks and authorization checks
- Due dates and reminders
- Search, filtering, and sorting
- Tags, subtasks, and task descriptions
- Pagination for large task lists
- Flyway or Liquibase database migrations
- REST API endpoints
- Expanded controller, service, repository, and browser tests
- Reduced-motion support for the WebGL background
