# Setup Guide for Event Registration Management System

## 📋 Pre-Setup Checklist

Before you start, make sure you have:
- [ ] Java 17+ installed
- [ ] Node.js 16+ installed
- [ ] MySQL 8.0+ installed and running
- [ ] Maven 3.6+ installed
- [ ] Git installed

## 🗄️ Database Setup

1. **Start MySQL server**
2. **Create database:**
```sql
CREATE DATABASE event_registration_db;
CREATE USER 'event_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON event_registration_db.* TO 'event_user'@'localhost';
FLUSH PRIVILEGES;
```

## ⚙️ Backend Configuration

1. **Navigate to Backend directory:**
```bash
cd Backend
```

2. **Copy configuration template:**
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

3. **Edit application.properties:**
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/event_registration_db
spring.datasource.username=event_user
spring.datasource.password=your_password

# Email Configuration (Gmail)
spring.mail.username=your-email@gmail.com
spring.mail.password=your-gmail-app-password

# JWT Secret (Generate a secure key)
jwt.secret=your-very-secure-secret-key-minimum-256-bits
```

4. **Gmail App Password Setup:**
   - Go to Google Account settings
   - Enable 2-Factor Authentication
   - Generate App Password for "Mail"
   - Use this app password in application.properties

## 🚀 Running the Application

### Backend (Terminal 1):
```bash
cd Backend
mvn spring-boot:run
```
Backend will start on: http://localhost:8080

### Frontend (Terminal 2):
```bash
cd Frontend
npm install
npm start
```
Frontend will start on: http://localhost:3000

## 👥 Default Login Credentials

After the backend starts, you can login with:

**Admin Account:**
- Email: admin@stfrancis.edu
- Password: admin123

**Faculty Account:**
- Email: faculty@stfrancis.edu
- Password: faculty123

## 🧪 Testing the Setup

1. **Open browser:** http://localhost:3000
2. **Register a new student account**
3. **Check email for OTP verification**
4. **Login and browse events**
5. **Login as faculty to create events**
6. **Login as admin to view system overview**

## 🔧 Troubleshooting

### Common Issues:

**Database Connection Error:**
- Check MySQL is running
- Verify database credentials
- Ensure database exists

**Email Not Sending:**
- Verify Gmail app password
- Check internet connection
- Ensure 2FA is enabled on Gmail

**Port Already in Use:**
- Backend: Change `server.port` in application.properties
- Frontend: Set `PORT=3001` environment variable

**JWT Token Issues:**
- Ensure JWT secret is at least 256 bits
- Clear browser localStorage
- Check token expiration settings

## 📁 Project Structure After Setup

```
event-registration-system/
├── Backend/
│   ├── src/main/resources/
│   │   ├── application.properties (your config)
│   │   └── application.properties.example
│   └── target/ (generated)
├── Frontend/
│   ├── node_modules/ (generated)
│   ├── build/ (after npm run build)
│   └── src/
└── README.md
```

## 🚀 Next Steps

1. **Customize the application:**
   - Update college name and branding
   - Modify email templates
   - Add more departments

2. **Deploy to production:**
   - Set up production database
   - Configure production email
   - Deploy to cloud services

3. **Add features:**
   - File upload for event posters
   - SMS notifications
   - Payment integration
   - Advanced reporting

## 📞 Need Help?

If you encounter issues:
1. Check the logs in terminal
2. Verify all configuration settings
3. Ensure all prerequisites are installed
4. Create an issue on GitHub with error details

Happy coding! 🎉