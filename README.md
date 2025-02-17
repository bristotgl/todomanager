# Todo Manager

This is a **REST API** for a **todo manager**, built with **Java** and **Spring Boot**. It currently features a **CRUD** (Create, Read, Update, Delete) for managing **Customers**, along with unit tests for both controllers and services. The API also supports **internationalization** in two languages: English and Portuguese.

The application uses **PostgreSQL** as its database, running in a **Docker** container, and **Flyway** for database versioning and migration.

## Features ✨ (so far)
- Customer Management: Create, read, update, and delete customers.
- Unit Testing: Comprehensive unit tests for controllers and services.
- Internationalization: Support for English and Portuguese.
- Database Versioning: Flyway for managing database migrations.
- Docker Integration: PostgreSQL database runs in a Docker container for easy setup and portability.

## Challenges ⛰️
So far, the biggest challenge has been adhering to **TDD** (Test Driven Development) principles and writing good, comprehensive unit tests.

## TODO ✏️
Here are the planned improvements and features for the project:
- Write error scenarios for Customer's unit tests
- Implement CRUD for Tasks
- Write unit tests for Task's service and controller
- Configure Spring Security to enable OAuth2 authorization

## Tech Stack 💻
- Java 21
- Spring Boot
- Hibernate
- Spring Data JPA
- Flyway
- Project Lombok
- Spring Web
- Postgres
- Docker
