import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';

const Events = () => {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const { user } = useAuth();

  useEffect(() => {
    fetchEvents();
  }, []);

  const fetchEvents = async () => {
    try {
      const response = await axios.get('/api/student/events');
      setEvents(response.data);
    } catch (error) {
      setError('Failed to fetch events');
      console.error('Error fetching events:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async (eventId) => {
    // This would open a modal or navigate to registration form
    // For now, just show an alert
    alert('Registration form would open here');
  };

  if (loading) {
    return (
      <div className="container mt-5">
        <div className="text-center">
          <div className="spinner-border" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Available Events</h2>
      
      {error && (
        <div className="alert alert-danger" role="alert">
          {error}
        </div>
      )}
      
      {events.length === 0 ? (
        <div className="alert alert-info" role="alert">
          No events available at the moment.
        </div>
      ) : (
        <div className="row">
          {events.map((event) => (
            <div key={event.id} className="col-md-6 col-lg-4 mb-4">
              <div className="card event-card h-100">
                {event.posterUrl && (
                  <img 
                    src={event.posterUrl} 
                    className="card-img-top" 
                    alt={event.title}
                    style={{ height: '200px', objectFit: 'cover' }}
                  />
                )}
                <div className="card-body d-flex flex-column">
                  <h5 className="card-title">{event.title}</h5>
                  <p className="card-text flex-grow-1">{event.description}</p>
                  
                  <div className="mb-2">
                    <small className="text-muted">
                      <strong>Date:</strong> {new Date(event.eventDate).toLocaleDateString()}
                    </small>
                  </div>
                  
                  <div className="mb-2">
                    <small className="text-muted">
                      <strong>Department:</strong> {event.department}
                    </small>
                  </div>
                  
                  <div className="mb-3">
                    <small className="text-muted">
                      <strong>Participants:</strong> {event.currentParticipants}/{event.maxParticipants}
                    </small>
                  </div>
                  
                  {user && user.role === 'STUDENT' && (
                    <button 
                      className={`btn ${event.isRegistered ? 'btn-success' : 'btn-primary'} w-100`}
                      onClick={() => handleRegister(event.id)}
                      disabled={event.isRegistered || event.currentParticipants >= event.maxParticipants}
                    >
                      {event.isRegistered ? 'Registered' : 
                       event.currentParticipants >= event.maxParticipants ? 'Full' : 'Register'}
                    </button>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Events;