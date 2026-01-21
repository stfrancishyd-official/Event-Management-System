import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';

const AdminDashboard = () => {
  const [stats, setStats] = useState({});
  const [events, setEvents] = useState([]);
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const { user } = useAuth();

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      const [statsResponse, eventsResponse, usersResponse] = await Promise.all([
        axios.get('/api/admin/stats'),
        axios.get('/api/admin/events'),
        axios.get('/api/admin/users')
      ]);
      
      setStats(statsResponse.data);
      setEvents(eventsResponse.data);
      setUsers(usersResponse.data);
    } catch (error) {
      console.error('Error fetching dashboard data:', error);
    } finally {
      setLoading(false);
    }
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
      <div className="row">
        <div className="col-12">
          <h2 className="mb-4">Admin Dashboard</h2>
          
          <div className="row mb-4">
            <div className="col-md-3">
              <div className="card bg-primary text-white">
                <div className="card-body">
                  <h5 className="card-title">Total Users</h5>
                  <h3>{stats.totalUsers || 0}</h3>
                </div>
              </div>
            </div>
            
            <div className="col-md-3">
              <div className="card bg-success text-white">
                <div className="card-body">
                  <h5 className="card-title">Total Events</h5>
                  <h3>{stats.totalEvents || 0}</h3>
                </div>
              </div>
            </div>
            
            <div className="col-md-3">
              <div className="card bg-info text-white">
                <div className="card-body">
                  <h5 className="card-title">Total Registrations</h5>
                  <h3>{stats.totalRegistrations || 0}</h3>
                </div>
              </div>
            </div>
            
            <div className="col-md-3">
              <div className="card bg-warning text-white">
                <div className="card-body">
                  <h5 className="card-title">Active Events</h5>
                  <h3>{events.filter(event => event.isActive).length}</h3>
                </div>
              </div>
            </div>
          </div>
          
          <div className="row">
            <div className="col-md-6">
              <div className="card">
                <div className="card-header">
                  <h5 className="mb-0">Recent Events</h5>
                </div>
                <div className="card-body">
                  {events.length === 0 ? (
                    <p>No events found.</p>
                  ) : (
                    <div className="table-responsive">
                      <table className="table table-sm">
                        <thead>
                          <tr>
                            <th>Title</th>
                            <th>Created By</th>
                            <th>Registrations</th>
                          </tr>
                        </thead>
                        <tbody>
                          {events.slice(0, 5).map((event) => (
                            <tr key={event.id}>
                              <td>{event.title}</td>
                              <td>{event.createdByName}</td>
                              <td>{event.currentParticipants}</td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </div>
                  )}
                </div>
              </div>
            </div>
            
            <div className="col-md-6">
              <div className="card">
                <div className="card-header">
                  <h5 className="mb-0">User Statistics</h5>
                </div>
                <div className="card-body">
                  {users.length === 0 ? (
                    <p>No users found.</p>
                  ) : (
                    <div>
                      <p><strong>Students:</strong> {users.filter(u => u.role === 'STUDENT').length}</p>
                      <p><strong>Faculty:</strong> {users.filter(u => u.role === 'FACULTY').length}</p>
                      <p><strong>Admins:</strong> {users.filter(u => u.role === 'ADMIN').length}</p>
                      <p><strong>Verified Users:</strong> {users.filter(u => u.verified).length}</p>
                    </div>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;