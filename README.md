# Student Management API

A Spring Boot REST API for managing products (used in a software testing context).

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 4.1.2**
- **Spring Web MVC**
- **Spring Boot Validation**
- **Maven**

## 📦 Project Structure

```
src/
└── main/
│   └── java/com/example/studentmanagementapi/
│       ├── StudentManagementApiApplication.java  # Entry point
│       ├── Product.java                          # Model class
│       └── ProductController.java               # REST Controller
└── test/
    └── java/com/example/studentmanagementapi/
        └── StudentManagementApiApplicationTests.java
```

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3.6+

### Run the application

```bash
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`.

## 📡 API Endpoints

Base URL: `/api/products`

| Method | Endpoint              | Description             |
|--------|-----------------------|-------------------------|
| GET    | `/api/products`       | Get all products        |
| GET    | `/api/products/{id}`  | Get product by ID       |
| POST   | `/api/products`       | Add a new product       |
| PUT    | `/api/products/{id}`  | Update existing product |

### Sample Request – Add Product (POST)

```json
{
  "name": "USB Hub",
  "category": "Electronics",
  "price": 1299.00,
  "quantity": 20
}
```

### Sample Response

```json
{
  "id": 6,
  "name": "USB Hub",
  "category": "Electronics",
  "price": 1299.0,
  "quantity": 20
}
```

## ✅ Validation Rules

- `name` and `category` are **required** (non-blank)
- `price` and `quantity` **cannot be negative**
- Returns `400 Bad Request` on invalid input
- Returns `404 Not Found` for missing IDs

## 🧪 Running Tests

```bash
./mvnw test
```

## 📝 License

This project is for educational / software testing purposes.
