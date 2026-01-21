import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';

const FacultyDashboard = () => {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const { user } = useAuth();

  useEffect(() => {
    fetchMyEvents();
  }, []);

  const fetchMyEvents = async () => {
    try {
      const response = await axios.get('/api/faculty/my-events');
      setEvents(response.data);
    } catch (error) {
      console.error('Error fetching events:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteEvent = async (eventId) => {
    if (window.confirm('Are you sure you want to delete this event?')) {
      try {
        await axios.delete(`/api/faculty/events/${eventId}`);
        fetchMyEvents(); // Refresh the list
      } catch (error) {
        alert('Failed to delete event');
      }
    }
  };

  return (
    <div className="container mt-4">
      <div className="row">
        <div className="col-12">
          <h2 className="mb-4">Faculty Dashboard</h2>
          
          <div className="row mb-4">
            <div className="col-md-4">
              <div className="card bg-primary text-white">
                <div className="card-body">
                  <h5 className="card-title">Welcome, {user.name}!</h5>
                  <p className="card-text">
                    Department: {user.department}
                  </p>
                </div>
              </div>
            </div>
            
            <div className="col-md-4">
              <div className="card bg-success text-white">
                <div className="card-body">
                  <h5 className="card-title">My Events</h5>
                  <h3>{events.length}</h3>
                </div>
              </div>
            </div>
            
            <div className="col-md-4">
              <div className="card bg-info text-white">
                <div className="card-body">
                  <h5 className="card-title">Total Registrations</h5>
                  <h3>{events.reduce((sum, event) => sum + event.currentParticipants, 0)}</h3>
                </div>
              </div>
            </div>
          </div>
          
          <div className="card">
            <div className="card-header d-flex justify-content-between align-items-center">
              <h5 className="mb-0">My Events</h5>
              <button className="btn btn-primary">
                Create New Event
              </button>
            </div>
            <div className="card-body">
              {loading ? (
                <div className="text-center">
                  <div className="spinner-border" role="status">
                    <span className="visually-hidden">Loading...</span>
                  </div>
                </div>
              ) : events.length === 0 ? (
                <div className="text-center">
                  <p>You haven't created any events yet.</p>
                  <button className="btn btn-primary">
                    Create Your First Event
                  </button>
                </div>
              ) : (
                <div className="table-responsive">
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th>Title</th>
                        <th>Date</th>
                        <th>Registrations</th>
                        <th>Status</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {events.map((event) => (
                        <tr key={event.id}>
                          <td>{event.title}</td>
                          <td>{new Date(event.eventDate).toLocaleDateString()}</td>
                          <td>{event.currentParticipants}/{event.maxParticipants}</td>
                          <td>
                            <span className={`badge ${event.isActive ? 'bg-success' : 'bg-secondary'}`}>
                              {event.isActive ? 'Active' : 'Inactive'}
                            </span>
                          </td>
                          <td>
                            <button className="btn btn-sm btn-outline-primary me-2">
                              View Registrations
                            </button>
                            <button className="btn btn-sm btn-outline-secondary me-2">
                              Edit
                            </button>
                            <button 
                              className="btn btn-sm btn-outline-danger"
                              onClick={() => handleDeleteEvent(event.id)}
                            >
                              Delete
                            </button>
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

export default FacultyDashboard;