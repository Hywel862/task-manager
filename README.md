Here’s a README file describing your project and task in a clear and professional way:

---

## 📋 Task Management API

This project is a Spring Boot REST API for managing tasks. It includes endpoints for creating, retrieving, updating, deleting, and searching for tasks. The API also includes validation, exception handling, and unit testing using JUnit and MockMvc.

### ✅ Objectives

The goal of this task was to build a full-stack-ready backend application with:

* A RESTful interface for managing `Task` entities.
* Spring Boot setup with appropriate controller, service, repository, and model layers.
* Custom exception handling using `@ControllerAdvice`.
* Basic input validation using Jakarta Bean Validation (`@NotBlank`, etc.).
* Unit tests using JUnit, Mockito, and Spring’s testing support.
* Swagger integration for API documentation (if desired/extended).
* (Optional) Setup for a frontend (HTML/JS) or future API consumers.

---

### 🧱 Project Structure

* `models/` – Java classes for Task and SearchRequest.
* `controllers/` – REST API endpoints (`TaskController`).
* `services/` – Business logic (`TaskService`).
* `repository/` – Spring Data JPA interface (`TaskRepository`).
* `exceptions/` – Custom exceptions and global handler.
* `helpers/` – Utility classes for tests (e.g., TaskHelper).
* `test/` – Unit tests for the controller using `MockMvc`.

---

### 📌 Functionality

* **POST /api/tasks** – Create a task
* **GET /api/tasks** – Retrieve all tasks
* **GET /api/tasks/{id}** – Retrieve a task by ID
* **PUT /api/tasks/update/{id}** – Update task by ID
* **DELETE /api/tasks/remove/{id}** – Delete task by ID
* **GET /api/tasks/search** – Search tasks by phrase (in title)

---

### 🧪 Testing

* Unit tests are written for all main controller methods using:

    * `@SpringBootTest`
    * `@AutoConfigureMockMvc`
    * `@MockBean` for mocking the repository
* Tests include:

    * Happy path (e.g., valid task creation)
    * Error cases (e.g., invalid ID, missing data)
    * Validation and exception handling tests

---

### ⚙ Technologies Used

* Java 17+
* Spring Boot
* Spring Data JPA
* H2 in-memory database
* Lombok
* Jakarta Bean Validation
* JUnit 5 + Mockito
* Jackson for JSON (with `JavaTimeModule`)
* Swagger (optional)
* Maven for dependency management

---

### 🚀 How to Run

1. Clone the repo
2. Run with your IDE or `mvn spring-boot:run`
3. Visit `http://localhost:8080/api/tasks` or run tests via your IDE or `mvn test`

---

Let me know if you'd like a version with Swagger UI instructions or frontend integration.
