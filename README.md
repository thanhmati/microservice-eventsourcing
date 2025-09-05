# 📚 Library Management System - Microservices with Event Sourcing

> Hệ thống quản lý thư viện được xây dựng theo kiến trúc **Microservices** kết hợp với **Event Sourcing** pattern, sử dụng **Spring Boot**, **Axon Framework**, và **Apache Kafka**.

## 🏗️ Kiến trúc hệ thống

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Client Apps   │───▶│   API Gateway    │───▶│  Discovery      │
│                 │    │   :8080          │    │  Server :8761   │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
        ┌───────▼──────┐ ┌─────▼─────┐ ┌──────▼──────┐
        │ Book Service │ │User Service│ │Employee     │
        │    :9001     │ │   :9005    │ │Service :9002│
        └──────────────┘ └───────────┘ └─────────────┘
                │               │               │
        ┌───────▼──────┐ ┌─────▼─────┐ ┌──────▼──────┐
        │ Borrowing    │ │   MySQL   │ │   Axon      │
        │Service :9003 │ │ Database  │ │Server :8024 │
        └──────────────┘ └───────────┘ └─────────────┘
                │
        ┌───────▼──────┐ ┌─────────────┐ ┌─────────────┐
        │Notification  │ │   Kafka     │ │   Redis     │
        │Service :9003 │ │  :9092      │ │   :6379     │
        └──────────────┘ └─────────────┘ └─────────────┘
```

## 🚀 Services Overview

| Service | Port | Technology Stack | Purpose |
|---------|------|------------------|---------|
| **API Gateway** | 8080 | Spring Cloud Gateway, Redis, OAuth2 | Entry point, routing, rate limiting, authentication |
| **Discovery Server** | 8761 | Netflix Eureka | Service registry and discovery |
| **Book Service** | 9001 | Spring Boot, Axon Framework, H2 | Book management with Event Sourcing |
| **User Service** | 9005 | Spring Boot, MySQL, Keycloak | User management and authentication |
| **Employee Service** | 9002 | Spring Boot, Axon Framework, H2 | Employee management with Event Sourcing |
| **Borrowing Service** | 9003 | Spring Boot, Axon Framework, H2 | Book borrowing with Saga Pattern |
| **Notification Service** | 9003 | Spring Boot, Kafka, Email | Async notifications and messaging |
| **Common Service** | - | Shared library | Common DTOs, events, and utilities |

## 🎯 Key Features & Patterns

### ✅ Microservices Patterns
- **API Gateway Pattern** - Centralized entry point
- **Service Discovery** - Dynamic service location
- **Database per Service** - Data isolation
- **Circuit Breaker** - Fault tolerance (planned)

### ✅ Event-Driven Architecture
- **Event Sourcing** - Complete audit trail
- **CQRS** - Command Query Responsibility Segregation
- **Saga Pattern** - Distributed transaction management
- **Event-Driven Communication** - Loose coupling

### ✅ Technology Integration
- **Axon Framework** - Event Sourcing and CQRS
- **Apache Kafka** - Message streaming
- **Redis** - Caching and rate limiting
- **Keycloak** - Identity and access management

## 🛠️ Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker & Docker Compose
- MySQL (for User Service)

### 1. Start Infrastructure
```bash
# Start supporting services
docker-compose up -d axonserver redis zookeeper broker control-center

# Verify services are running
docker-compose ps
```

### 2. Start Core Services
```bash
# Start Discovery Server first
cd discoverserver
mvn spring-boot:run

# Wait for Discovery Server to be ready, then start other services
cd ../bookservice && mvn spring-boot:run &
cd ../userservice && mvn spring-boot:run &
cd ../employeeservice && mvn spring-boot:run &
cd ../borrowingservice && mvn spring-boot:run &
cd ../notificationservice && mvn spring-boot:run &
cd ../apigateway && mvn spring-boot:run &
```

### 3. Setup Keycloak (Optional)
```bash
# Import Keycloak configuration from KeyCloak.postman_collection.json
# Configure realm: ltfullstack
# Client ID: ltfullstack_app
```

## 📋 API Examples

### Create a Book
```bash
curl -X POST http://localhost:8080/api/v1/books \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot in Action",
    "author": "Craig Walls",
    "isReady": true
  }'
```

### Get All Books
```bash
curl http://localhost:8080/api/v1/books
```

### Create Employee
```bash
curl -X POST http://localhost:8080/api/v1/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "kin": "Jane Doe",
    "isDisciplined": false
  }'
