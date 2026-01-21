import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Home = () => {
  const { user, getDashboardRoute } = useAuth();

  return (
    <div className="container mt-5">
      <div className="row">
        <div className="col-lg-8 mx-auto text-center">
          <h1 className="display-4 mb-4">Welcome to Event Registration System</h1>
          <p className="lead mb-5">
            Manage and register for college events seamlessly. Students can browse and register for events, 
            while faculty can create and manage their events with detailed registration reports.
          </p>
          
          {user ? (
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Welcome back, {user.name}!</h5>
                <p className="card-text">
                  You are logged in as a {user.role.toLowerCase()}. 
                  Access your dashboard to manage your activities.
                </p>
                <Link to={getDashboardRoute()} className="btn btn-primary btn-lg">
                  Go to Dashboard
                </Link>
              </div>
            </div>
          ) : (
            <div className="row">
              <div className="col-md-6 mb-4">
                <div className="card h-100">
                  <div className="card-body">
                    <h5 className="card-title">New User?</h5>
                    <p className="card-text">
                      Create an account to start registering for events and managing your profile.
                    </p>
                    <Link to="/register" className="btn btn-success">
                      Register Now
                    </Link>
                  </div>
                </div>
              </div>
              
              <div className="col-md-6 mb-4">
                <div className="card h-100">
                  <div className="card-body">
                    <h5 className="card-title">Already have an account?</h5>
                    <p className="card-text">
                      Sign in to access your dashboard and manage your events.
                    </p>
                    <Link to="/login" className="btn btn-primary">
                      Login
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          )}
          
          <div className="row mt-5">
            <div className="col-md-4 mb-4">
              <div className="card">
                <div className="card-body text-center">
                  <h5 className="card-title">For Students</h5>
                  <p className="card-text">
                    Browse events, register with your details, and track your registrations.
                  </p>
                </div>
              </div>
            </div>
            
            <div className="col-md-4 mb-4">
              <div className="card">
                <div className="card-body text-center">
                  <h5 className="card-title">For Faculty</h5>
                  <p className="card-text">
                    Create events, manage registrations, and get detailed reports.
                  </p>
                </div>
              </div>
            </div>
            
            <div className="col-md-4 mb-4">
              <div className="card">
                <div className="card-body text-center">
                  <h5 className="card-title">For Admins</h5>
                  <p className="card-text">
                    Complete system overview with access to all events and users.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;