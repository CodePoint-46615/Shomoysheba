# Shomoysheba

[![Java 17](https://img.shields.io/badge/Java-17-007396?logo=openjdk)](https://openjdk.org/) [![Spring Boot 3.5.3](https://img.shields.io/badge/Spring%20Boot-3.5.3-6DB33F?logo=spring-boot)](https://spring.io/projects/spring-boot) [![Build: Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven)](https://maven.apache.org/) [![Database: MySQL](https://img.shields.io/badge/DB-MySQL-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/) [![Auth: JWT](https://img.shields.io/badge/Auth-JWT-000000?logo=jsonwebtokens&logoColor=white)](https://jwt.io/) [![License](https://img.shields.io/badge/License-See%20LICENSE-blue.svg)](LICENSE)

## Overview
What you get:
- ✅ User registration and login
- ✅ JWT issuance and validation (stateless sessions)
- ✅ Role-based access control (ADMIN, USER)
- ✅ Profile fetch and update
- ✅ Change / reset password flows
- ✅ Request logging via Spring AOP

## 🚀 Quick Start
1) Prerequisites
- Java 17+
- MySQL 8+
- Maven (optional; Maven Wrapper is included)

## 🔎 API at a glance
Base path: `/api/users`

| Method | Path             | Auth             | Role   | Description                         |
|-------:|------------------|------------------|--------|-------------------------------------|
| POST   | /register        | Public           | Any    | Register a new user                 |
| POST   | /login           | Public           | Any    | Authenticate and receive JWT        |
| POST   | /forgot-password | Public           | Any    | Send temporary password             |
| POST   | /profile         | Bearer token     | Any    | Get current user profile            |
| PUT    | /update          | Bearer token     | Any    | Update name and email               |
| POST   | /change-password | Bearer token     | Any    | Change password                     |
| GET    | /all             | Bearer token     | ADMIN  | List all users                      |

Headers: `Authorization: Bearer <JWT_TOKEN>`

## 🧰 Tech Stack
- Language: Java 17
- Runtime: Spring Boot 3.5.3
- Build: Maven (wrapper included: mvnw/mvnw.cmd)
- Web: spring-boot-starter-web (embedded Tomcat)
- Security: spring-boot-starter-security, BCrypt password encoder
- Auth: JSON Web Tokens (jjwt-api, jjwt-impl, jjwt-jackson)
- Persistence: Spring JDBC (JdbcTemplate), MySQL Connector/J
- Validation: Jakarta Bean Validation (hibernate-validator)
- AOP/Logging: spring-boot-starter-aop
- Mail: spring-boot-starter-mail (prepared for email flows)
- JSON: Jackson (jackson-databind)
- Testing: spring-boot-starter-test

## 🗂️ Project Structure
```
Shomoysheba/
├─ pom.xml
├─ mvnw
├─ mvnw.cmd
├─ .mvn/
│  └─ wrapper/
│     └─ maven-wrapper.properties
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ com/example/project/
│  │  │     ├─ ProjectApplication.java
│  │  │     ├─ api/
│  │  │     │  └─ UserAPI.java
│  │  │     ├─ aspect/
│  │  │     │  └─ LoggingAspect.java
│  │  │     ├─ config/
│  │  │     │  └─ SecurityConfig.java
│  │  │     ├─ entity/
│  │  │     │  └─ User.java
│  │  │     ├─ jwt/
│  │  │     │  ├─ AuthEntryPoint.java
│  │  │     │  ├─ AuthFilter.java
│  │  │     │  └─ JwtUtil.java
│  │  │     ├─ repository/
│  │  │     │  └─ UserRepository.java
│  │  │     └─ service/
│  │  │        ├─ CustomUserService.java
│  │  │        ├─ EmailService.java
│  │  │        └─ UserService.java
│  │  └─ resources/
│  │     ├─ jsonFormat.txt
│  │     └─ schema.sql
│  └─ test/
│     └─ java/
│        └─ com/example/project/
│           └─ ProjectApplicationTests.java
├─ .gitignore
├─ .gitattributes
├─ LICENSE
└─ README.md
```
