# Admin Portal – Customer User Management System

An Admin Portal built with **Angular 19** (frontend) and **Java Spring Boot** (backend) for managing insurance customer data and user administration.

## Features

### Customer Search
- Search by SSN & Birth Year, Tax ID, Policy Number, Name & Address, Username, or Email
- Results display Policy Owner Name, Policy Number, Status, Role, Address, Master ID, and Username

### Customer Details View
- **Personal Information**: Name, Address, City, State, ZIP, Country, DOB, SSN (masked), Tax ID (masked)
- **User Information**: Username, Email, Mobile, Status, Disable Reason, Creation Date, Last Login
- **Policy Information**: Policy Number, Status, Role, Type, Product, Issue Date, Portal Visibility

### User Registration
- Supports Individual, Trust, and Corporate customer types
- Mandatory fields: SSN/ITIN, Year of Birth, Phone, Email, Username
- Auto-generates unique Master ID

### Admin Actions
- Update user profile (Phone, Email, Address)
- Enable / Disable users (with reason)
- Delete users (soft delete)

### Security & Compliance
- SSN and Tax ID masking in API responses
- Full audit trail for all admin actions

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend | Angular 19, TypeScript, SCSS |
| Backend | Java 17, Spring Boot 3.5, Spring Data JPA |
| Database | H2 (in-memory, for development) |
| Build | Maven (backend), Angular CLI (frontend) |

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- npm 9+

### Backend Setup
```bash
cd backend
./mvnw spring-boot:run
```
The API server starts at `http://localhost:8080`.

### Frontend Setup
```bash
cd frontend
npm install
ng serve
```
The Angular app starts at `http://localhost:4200`.

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/customers/search` | Search customers |
| GET | `/api/customers/{masterId}` | Get customer details |
| POST | `/api/customers/register` | Register new user |
| PUT | `/api/customers/{masterId}/profile` | Update profile |
| PUT | `/api/customers/{masterId}/enable` | Enable user |
| PUT | `/api/customers/{masterId}/disable` | Disable user |
| DELETE | `/api/customers/{masterId}` | Delete user |
| GET | `/api/audit` | Get all audit logs |
| GET | `/api/audit/{masterId}` | Get audit logs for customer |

## Seed Data

The application comes pre-loaded with 6 sample customers and 7 policies for development/testing.
