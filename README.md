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

## 2.3 Domain Entities (3 entities)

### **InvoiceLog**
Stores metadata for generated PDF invoices:
- `id` — String (Mongo ObjectId stored as String)
- `repairId` — UUID reference to the main application repair
- Timestamps: createdAt, completedAt, generatedAt
- Customer & mechanic data
- Car details
- Price breakdown (partsTotal, serviceFee, totalPrice)
- List of `UsedPartInfo`
- PDF document stored as `byte[] document`

### **MessageLog**
Stores details for every WhatsApp message request:
- `id` — String
- recipient
- messageContent
- channel (e.g. WhatsApp)
- status / response / error
- timestamp

### **RepairLog**
Stores repair event logs:
- `id` — String
- `repairId` — String reference to repair
- status
- message
- receivedAt

**All three entities:**
- Use **String-based IDs** (Mongo ObjectId serialized as String)
- Have **Repository + Service** layers
- Are part of valid SoftUni functionalities


---

## 2.4 REST API Endpoints

Base path: `/api/v1`

### **POST Endpoints (modify state – required by SoftUni)**

### **POST Endpoints**
| Endpoint                 | Description                                |
|--------------------------|--------------------------------------------|
| **POST /invoices**       | Generate invoice (PDF) and save InvoiceLog |
| **POST /complete-order** | Send WhatsApp notification (MessageLog)    |

### **GET Endpoints**
| Endpoint                              | Description                                 |
|---------------------------------------|---------------------------------------------|
| **GET /history**                      | Returns all InvoiceLog + MessageLog records |
| **GET /pdf/repair/{repairId}/latest** | Returns latest InvoiceLog PDF               |


→ These endpoints are **directly consumed by the main application** using **FeignClient**.

---

## 2.5 Functionalities (Microservice)

### 1) Generate Invoice (PDF)
- Triggered from the main app
- Creates and stores an InvoiceLog
- Stores PDF binary into MongoDB

### 2) Send WhatsApp Notification
- Uses GreenAPI
- Stores a MessageLog

### 3) Provide Repair History
- Returns InvoiceLog + RepairLog + MessageLog entries
- 
### 4) Create RepairLog entries  
   Every major repair event from the main application is recorded in MongoDB for history and auditing.

All satisfy the requirement for **2+ valid functionalities**.

---

## 2.6 Validation & Error Handling

- DTO validation using Jakarta annotations
- Global `@ControllerAdvice`
- Custom exceptions
- No whitelabel pages
- Proper JSON error responses

---

## 2.7 Database Requirements

- MongoDB database (fully separated from the main application)
- String-based IDs (Mongo ObjectId stored as String)
- UUID values used only when referencing MySQL data (`repairId` in InvoiceLog)
- Entities stored in dedicated collections (`invoice_logs`, `repair_logs`)
- Indexed fields (repairId)
- Fully Dockerized

## Collections:
- `invoice_logs`
- `repair_logs`
- generic collection for `MessageLog` (auto-managed by Spring)


---

## 2.8 Testing

The microservice includes:

- **Unit tests** for the service layer
- **API tests (MockMvc)** for controller endpoints
- **Integration-style tests** using Spring Boot Test with an active MongoDB instance (via Docker)
- Verification of invoice generation, message logging, and history endpoints

Overall project test coverage meets the SoftUni requirement of **80%+** combined for main app + microservice.

---

## 2.9 Logging

Log statements added for:
- InvoiceLog creation
- MessageLog sending
- RepairLog entries
- External API responses
- Error handling


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