# PrioriTaskAPI

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Setting Up the Database](#setting-up-the-database)
    - [Running the Application](#running-the-application)
- [Authentication and Authorization](#authentication-and-authorization)
- [Scheduler with Quartz](#scheduler-with-quartz)
- [Email Sending](#email-sending)
- [Improvements](#improvements)

## Introduction
PrioriTaskAPI is a task management API designed to help users manage their tasks effectively. This API lays the foundation for a powerful task management platform, offering features such as user authentication, task reminders, and email notifications.

## Features
- **User Authentication and Authorization:** Implement secure user authentication and authorization using Spring Security and JWT (JSON Web Tokens).

- **Database:** Utilize PostgreSQL as the database of choice. A Docker Compose file is provided for convenient local database setup.

- **Scheduler:** Implement a task scheduler using Quartz, ensuring timely task reminders and notifications. Flyway automates the integration of Quartz with PostgreSQL.

- **Email Sending:** Incorporate Apache Commons Email for efficient email sending functionality. Thymeleaf is employed to generate HTML email templates for task reminders and notifications.

## Technologies Used
- Spring Boot
- Kotlin
- PostgreSQL
- Docker
- Quartz Scheduler
- Flyway
- Apache Commons Email
- Thymeleaf
- JSON Web Tokens (JWT)


## Getting Started

### Prerequisites
Before running the application, make sure you have the following prerequisites installed:

- [Java](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

### Setting Up the Database
To set up the PostgreSQL database locally using Docker Compose, execute the following command within the project's root directory:

```bash
docker compose up
```
This command will start a PostgreSQL container with the required configurations.

### Running the Application
1. Clone this repository to your local machine:
2. In terminal navigate to the project directory
3. Run the Spring Boot application:

```bash
./gradlew bootRun
```
This action initiates the server at http://localhost:8081.

## Authentication and Authorization
User authentication and authorization are implemented using Spring Security and JSON Web Tokens (JWT). Ensure that you include the JWT token in your requests to access protected endpoints.

## Scheduler with Quartz
Leveraging Quartz scheduler, PrioriTask ensures that task reminders and notifications are dispatched punctually. The integration of Quartz with PostgreSQL is automated through Flyway, guaranteeing smooth execution.

## Email Sending
For efficient email communication, the application incorporates Apache Commons Email. Thymeleaf is employed to create dynamic HTML email templates for task reminders and notifications.

## Improvements
Future enhancements are planned, including:

- Implementation of comprehensive unit tests to ensure the reliability and stability of the API.
- Introduction of additional task-related features, such as task categories, priorities, and attachments.
- Enhanced error handling with user-friendly error messages for a seamless user experience.
- User profile management and customization options.
- Integration with a frontend application to provide users with a complete task management solution.
