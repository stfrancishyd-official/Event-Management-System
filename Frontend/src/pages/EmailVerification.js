import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';

const EmailVerification = () => {
  const [otp, setOtp] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [resendLoading, setResendLoading] = useState(false);
  const [resendMessage, setResendMessage] = useState('');
  
  const { verifyEmail, getDashboardRoute } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  
  // Get email from navigation state or prompt user to enter it
  const [email, setEmail] = useState(location.state?.email || '');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    if (!email) {
      setError('Please enter your email address');
      setLoading(false);
      return;
    }

    if (!otp || otp.length !== 6) {
      setError('Please enter a valid 6-digit OTP');
      setLoading(false);
      return;
    }

    const result = await verifyEmail(email, otp);
    
    if (result.success) {
      navigate(getDashboardRoute());
    } else {
      setError(result.message);
    }
    
    setLoading(false);
  };

  const handleResendOTP = async () => {
    if (!email) {
      setError('Please enter your email address first');
      return;
    }

    setResendLoading(true);
    setResendMessage('');
    setError('');

    try {
      await axios.post(`/api/auth/resend-otp?email=${email}`);
      setResendMessage('OTP sent successfully! Please check your email.');
    } catch (error) {
      setError(error.response?.data || 'Failed to resend OTP');
    }
    
    setResendLoading(false);
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6 col-lg-4">
          <div className="card">
            <div className="card-body">
              <h3 className="card-title text-center mb-4">Email Verification</h3>
              <p className="text-center text-muted mb-4">
                Please enter the 6-digit verification code sent to your email.
              </p>
              
              {error && (
                <div className="alert alert-danger" role="alert">
                  {error}
                </div>
              )}
              
              {resendMessage && (
                <div className="alert alert-success" role="alert">
                  {resendMessage}
                </div>
              )}
              
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="email" className="form-label">Email</label>
                  <input
                    type="email"
                    className="form-control"
                    id="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    disabled={location.state?.email} // Disable if email came from registration
                  />
                </div>
                
                <div className="mb-3">
                  <label htmlFor="otp" className="form-label">Verification Code</label>
                  <input
                    type="text"
                    className="form-control text-center"
                    id="otp"
                    value={otp}
                    onChange={(e) => setOtp(e.target.value.replace(/\D/g, '').slice(0, 6))}
                    placeholder="000000"
                    maxLength="6"
                    required
                    style={{ fontSize: '1.5rem', letterSpacing: '0.5rem' }}
                  />
                </div>
                
                <button 
                  type="submit" 
                  className="btn btn-primary w-100 mb-3"
                  disabled={loading}
                >
                  {loading ? 'Verifying...' : 'Verify Email'}
                </button>
              </form>
              
              <div className="text-center">
                <p className="mb-2">Didn't receive the code?</p>
                <button 
                  className="btn btn-outline-secondary"
                  onClick={handleResendOTP}
                  disabled={resendLoading}
                >
                  {resendLoading ? 'Sending...' : 'Resend OTP'}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EmailVerification;