```

### Borrow a Book (Saga Pattern)
```bash
curl -X POST http://localhost:8080/api/v1/borrowing \
  -H "Content-Type: application/json" \
  -d '{
    "bookId": "<book-id>",
    "employeeId": "<employee-id>"
  }'
```

## 🔍 Monitoring & Management

### Service Health Checks
```bash
# Check Discovery Server
curl http://localhost:8761

# Check API Gateway
curl http://localhost:8080/actuator/health

# Check individual services
curl http://localhost:9001/actuator/health  # Book Service
curl http://localhost:9002/actuator/health  # Employee Service
```

### Event Store (Axon Server)
- Dashboard: http://localhost:8024
- View events, aggregates, and saga instances

### Kafka Control Center
- Dashboard: http://localhost:9021
- Monitor topics, consumers, and message flow

## 🧪 Testing Scenarios

### 1. Successful Book Borrowing
```bash
# 1. Create a book (isReady: true)
# 2. Create an employee (isDisciplined: false)
# 3. Create borrowing request
# 4. Verify book status changed to isReady: false
```

### 2. Saga Rollback (Employee Disciplined)
```bash
# 1. Update employee to isDisciplined: true
# 2. Try to borrow book
# 3. Verify saga rollback and book remains available
```

### 3. Saga Rollback (Book Not Available)
```bash
# 1. Set book isReady: false
# 2. Try to borrow book
# 3. Verify borrowing request is rejected
```

## 📚 Learning Resources

### 📖 Detailed Documentation
- **[Complete Course Documentation](./Tai_lieu_khoa_hoc_Microservice_Event_Sourcing.md)** - Comprehensive guide for students and instructors

### 🎓 Key Concepts Covered
- **Event Sourcing** - Store events instead of current state
- **CQRS** - Separate read and write models
- **Saga Pattern** - Manage distributed transactions
- **Microservices Communication** - Sync vs Async patterns
- **API Gateway** - Centralized routing and security

### 🛠️ Hands-on Labs
- Setting up microservices infrastructure
- Implementing Event Sourcing with Axon
- Building distributed sagas
- Testing failure scenarios and rollbacks

## 🏗️ Architecture Decisions

### Why Event Sourcing?
- **Complete audit trail** - Every state change is recorded
- **Temporal queries** - Query system state at any point in time
- **Event replay** - Rebuild system state from events
- **Natural fit** for event-driven microservices

### Why Saga Pattern?
- **Distributed transactions** across multiple services
- **Compensating actions** for failure handling
- **Loose coupling** between services
- **Eventual consistency** with reliability

### Why CQRS?
- **Performance optimization** - Separate read/write models
- **Scalability** - Scale reads and writes independently
- **Flexibility** - Different models for different use cases

## 🚧 Development Roadmap

### Phase 1: Core Features ✅
- [x] Basic CRUD operations
- [x] Event Sourcing implementation
- [x] Saga pattern for borrowing
- [x] API Gateway with routing

### Phase 2: Enhanced Features 🔄
- [ ] Book return workflow
- [ ] Advanced search and filtering
- [ ] Email notifications
- [ ] Metrics and monitoring

### Phase 3: Production Features 📋
- [ ] Event versioning and migration
- [ ] Distributed tracing
- [ ] Circuit breaker implementation
- [ ] Comprehensive testing suite

## 🤝 Contributing

### Development Setup
1. Fork the repository
2. Create feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

### Code Standards
- Follow Spring Boot best practices
- Write comprehensive tests
- Document API changes
- Update architecture diagrams

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Troubleshooting

### Common Issues

**Services not registering with Eureka**
```bash
# Check if Discovery Server is running
curl http://localhost:8761

# Verify service configuration
# eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

**Axon Server connection issues**
```bash
# Check Axon Server status
docker-compose logs axonserver

# Verify connection string
# axon.axonserver.servers=axonserver:8124
```

**Kafka consumer not receiving messages**
```bash
# Check Kafka cluster status
docker-compose logs broker

# Verify topic creation
docker-compose exec broker kafka-topics --bootstrap-server localhost:9092 --list
```

### Getting Help
- 📖 Check the [detailed documentation](./Tai_lieu_khoa_hoc_Microservice_Event_Sourcing.md)
- 🐛 Report issues in the GitHub Issues section
- 💬 Join discussions in the project wiki

---

**Built with ❤️ for learning Microservices and Event Sourcing**

*This project serves as a comprehensive example of modern distributed system patterns and is designed for educational purposes.*