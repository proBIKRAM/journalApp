# JournalApp

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-green)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-brightgreen)
![Redis](https://img.shields.io/badge/Redis-Cloud-red)
![JWT](https://img.shields.io/badge/Auth-JWT-9C27B0)
![Swagger](https://img.shields.io/badge/API-Swagger-85EA2D)

**Backend for JournalApp** – a simple journal management system with user and admin roles.

## 🌐 Hosted Deployment

Live URL: [https://journalapp-vg41.onrender.com](https://journalapp-vg41.onrender.com)  

Health Check: [https://journalapp-vg41.onrender.com/public/health-check](https://journalapp-vg41.onrender.com/public/health-check)

## 📂 Project Repository

[https://github.com/proBIKRAM/journalApp](https://github.com/proBIKRAM/journalApp)

---

## 📋 Table of Contents
- [Tech Stack](#tech-stack)
- [Setup Instructions](#setup-instructions)
- [Docker Deployment](#docker-deployment)
- [Authentication](#authentication)
- [Testing & Coverage](#testing--coverage)
- [Deployment Notes](#deployment-notes)
- [Notes for Frontend Developers](#notes-for-frontend-developers)
- [Code Quality](#code-quality)
- [License](#license)

## 🛠️ Tech Stack

| Technology              | Version / Detail                          |
|-------------------------|-------------------------------------------|
| Java                    | 17                                        |
| Spring Boot             | 3.2.3                                     |
| Database                | MongoDB Atlas (Cloud)                     |
| Cache                   | Redis (Cloud)                             |
| Authentication          | JWT (jjwt 0.12.6)                         |
| API Docs                | SpringDoc OpenAPI (Swagger UI)            |
| Build Tool              | Maven                                     |
| Container               | Docker (multi-stage)                      |
| CI/CD & Quality         | GitHub Actions + SonarCloud + Qodana      |

## ⚙️ Setup Instructions

### 1. Clone & enter folder
```bash
git clone https://github.com/proBIKRAM/journalApp.git
cd journalApp
