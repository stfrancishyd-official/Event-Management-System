# Event Registration Management System

A comprehensive web application for managing event registrations in educational institutions, built with Spring Boot and React.

## 🚀 Features

### 👨‍🎓 Student Features
- **User Registration**: Register with email verification via OTP
- **Email Verification**: 6-digit OTP sent to email for account verification
- **Event Browsing**: View all available events with details (poster, date, description, department)
- **Event Registration**: Register for events with student details (name, roll number, section, batch year)
- **Registration History**: Track all registered events
- **Profile Management**: Update student profile information

### 👨‍🏫 Faculty Features
- **Event Creation**: Create new events with comprehensive details
- **Event Management**: Update and delete own events
- **Registration Reports**: View detailed list of students registered for their events
- **Event Statistics**: Get registration analytics for all their events

### 👨‍💼 Admin Features
- **System Overview**: Complete view of all users, events, and registrations
- **System Statistics**: Comprehensive analytics and reports
- **User Management**: Access to all system data

## 🛠 Technology Stack

### Backend
- **Framework**: Spring Boot 3.x
- **Database**: MySQL 8.0+
- **Security**: Spring Security + JWT
- **Email**: Spring Mail (SMTP)
- **Validation**: Bean Validation
- **Build Tool**: Maven

### Frontend
- **Framework**: React 18
- **Routing**: React Router DOM
- **HTTP Client**: Axios
- **Styling**: Bootstrap 5
- **State Management**: React Context API

## 📋 Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- npm or yarn

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/event-registration-system.git
cd event-registration-system
```

### 2. Database Setup
```sql
CREATE DATABASE event_registration_db;
```

### 3. Backend Setup
```bash
cd Backend

# Copy and configure application properties
cp src/main/resources/application.properties.example src/main/resources/application.properties

# Edit application.properties with your database and email credentials
# Update the following:
# - Database URL, username, password
# - Email SMTP settings
# - JWT secret key

# Run the application
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### 4. Frontend Setup
```bash
cd Frontend

# Install dependencies
npm install

# Start the development server
npm start
```

The frontend will start on `http://localhost:3000`

## 🔧 Configuration

### Database Configuration
Update `Backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event_registration_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Email Configuration
For Gmail SMTP, update:
```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```
**Note**: Use App Password for Gmail, not your regular password.

### JWT Configuration
Generate a secure secret key:
```properties
jwt.secret=your-very-secure-secret-key-at-least-256-bits
```

## 👥 Default Users

The application creates default users on startup:

- **Admin**: admin@stfrancis.edu / admin123
- **Faculty**: faculty@stfrancis.edu / faculty123

## 📚 API Documentation

### Authentication Endpoints
- `POST /api/auth/register` - User registration
- `POST /api/auth/verify-email` - Email verification
- `POST /api/auth/login` - User login
- `POST /api/auth/forgot-password` - Password reset request
- `POST /api/auth/reset-password` - Password reset

### Student Endpoints
- `GET /api/student/events` - Get all active events
- `POST /api/student/register` - Register for an event
- `GET /api/student/my-registrations` - Get registration history

### Faculty Endpoints
- `POST /api/faculty/events` - Create new event
- `GET /api/faculty/my-events` - Get own events
- `PUT /api/faculty/events/{id}` - Update event
- `DELETE /api/faculty/events/{id}` - Delete event
- `GET /api/faculty/events/{id}/registrations` - Get event registrations

### Admin Endpoints
- `GET /api/admin/events` - Get all events
- `GET /api/admin/users` - Get all users
- `GET /api/admin/stats` - Get system statistics

## 🗄 Database Schema

### Users Table
- User information with roles (STUDENT, FACULTY, ADMIN)
- Email verification status
- Student-specific fields (roll number, section, batch year)

### Events Table
- Event details (title, description, date, department)
- Registration limits and current count
- Created by faculty reference

### Event Registrations Table
- Student-event registration mapping
- Registration timestamp and student details

## 🔒 Security Features

- **JWT Authentication**: Secure token-based authentication
- **Role-based Access Control**: Different access levels for each user type
- **Email Verification**: OTP-based email verification
- **Password Encryption**: BCrypt password hashing
- **CORS Configuration**: Configured for React frontend

## 🚀 Deployment

### Backend Deployment
1. Build the JAR file: `mvn clean package`
2. Deploy to your server
3. Update application.properties for production
4. Set up MySQL database

### Frontend Deployment
1. Build for production: `npm run build`
2. Deploy the `build` folder to static hosting (Netlify, Vercel, etc.)
3. Update API base URL for production

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin feature-name`
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- Create an issue on GitHub
- Email: support@stfrancis.edu

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- React team for the frontend library
- Bootstrap team for the UI components
- All contributors and testers

---

**Made with ❤️ for St. Francis College**