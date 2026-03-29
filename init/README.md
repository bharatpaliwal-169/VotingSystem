# Voting System API

A basic CRUD REST API for a voting system built with Spring Boot. This project demonstrates key Spring Boot concepts including JPA, Security, JWT authentication, and RESTful APIs.

## Features

- User registration and login with JWT authentication
- Register as candidate or voter
- Cast votes
- View candidates

## Technologies Used

- **Spring Boot**: Framework for building the application
- **Spring Data JPA**: For database operations
- **Spring Security**: For authentication and authorization
- **JWT**: For token-based authentication
- **H2 Database**: In-memory database for simplicity
- **Lombok**: For reducing boilerplate code

## Annotations Explained

Throughout the code, you'll find detailed comments explaining each annotation:

- `@SpringBootApplication`: Marks the main class and enables auto-configuration
- `@Entity`: Maps a class to a database table
- `@Id`, `@GeneratedValue`: Defines primary keys
- `@Repository`: Marks data access components
- `@Service`: Marks business logic components
- `@RestController`: Creates RESTful web services
- `@RequestMapping`: Maps web requests to handler methods
- `@Autowired`: Enables dependency injection
- And many more...

## API Endpoints

### Authentication
- `POST /api/auth/signup` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Candidates
- `POST /api/candidates/register` - Register as candidate (requires JWT)
- `GET /api/candidates` - Get all candidates

### Voters
- `POST /api/voters/register` - Register as voter (requires JWT)

### Votes
- `POST /api/votes/cast` - Cast a vote (requires JWT)

## Running the Application

1. Ensure you have Java 17 installed
2. Clone the repository
3. Navigate to the project directory
4. Run `./mvnw spring-boot:run`

The application will start on `http://localhost:8080`

## Testing the API

You can use tools like Postman or curl to test the endpoints.

### Example: Signup
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password","email":"test@example.com"}'
```

### Example: Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password"}'
```

Use the returned token in the Authorization header for protected endpoints:
```
Authorization: Bearer <token>
```

## Database

The application uses H2 in-memory database. You can access the H2 console at `http://localhost:8080/h2-console` with:
- JDBC URL: `jdbc:h2:mem:voting_system`
- Username: `sa`
- Password: (leave blank)

## Project Structure

- `model/`: Entity classes
- `repository/`: Data access layer
- `service/`: Business logic layer
- `controller/`: REST controllers
- `dto/`: Data transfer objects
- `util/`: Utility classes
- `config/`: Configuration classes

This project serves as a comprehensive example of building a REST API with Spring Boot, covering authentication, data persistence, and RESTful design patterns.