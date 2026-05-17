# 🎓 SGE - Student Grade Management System

SGE is a secure backend system built with Spring Boot for managing students, academic fields, modules, grades, bulletins, and authentication using JWT and role-based access control.

---

## 📌 Project Overview

SGE allows managing:

- Students
- Academic Fields (Filieres)
- Modules
- Grades (Notes)
- Bulletins
- Dashboard statistics
- Authentication (JWT)

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT (JJWT)
- MySQL
- Maven

---

## 🔐 Authentication

### Login
POST `/auth/login`

### Register
POST `/auth/register`

### Token Usage
Authorization header:

---

## 👥 Roles

- ADMIN → full access
- USER → read-only access

---

## 🔑 Default Admin

Username: admin  
Password: admin123  
Role: ADMIN  

---

## 📡 API Endpoints

### Auth
- POST /auth/login
- POST /auth/register

### Students
- GET /etudiants
- POST /etudiants
- PUT /etudiants/{id}
- DELETE /etudiants/{id}

### Filieres
- GET /filieres
- POST /filieres
- PUT /filieres/{id}
- DELETE /filieres/{id}

### Modules
- GET /modules
- POST /modules
- DELETE /modules/{id}

### Notes
- GET /notes
- POST /notes
- PUT /notes/{id}
- DELETE /notes/{id}

---

## 🏗 Architecture

### Layered Architecture
```mermaid
graph TD
Client --> Controller
Controller --> Security
Security --> Service
Service --> Repository
Repository --> Database
