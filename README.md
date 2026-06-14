[README.md](https://github.com/user-attachments/files/28921633/README.md)
# Student Enrollment System

![Java](https://img.shields.io/badge/Java-17-007396?logo=java&logoColor=white)
![Spring Framework](https://img.shields.io/badge/Spring%20Framework-6.x-6DB33F?logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-6.x-59666C?logo=hibernate&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-4169E1?logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven&logoColor=white)

A layered monolith built on plain **Spring Framework 6** (no Spring Boot) that manages student enrollment records. The project is intentionally built without auto-configuration, demonstrating manual IoC container bootstrapping, Hibernate ORM integration, declarative transaction management, and cross-cutting concerns via AspectJ AOP.

---

## Key Features

- Manual `AnnotationConfigApplicationContext` bootstrapping — no `@SpringBootApplication`, no auto-wiring magic.
- Java-based Spring configuration via `@Configuration`, `@ComponentScan`, and `@Import`.
- Connection pooling with HikariCP and a manually declared `LocalSessionFactoryBean` bridging Spring and Hibernate.
- Declarative transaction management using `@Transactional` backed by `HibernateTransactionManager`.
- AOP-driven logging and execution timing via `@Aspect` / `@Around` interceptors applied to the service layer.
- Strict layered architecture: `config → model → repository → service → aspect`.
- Constructor injection throughout — no field injection.

---

## Architecture

```
AnnotationConfigApplicationContext (Main.java)
        |
        +-- AppConfig.java            @Configuration, @ComponentScan, @EnableAspectJAutoProxy
        |       |
        |       +-- PersistenceConfig.java   DataSource, SessionFactory, TransactionManager
        |
        +-- org.example.model
        |       Student.java          @Entity, @Table, @Id, @GeneratedValue
        |
        +-- org.example.repository
        |       StudentRepository.java  @Repository — getCurrentSession().save(...)
        |
        +-- org.example.service
        |       StudentService.java    @Service, @Transactional
        |
        +-- org.example.aspect
                LoggingAspect.java    @Before / @After service methods
                TimingAspect.java     @Around — execution time in ms
```

### Folder Structure

```
student-enrollment-system/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── org/example/
                ├── Main.java
                ├── config/
                │   ├── AppConfig.java
                │   └── PersistenceConfig.java
                ├── model/
                │   └── Student.java
                ├── repository/
                │   └── StudentRepository.java
                ├── service/
                │   └── StudentService.java
                └── aspect/
                    ├── LoggingAspect.java
                    └── TimingAspect.java
```

---

## Getting Started

### Prerequisites

| Tool | Minimum Version |
|---|---|
| JDK | 17 |
| Maven | 3.9+ |
| PostgreSQL | 15+ |

### Installation

```bash
git clone https://github.com/khafifithebork/Student-Enrollment-System.git
cd Student-Enrollment-System
mvn clean install
```

### Environment Configuration

The database connection is configured directly in `AppConfig.java` via `HikariDataSource`. Before running, update the following values to match your local PostgreSQL instance:

```java
// src/main/java/org/example/config/AppConfig.java
dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/student_db");
dataSource.setUsername("your_username");
dataSource.setPassword("your_password");
```

Alternatively, externalize these values via a `.properties` file and load them with `@PropertySource` / `Environment`.

Create the database before running:

```sql
CREATE DATABASE student_db;
```

Hibernate is configured to auto-generate the schema on startup (`hbm2ddl.auto=update`). No manual migration step is required for first run.

### Running the Application

```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

Or compile and run directly:

```bash
mvn clean compile
java -cp target/classes:$(mvn -q dependency:build-classpath -Dmdep.outputFile=/dev/stdout) org.example.Main
```

---

## Usage

The entry point `Main.java` bootstraps the Spring context and exercises the service layer:

```java
AnnotationConfigApplicationContext context =
    new AnnotationConfigApplicationContext(AppConfig.class);

StudentService service = context.getBean(StudentService.class);

service.enroll("Ayman", 21);   // Persists a student record
service.listAll();             // Retrieves all enrolled students

context.close();               // Triggers @PreDestroy on managed beans
```

The `LoggingAspect` and `TimingAspect` intercept every call to `StudentService` methods automatically — no changes to business logic are required.

Expected console output (approximate):

```
[LOG]    >> Entering enroll(name=Ayman, age=21)
[TIMING] >> enroll executed in 42ms
[LOG]    >> Exiting enroll
```

> **Note on self-invocation:** Calling `enroll()` from within the same service class bypasses the Spring proxy. Aspect interceptors and `@Transactional` will **not** apply to internal calls. Always invoke service methods through the context-managed bean.

---

## Running Tests

```bash
mvn test
```

Unit tests for the service layer should mock `StudentRepository` using a standard mocking library (e.g., Mockito) to isolate business logic from the persistence layer. Integration tests should use an embedded H2 datasource wired via a dedicated test configuration class:

```java
@Configuration
@Import(PersistenceConfig.class)
public class TestConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }
}
```

