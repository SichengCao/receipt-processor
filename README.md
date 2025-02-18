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

### **Build the Project**


mvn clean package
### **Run the Application**
sh

mvn spring-boot:run
Your API will be available at http://localhost:8080

### ** Run with Docker **
1️ Build Docker Image
sh

docker build -t receipt-processor .
2️ Run Docker Container
sh

docker run -p 8080:8080 receipt-processor
