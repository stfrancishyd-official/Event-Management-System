import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';

const StudentDashboard = () => {
  const [registrations, setRegistrations] = useState([]);
  const [loading, setLoading] = useState(true);
  const { user } = useAuth();

  useEffect(() => {
    fetchRegistrations();
  }, []);

  const fetchRegistrations = async () => {
    try {
      const response = await axios.get('/api/student/my-registrations');
      setRegistrations(response.data);
    } catch (error) {
      console.error('Error fetching registrations:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-4">
      <div className="row">
        <div className="col-12">
          <h2 className="mb-4">Student Dashboard</h2>
          
          <div className="row mb-4">
            <div className="col-md-4">
              <div className="card bg-primary text-white">
                <div className="card-body">
                  <h5 className="card-title">Welcome, {user.name}!</h5>
                  <p className="card-text">
                    {user.department && `Department: ${user.department}`}
                  </p>
                </div>
              </div>
            </div>
            
            <div className="col-md-4">
              <div className="card bg-success text-white">
                <div className="card-body">
                  <h5 className="card-title">Registered Events</h5>
                  <h3>{registrations.length}</h3>
                </div>
              </div>
            </div>
            
            <div className="col-md-4">
              <Link to="/events" className="text-decoration-none">
                <div className="card bg-info text-white">
                  <div className="card-body">
                    <h5 className="card-title">Browse Events</h5>
                    <p className="card-text">Find new events to register</p>
                  </div>
                </div>
              </Link>
            </div>
          </div>
          
          <div className="card">
            <div className="card-header">
              <h5 className="mb-0">My Registrations</h5>
            </div>
            <div className="card-body">
              {loading ? (
                <div className="text-center">
                  <div className="spinner-border" role="status">
                    <span className="visually-hidden">Loading...</span>
                  </div>
                </div>
              ) : registrations.length === 0 ? (
                <div className="text-center">
                  <p>You haven't registered for any events yet.</p>
                  <Link to="/events" className="btn btn-primary">
                    Browse Events
                  </Link>
                </div>
              ) : (
                <div className="table-responsive">
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th>Event Title</th>
                        <th>Date</th>
                        <th>Department</th>
                        <th>Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      {registrations.map((event) => (
                        <tr key={event.id}>
                          <td>{event.title}</td>
                          <td>{new Date(event.eventDate).toLocaleDateString()}</td>
                          <td>{event.department}</td>
                          <td>
                            <span className="badge bg-success">Registered</span>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentDashboard;