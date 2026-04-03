# Gateway Microservice HMS

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue)](https://spring.io/projects/spring-cloud)
[![Spring Cloud Gateway](https://img.shields.io/badge/Spring%20Cloud%20Gateway-WebFlux-blueviolet)](https://spring.io/projects/spring-cloud-gateway)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)
[![Eureka](https://img.shields.io/badge/Netflix%20Eureka-Client-lightgrey)](https://cloud.spring.io/spring-cloud-netflix/)

## 🚀 What the Project Does

`gateway-microservice-hms` is a Spring Cloud Gateway-based API Gateway for the Hospital Management System. It acts as the entry point for all client requests, routing them to appropriate microservices (User, Profile, Appointment, Pharmacy). The gateway implements JWT token validation, CORS handling, and service discovery integration with Netflix Eureka. It provides a centralized layer for authentication, request routing, and cross-cutting concerns in a distributed microservices architecture.

## 💡 Why the Project is Useful

- Provides a single entry point for frontend clients, abstracting the complexity of multiple downstream microservices.
- Centralizes JWT token validation using a custom TokenFilter before requests reach backend services.
- Implements global CORS configuration to handle cross-origin requests from frontend applications.
- Enables seamless service discovery through Eureka, allowing dynamic routing to microservices.
- Deduplicates CORS headers to prevent header conflicts in responses.
- Supports flexible environment-based routing and microservice discovery with sensible defaults.

## ✨ Key Features

- **Centralized routing** to User, Profile, Appointment, and Pharmacy microservices.
- **JWT token validation** with custom TokenFilter supporting bypass for login/register endpoints.
- **Global CORS configuration** allowing cross-origin requests from frontend clients.
- **Service discovery integration** with Netflix Eureka for dynamic microservice location.
- **Deduplicating Response Headers** to prevent duplicate CORS headers in responses.
- **Environment-based configuration** using properties or environment variables for flexibility.
- **Configurable ports** via `PORT` environment variable (default: 9000).

## 🛠️ Tech Stack

- Frontend: (Not included in this module) expects a separate client app.
- Backend:
  - Java 21
  - Spring Boot 3.5.3
  - Spring Cloud Gateway Server WebFlux
  - Spring Cloud Eureka Client
  - JWT (`io.jsonwebtoken` v0.11.5)
- DevOps / Tools:
  - Maven
  - Docker (multi-stage Dockerfile)
  - Spring Boot Maven Plugin

## ⚙️ Getting Started (Installation & Setup)

```bash
git clone https://github.com/vaibhavchaubey/gateway-microservice-hms.git
cd gateway-microservice-hms
```

### 1. Configure Microservice URLs

Edit `src/main/resources/application.properties` or set environment variables:

- `USER_MICROSERVICE_URL` (default: `lb://user-microservice-hms`)
- `PROFILE_MICROSERVICE_URL` (default: `lb://profile-microservice-hms`)
- `APPOINTMENT_MICROSERVICE_URL` (default: `lb://appointment-microservice-hms`)
- `PHARMACY_MICROSERVICE_URL` (default: `lb://pharmacy-microservice-hms`)

### 2. Configure CORS

Set allowed origins in `src/main/resources/application.properties`:

- `CORS_ALLOWED_ORIGINS` (default: `http://localhost:5173`)

### 3. Configure Eureka (Optional)

If using Eureka for service discovery (via `lb://` protocol in URLs):

- Ensure Eureka Server is running (typically at `http://localhost:8761/eureka/`)
- Update `spring.cloud.gateway.server.webflux.routes[*].uri` with `lb://microservice-name` format

### 4. Update JWT Secret Key

⚠️ **IMPORTANT**: The JWT secret key in `TokenFilter.java` should be externalized and secured:

```java
private static final String SECRET_KEY = "your-secure-secret-key-here";
```

Consider using Spring Cloud Config or environment variables for production.

### 5. Run Locally

```bash
./mvnw clean package
./mvnw spring-boot:run
```

Or with environment variables:

```bash
PORT=9000 ./mvnw spring-boot:run
```

Gateway starts on port `9000` by default (override with `PORT` env var).

### 6. API Routes

The gateway routes requests based on path patterns:

#### User Routes

- `GET /user/login` → user-microservice-hms (bypasses token validation)
- `GET /user/register` → user-microservice-hms (bypasses token validation)
- `GET /user/**` → user-microservice-hms (requires valid JWT token)

#### Profile Routes

- `GET /profile/**` → profile-microservice-hms (requires valid JWT token)

#### Appointment Routes

- `GET /appointment/**` → appointment-microservice-hms (requires valid JWT token)
- `POST /appointment/**` → appointment-microservice-hms (requires valid JWT token)
- Examples:
  - `POST /appointment/schedule`
  - `GET /appointment/get/{id}`
  - `GET /appointment/getAllByPatient/{patientId}`

#### Pharmacy Routes

- `GET /pharmacy/**` → pharmacy-microservice-hms (requires valid JWT token)
- `POST /pharmacy/**` → pharmacy-microservice-hms (requires valid JWT token)

### 7. Request Headers

All requests (except `/user/login` and `/user/register`) **must** include:

```
Authorization: Bearer <JWT_TOKEN>
```

Example using cURL:

```bash
curl -H "Authorization: Bearer your_jwt_token_here" \
  http://localhost:9000/appointment/get/1
```

## 🧪 Tests

```bash
./mvnw test
```

## 🚢 Docker (Optional)

Build image:

```bash
docker build -t gateway-microservice-hms:latest .
```

Run container with linked microservices:

```bash
docker run \
  -e PORT=9000 \
  -e USER_MICROSERVICE_URL=http://host.docker.internal:9100 \
  -e PROFILE_MICROSERVICE_URL=http://host.docker.internal:9101 \
  -e APPOINTMENT_MICROSERVICE_URL=http://host.docker.internal:9200 \
  -e PHARMACY_MICROSERVICE_URL=http://host.docker.internal:9300 \
  -e CORS_ALLOWED_ORIGINS=http://localhost:5173 \
  -p 9000:9000 \
  gateway-microservice-hms:latest
```

Or use Docker Compose for the entire HMS stack (recommended).

## 📦 Project Metadata

- Name: gateway-microservice-hms
- Description: Gateway Microservice for Hospital Management System
- Artifact: `com.hms:gateway-microservice-hms:0.0.1-SNAPSHOT`
- Default Port: 9000

## 🔐 Security Considerations

1. **JWT Secret Key**: Currently hardcoded in `TokenFilter.java`. Move to configuration/secrets management for production.
2. **CORS Configuration**: Update `CORS_ALLOWED_ORIGINS` to match your frontend domain.
3. **Public Routes**: Login and register endpoints bypass token validation. Ensure they are properly validated at the backend microservice.
4. **Token Validation**: Tokens are validated against the hardcoded secret. Consider implementing token refresh mechanisms and expiration checks.

## 🤝 Contributing

1. Fork the repo
2. Create a feature branch
3. Add tests and run `./mvnw test`
4. Open PR with description and linked issue

## 📝 License

Add your open source / corporate license file at `/LICENSE` (e.g., Apache-2.0, MIT, etc.).
