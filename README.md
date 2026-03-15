# JournalApp

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-green)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-brightgreen)
![Redis](https://img.shields.io/badge/Redis-Cloud-red)
![JWT](https://img.shields.io/badge/Auth-JWT-9C27B0)
![Swagger](https://img.shields.io/badge/API-Swagger-85EA2D)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)

**E2EE Journal App** – Full-featured backend with user/admin roles, JWT auth, MongoDB storage, Redis caching, email support, and scheduled tasks.

## 🌐 Live Deployment

- **App**: [https://journalapp-vg41.onrender.com](https://journalapp-vg41.onrender.com)
- **Health Check** (public): [https://journalapp-vg41.onrender.com/public/health-check](https://journalapp-vg41.onrender.com/public/health-check)
- **Swagger UI**: [https://journalapp-vg41.onrender.com/swagger-ui/index.html](https://journalapp-vg41.onrender.com/swagger-ui/index.html) or `/docs`
---
## 📂 Repository

[https://github.com/proBIKRAM/journalApp](https://github.com/proBIKRAM/journalApp)

---

## 📋 Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Docker Deployment](#docker-deployment)
- [Authentication](#authentication)
- [Public Endpoints](#public-endpoints)
- [Testing & Coverage](#testing--coverage)
- [Code Quality & CI/CD](#code-quality--cicd)
- [Notes for Frontend Developers](#notes-for-frontend-developers)
- [License](#license)

## ✨ Features
- User registration & login with JWT
- Role-based access control (USER / ADMIN)
- Journal CRUD operations (E2EE encrypted journals)
- Redis caching layer
- Email support (Spring Mail)
- Scheduled tasks
- Public health-check endpoint (permitted by Spring Security)
- Full OpenAPI/Swagger documentation
- Actuator monitoring
- Multi-stage Docker build

## 🛠️ Tech Stack

| Technology                  | Version / Detail                     |
|-----------------------------|--------------------------------------|
| Java                        | 17                                   |
| Spring Boot                 | 3.2.3                                |
| Database                    | MongoDB Atlas + Spring Data MongoDB  |
| Cache                       | Redis (Spring Data Redis)            |
| Authentication              | Spring Security + JJWT 0.12.6        |
| API Docs                    | SpringDoc OpenAPI 2.4.0 (Swagger UI) |
| Build Tool                  | Maven                                |
| Container                   | Docker (multi-stage)                 |
| Additional                  | Lombok, Actuator, Java Mail, JaCoCo  |
| CI/CD & Quality             | GitHub Actions + SonarCloud          |

## 📁 Project Structure
```plaintext
src/main/java/nec/bikram/journalApp/
├── config/          (SecurityConfig, SwaggerConfig etc.)
├── controller/      (all REST endpoints)
├── dto/             (request/response objects)
├── entity/          (MongoDB models)
├── enums/           (Role, etc.)
├── filter/          (JWT authentication filter)
├── repository/      (Mongo repositories)
├── service/         (business logic)
├── utils/           (JWT utils, encryption helpers)
├── cache/           (Redis cache)
├── scheduler/       (scheduled tasks)
├── api/response/    (custom API responses)
└── JournalApplication.java
```
## ⚙️ Setup Instructions

1. Clone the repo
```bash
git clone https://github.com/proBIKRAM/journalApp.git
cd journalApp
```
2. Create src/main/resources/application.properties (not committed) with:
```bash
propertiesspring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.e7ytsmk.mongodb.net/journaldb?retryWrites=true&w=majority
spring.redis.host=<redis-host>
spring.redis.password=<redis-password>
# Add any other mail / actuator props as needed
```
3. Build
```bash
mvn clean install
```
4. Run locally
```bash
mvn spring-boot:run
```
  → Open http://localhost:8080
  
## 🐳 Docker Deployment
```bash
docker build -t journalapp .
docker run -p 8080:8080 journalapp
```
## 🔑 Authentication
- JWT-based (jjwt 0.12.6)
- Roles: USER / ADMIN
- Public routes need no token
- Protected routes: Authorization: Bearer <token>
- Token generated via login endpoint
 
## 🌍 Public Endpoints

- /public/health-check (explicitly permitted in Spring Security config)
- /public/ signup & login
- Permitted journal views
- Swagger UI (/swagger-ui/index.html or /docs)

## 🧪 Testing & Coverage

- JaCoCo 0.8.10 fully configured (mvn verify generates reports)
- Run tests: mvn test
- Manual testing via Postman or Swagger
- Current coverage improves automatically with added tests

## ⚡ Code Quality & CI/CD

- GitHub Actions (build.yml)
- SonarCloud analysis
- JaCoCo reports attached to builds
- Qodana support ready
  
## 📖 Notes for Users

- Base URL: https://journalapp-vg41.onrender.com
- Public routes (including /public/health-check) need no header
- Protected routes require Authorization: Bearer <jwt>
- All schemas visible in Swagger UI
- Use /public/health-check to keep Render awake
  
## 📚 Citations / Acknowledgments
- [pom.xml](https://raw.githubusercontent.com/proBIKRAM/journalApp/main/pom.xml)  
- [Java source tree](https://github.com/proBIKRAM/journalApp/tree/main/src/main/java/nec/bikram/journalApp)  
- [Dockerfile](https://raw.githubusercontent.com/proBIKRAM/journalApp/main/Dockerfile)  
- [.github/workflows](https://github.com/proBIKRAM/journalApp/tree/main/.github/workflows)  
- [Live public health-check](https://journalapp-vg41.onrender.com/public/health-check)  
- [Full repository](https://github.com/proBIKRAM/journalApp)
- This project uses **Spring Boot**, **MongoDB**, and **Redis** — thanks to their excellent open-source communities.  
- Swagger UI is used for API documentation.  
- SonarCloud is used for code quality analysis.  
- Inspired by best practices from open-source Java backend projects.
