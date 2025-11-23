# Garage Management System – API Microservice (GMS API)
**Final Project – Spring Advanced (October 2025)**  
SoftUni Java Web Development Track

<!-- =======================
       TECHNOLOGY BADGES
======================== -->
![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Web](https://img.shields.io/badge/Spring_Web-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Validation](https://img.shields.io/badge/Spring_Validation-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Data MongoDB](https://img.shields.io/badge/Spring_Data_MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Feign](https://img.shields.io/badge/Feign_Client-6DB33F?style=for-the-badge)
![GreenAPI](https://img.shields.io/badge/GreenAPI-1A8C3A?style=for-the-badge&logo=whatsapp&logoColor=white)
![iText](https://img.shields.io/badge/iText_PDF-000000?style=for-the-badge)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

---

# 1. Microservice Overview

The **GMS API Microservice** is an independent Spring Boot application responsible for:

- **Invoice generation (PDF)**
- **WhatsApp notifications** for completed repairs (GreenAPI)
- **Storing invoice & message logs in MongoDB**
- **Providing history endpoints** consumed by the main GMS application

It is fully separate from the Main app and communicates only through **REST + Feign Client**, satisfying all SoftUni criteria for multi-application architecture.

---

# 2. SoftUni Requirements Compliance

---

## 2.1 Technology Stack

### Backend
- Java 17
- Spring Boot 3.4.0
- Spring Web / REST
- Spring Validation
- Spring Data MongoDB
- iText for PDF
- Feign Client (consumed by main app)
- Dockerized MongoDB
- GreenAPI integration

### Storage
- MongoDB (dedicated, separate from main app)

### DevOps
- Docker Compose
- Git + GitHub

---

## 2.2 Architecture (Independent Application)

The microservice runs on its own port and is **completely isolated** from the main application.

Key responsibilities:
- Generates invoices called via **Feign** from the main app
- Saves invoice data into MongoDB
- Sends WhatsApp notifications
- Exposes REST endpoints under `/api/v1`
- Provides repair history to the main app

---

## 2.3 Domain Entities (2 entities)

### **InvoiceRecord**
Stores metadata for generated PDF invoices.

### **MessageLog**
Stores details for every WhatsApp message request.

**Both entities:**
- Use **UUID**
- Have **Repository + Service**
- Are part of valid functionalities

---

## 2.4 REST API Endpoints

Base path: `/api/v1`

### **POST Endpoints (modify state – required by SoftUni)**

| Endpoint                 | Description                                     |
|--------------------------|-------------------------------------------------|
| **POST /invoices**       | Generate invoice (PDF) and save invoice record  |
| **POST /complete-order** | Send WhatsApp notification and save message log |

### **GET Endpoints**

| Endpoint                              | Description                          |
|---------------------------------------|--------------------------------------|
| **GET /history**                      | Returns all invoice + message logs   |
| **GET /pdf/repair/{repairId}/latest** | Returns latest generated PDF invoice |

→ These endpoints are **directly consumed by the main application** using **FeignClient**.

---

## 2.5 Functionalities (Microservice)

### 1) Generate Invoice (PDF)
- Triggered from main app
- POST request with repair data
- Generates PDF file
- Stores `InvoiceRecord`
- Returns URL to download PDF

### 2) Send WhatsApp Notification
- Triggered when repair order is completed
- Uses **GreenAPI**
- Saves `MessageLog`

### 3) Provide Repair History
- Returns all logs for admin panel in main app

All three satisfy the requirement for **2+ valid functionalities**.

---

## 2.6 Validation & Error Handling

- DTO validation using Jakarta annotations
- Global `@ControllerAdvice`
- Custom exceptions
- No whitelabel pages
- Proper JSON error responses

---

## 2.7 Database Requirements

- MongoDB database (separate from main app)
- UUID IDs
- Entities stored in collections
- Indexed fields (repairId)
- Fully Dockerized

---

## 2.8 Testing

The microservice includes:

- **Unit tests** (service layer)
- **Integration test** (with embedded MongoDB or Testcontainers)
- **API tests** (MockMvc controllers)

Coverage target: **80%+**, as required.

---

## 2.9 Logging

- Log statements added for:
    - Invoice creation
    - Message sending
    - External API responses
    - Error handling
- Follows main project’s logging conventions

---

## 2.10 Code Quality & Style

- No dead code
- No unused imports
- Layered architecture (controller → service → repository)
- Thin controllers
- SOLID applied
- Clear naming conventions

---

# 3. Bonus Features (Microservice)

| Bonus Feature               | Points | Status |
|-----------------------------|--------|--------|
| MongoDB (non-relational DB) | +4     | ✔      |
| PDF export (iText)          | +2     | ✔      |
| Dockerized setup            | +1     | ✔      |
| 3rd-Party API (GreenAPI)    | +2     | ✔      |

---

# 4. How to Run the Microservice

### 4.1 Clone the Repository
```bash
  git clone https://github.com/valentinyanev92/garage-management-system-api
```

---

### 4.2 Start MongoDB

MongoDB is included in the **Docker Compose configuration of the Main application**, so it can be started together with the entire infrastructure:

```bash
  docker compose up -d
````

Alternatively, if you want to run only MongoDB (without starting the rest of the Main app services), you can start it directly from this microservice project:
```bash
    docker compose up -d mongodb
```
This ensures the microservice has access to a running MongoDB instance even when the Main application is not active.

---

### Default API URL
```bash
    http://localhost:8081/api/v1
```

---

### 5. Author

Valentin Rumenov Yanev

SoftUni Java Developer Track (2024–2025)