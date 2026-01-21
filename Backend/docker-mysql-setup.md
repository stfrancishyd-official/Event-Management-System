# Docker MySQL Setup (Alternative)

If you prefer using Docker instead of installing MySQL directly:

## 1. Install Docker Desktop
Download from: https://www.docker.com/products/docker-desktop

## 2. Run MySQL Container
```bash
docker run --name mysql-event-db \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=event_registration_db \
  -e MYSQL_USER=event_user \
  -e MYSQL_PASSWORD=event_password123 \
  -p 3306:3306 \
  -d mysql:8.0
```

## 3. Verify Container is Running
```bash
docker ps
```

## 4. Connect to Database (Optional)
```bash
docker exec -it mysql-event-db mysql -u event_user -p
# Enter password: event_password123
```

## 5. Use Same application.properties
The application.properties configuration remains the same since Docker exposes MySQL on localhost:3306.

## 6. Stop/Start Container
```bash
# Stop
docker stop mysql-event-db

# Start
docker start mysql-event-db

# Remove (if needed)
docker rm mysql-event-db
```

This approach gives you a clean, isolated MySQL instance without installing MySQL directly on your system.