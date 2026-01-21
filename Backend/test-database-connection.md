# Database Connection Test

## Quick Test Steps:

1. **Start MySQL Service:**
   ```bash
   # Windows (if installed as service)
   net start mysql
   
   # macOS with Homebrew
   brew services start mysql
   
   # Linux
   sudo systemctl start mysql
   ```

2. **Verify Database Exists:**
   ```bash
   mysql -u event_user -p
   # Enter password: event_password123
   ```
   
   ```sql
   SHOW DATABASES;
   USE event_registration_db;
   SHOW TABLES;
   EXIT;
   ```

3. **Test Spring Boot Connection:**
   ```bash
   cd Backend
   mvn spring-boot:run
   ```
   
   Look for these success messages:
   - ✅ `HikariPool-1 - Start completed`
   - ✅ `Started EventRegistrationApplication`
   - ✅ `Default admin user created`
   - ✅ `Default faculty user created`

## If You See Errors:

### Connection Refused:
- Check if MySQL is running: `sudo systemctl status mysql`
- Verify port 3306 is open: `netstat -an | grep 3306`

### Access Denied:
- Double-check username/password in application.properties
- Verify user exists: `SELECT User FROM mysql.user;`

### Database Not Found:
- Create database: `CREATE DATABASE event_registration_db;`
- Grant permissions again

### Tables Not Created:
- Check `spring.jpa.hibernate.ddl-auto=update` is set
- Look for Hibernate logs in console
- Verify MySQL user has CREATE privileges