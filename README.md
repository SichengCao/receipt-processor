# Receipt Processor Challenge

This is a Java Spring Boot application that processes receipts and calculates points based on Fetch Rewards' rules.

## Features
- **Process receipts** (`POST /receipts/process`)
- **Get points for a receipt** (`GET /receipts/{id}/points`)
- **Implements Fetch Rewards receipt scoring rules**

---

## How to Run

### **Clone the Repository**
```sh
git clone https://github.com/yourusername/receipt-processor.git
cd receipt-processor
```

### **Build the Project**
```sh
mvn clean package
```

### **Run the Application**
```sh
mvn spring-boot:run
```
 **Your API will be available at** `http://localhost:8080`

---

## API Endpoints

### **1. Process a Receipt**
**Request:**
```sh
curl -X POST http://localhost:8080/receipts/process \
     -H "Content-Type: application/json" \
     -d '{
         "retailer": "Target",
         "purchaseDate": "2022-01-01",
         "purchaseTime": "13:01",
         "items": [
             { "shortDescription": "Mountain Dew 12PK", "price": "6.49" },
             { "shortDescription": "Emils Cheese Pizza", "price": "12.25" },
             { "shortDescription": "Knorr Creamy Chicken", "price": "1.26" },
             { "shortDescription": "Doritos Nacho Cheese", "price": "3.35" },
             { "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ", "price": "12.00" }
         ],
         "total": "35.35"
     }'
```

---

### **2. Get Receipt Points**
**Request:**
```sh
curl -X GET http://localhost:8080/receipts/abcd1234-ef56-7890-ghij-klmnopqrstuv/points
```

**Response Example:**
```json
{ "points": 28 }
```

---

##  Run with Docker
### **1. Build Docker Image**
```sh
docker build -t receipt-processor .
```

### **2. Run Docker Container**
```sh
docker run -p 8080:8080 receipt-processor
```
**Your API will be available at** `http://localhost:8080`

---

## Running Tests
```sh
mvn test
```

---

## Submission Notes
This project was built using **Spring Boot**, **Maven**, and **Docker**. If you have any issues running the project, please ensure:
- **Java 17+** is installed
- **Maven** is installed
- **Docker** (if running via container) is installed

**Developed by:** Sicheng Cao
