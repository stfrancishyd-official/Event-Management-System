# Event Registration Management System - Backend

A comprehensive Spring Boot application for managing event registrations in educational institutions.

## Features

### Student Features
- **User Registration**: Register with email verification via OTP
- **Email Verification**: 6-digit OTP sent to email for account verification
- **Login/Logout**: JWT-based authentication
- **View Events**: Browse all active events with details (poster, date, description, department)
- **Event Registration**: Register for events with student details (name, roll number, section, batch year)
- **Registration History**: View all registered events
- **Profile Management**: Update student profile information

### Faculty Features
- **Event Creation**: Create new events with all details
- **Event Management**: Update and delete own events
- **Registration Reports**: View list of students registered for their events
- **Event Statistics**: Get registration statistics for all their events

### Admin Features
- **System Overview**: View all users, events, and registrations
- **System Statistics**: Get overall system statistics
- **Complete Access**: Access to all data in the system

## Technology Stack

- **Backend**: Spring Boot 3.x
- **Database**: MySQL
- **Security**: Spring Security + JWT
- **Email**: Spring Mail (SMTP)
- **Validation**: Bean Validation
- **Build Tool**: Maven

## Setup Instructions

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE event_registration_db;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event_registration_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Email Configuration
Update email settings in `application.properties`:
```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

**Note**: For Gmail, use App Password instead of regular password.

### Running the Application

1. Clone the repository
2. Navigate to the Backend directory
3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Default Users
The application creates default users on startup:

- **Admin**: admin@stfrancis.edu / admin123
- **Faculty**: faculty@stfrancis.edu / faculty123

## API Endpoints

### Authentication Endpoints (`/api/auth`)
- `POST /register` - User registration
- `POST /verify-email` - Email verification with OTP
- `POST /login` - User login
- `POST /resend-otp` - Resend verification OTP
- `POST /forgot-password` - Request password reset
- `POST /reset-password` - Reset password with OTP
- `PUT /profile` - Update user profile

### Student Endpoints (`/api/student`)
- `GET /events` - Get all active events
- `GET /events/{id}` - Get specific event details
- `POST /register` - Register for an event
- `GET /my-registrations` - Get registration history

### Faculty Endpoints (`/api/faculty`)
- `POST /events` - Create new event
- `GET /my-events` - Get own events
- `PUT /events/{id}` - Update own event
- `DELETE /events/{id}` - Delete own event
- `GET /events/{id}/registrations` - Get event registrations
- `GET /registration-stats` - Get registration statistics

### Admin Endpoints (`/api/admin`)
- `GET /events` - Get all events
- `GET /users` - Get all users
- `GET /registrations` - Get all registrations
- `GET /stats` - Get system statistics

## Database Schema

### Users Table
- id, name, email, password, role, is_verified
- verification_token, token_expiry, created_at
- roll_number, section, batch_year, department

### Events Table
- id, title, description, event_date, registration_deadline
- department, poster_url, max_participants, current_participants
- is_active, created_by, created_at, updated_at

### Event Registrations Table
- id, event_id, user_id, student_name, roll_number
- section, batch_year, registered_at

## Security Features

- **JWT Authentication**: Secure token-based authentication
- **Role-based Access Control**: Different access levels for students, faculty, and admin
- **Email Verification**: OTP-based email verification
- **Password Encryption**: BCrypt password hashing
- **CORS Configuration**: Configured for React frontend

## Error Handling

The application includes comprehensive error handling with meaningful error messages for:
- Validation errors
- Authentication failures
- Authorization issues
- Business logic violations
- Database constraints

## Development Notes

- All passwords are encrypted using BCrypt
- JWT tokens expire after 24 hours
- OTP tokens expire after 15 minutes
- Email verification is required before login
- Students can only register for active events
- Faculty can only manage their own events
- Admin has full system access

## Testing

Use tools like Postman or curl to test the API endpoints. Make sure to include the JWT token in the Authorization header for protected endpoints:

```
Authorization: Bearer <your-jwt-token>
```