# Event Registration Management System - Frontend

A React-based frontend application for the Event Registration Management System.

## Features

### User Authentication
- User registration with email verification
- Login/logout functionality
- Role-based access control (Student, Faculty, Admin)
- JWT token management

### Student Features
- Browse available events
- Register for events with student details
- View registration history
- Student dashboard with statistics

### Faculty Features
- Create and manage events
- View event registrations
- Registration statistics and reports
- Faculty dashboard

### Admin Features
- System overview and statistics
- View all users, events, and registrations
- Complete system management

## Technology Stack

- **Frontend Framework**: React 18
- **Routing**: React Router DOM
- **HTTP Client**: Axios
- **Styling**: Bootstrap 5
- **State Management**: React Context API

## Setup Instructions

### Prerequisites
- Node.js 16 or higher
- npm or yarn

### Installation

1. Navigate to the Frontend directory:
```bash
cd Frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```

The application will start on `http://localhost:3000`

### Backend Connection

The frontend is configured to proxy API requests to the backend server running on `http://localhost:8080`. Make sure the backend is running before starting the frontend.

## Project Structure

```
Frontend/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   ├── Navbar.js
│   │   └── ProtectedRoute.js
│   ├── context/
│   │   └── AuthContext.js
│   ├── pages/
│   │   ├── Home.js
│   │   ├── Login.js
│   │   ├── Register.js
│   │   ├── EmailVerification.js
│   │   ├── Events.js
│   │   ├── StudentDashboard.js
│   │   ├── FacultyDashboard.js
│   │   └── AdminDashboard.js
│   ├── App.js
│   ├── index.js
│   └── index.css
└── package.json
```

## Key Components

### AuthContext
Manages user authentication state, login/logout functionality, and JWT token handling.

### ProtectedRoute
Wrapper component that protects routes based on authentication and user roles.

### Navbar
Navigation component with role-based menu items and user information.

## Available Scripts

- `npm start` - Runs the app in development mode
- `npm build` - Builds the app for production
- `npm test` - Launches the test runner
- `npm eject` - Ejects from Create React App (one-way operation)

## API Integration

The frontend communicates with the backend through REST API endpoints:

- Authentication: `/api/auth/*`
- Student endpoints: `/api/student/*`
- Faculty endpoints: `/api/faculty/*`
- Admin endpoints: `/api/admin/*`

## Styling

The application uses Bootstrap 5 for responsive design and styling. Custom CSS is added in `index.css` for additional styling.

## Development Notes

- JWT tokens are stored in localStorage
- Axios interceptors handle authentication headers
- Role-based routing ensures users only access appropriate pages
- Form validation is implemented for user inputs
- Error handling provides user-friendly messages

## Future Enhancements

- Event registration modal with form validation
- File upload for event posters
- Real-time notifications
- Advanced search and filtering
- Export functionality for reports
- Email templates customization
- Mobile app version

## Testing

To run tests:
```bash
npm test
```

## Building for Production

To create a production build:
```bash
npm run build
```

This creates a `build` folder with optimized production files.

## Deployment

The built application can be deployed to any static hosting service like:
- Netlify
- Vercel
- AWS S3 + CloudFront
- GitHub Pages

Make sure to update the API base URL for production deployment.