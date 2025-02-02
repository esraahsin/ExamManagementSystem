import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { AuthForm, AuthLink } from './Auth';
import "./Signup.css";

const SignupPage = () => {
  const [departments, setDepartments] = useState([]);
  const [userData, setUserData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: 'etudiant',
    departmentId: '', // Changed from specialty to departmentId
    specialty: '', // Added specialty field
    userId: '' // Added user_id field
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // Fetch departments from backend
  useEffect(() => {
    const fetchDepartments = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/departments');
        setDepartments(response.data);
      } catch (err) {
        console.error('Error fetching departments:', err);
        setDepartments([]); // Fallback to empty array
      }
    };
    fetchDepartments();
  }, []);

  const handleChange = (e) => {
    setUserData({
      ...userData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate password match
    if (userData.password !== userData.confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    // Validate user_id (8 digits)
    if (!/^\d{8}$/.test(userData.userId)) {
      setError('User ID must be exactly 8 digits');
      return;
    }

    // Validate student department and specialty
    if (userData.role === 'etudiant') {
      if (!userData.departmentId) {
        setError('Department is required for students');
        return;
      }
      if (!userData.specialty.trim()) {
        setError('Specialty is required for students');
        return;
      }
    }

    setLoading(true);
    setError('');

    try {
      const payload = {
        name: userData.name,
        email: userData.email,
        password: userData.password,
        role: userData.role.toUpperCase(), // Ensure uppercase to match backend enum
        departmentId: userData.role === 'etudiant' ? parseInt(userData.departmentId) : null,
        specialty: userData.role === 'etudiant' ? userData.specialty.trim() : null, // Include specialty for students
        userId: userData.userIdd // Include user_id in the payload
      };

      await axios.post('http://localhost:8080/api/auth/signup', payload);
      navigate('/login');
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <div className="signup-container">
        <AuthForm title="Create New Account" onSubmit={handleSubmit}>
          {/* Name Field */}
          <div className="auth-input-group">
            <label className="auth-label">Full Name</label>
            <input
              className="auth-input"
              type="text"
              name="name"
              placeholder="Full name"
              value={userData.name}
              onChange={handleChange}
              required
            />
          </div>

          {/* Email Field */}
          <div className="auth-input-group">
            <label className="auth-label">Email Address</label>
            <input
              className="auth-input"
              type="email"
              name="email"
              placeholder="Email address"
              value={userData.email}
              onChange={handleChange}
              required
            />
          </div>

          {/* User ID Field */}
          <div className="auth-input-group">
            <label className="auth-label">User ID (8 digits)</label>
            <input
              className="auth-input"
              type="text"
              name="userId"
              placeholder="e.g., 12345678"
              value={userData.userId}
              onChange={handleChange}
              required
            />
          </div>

          {/* Role Selection */}
          <div className="auth-input-group">
            <label className="auth-label">Account Type</label>
            <select
              className="auth-input"
              name="role"
              value={userData.role}
              onChange={handleChange}
              required
            >
              <option value="etudiant">Student</option>
              <option value="enseignant">Teacher</option>
            </select>
          </div>

          {/* Department Field (for students) */}
          {userData.role === 'etudiant' && (
            <div className="auth-input-group">
              <label className="auth-label">Department</label>
              <select
                className="auth-input"
                name="departmentId"
                value={userData.departmentId}
                onChange={handleChange}
                required
              >
                <option value="">Select Department</option>
                {departments.length > 0 ? (
                  departments.map(dept => (
                    <option key={dept.departmentId} value={dept.departmentId}>
                      {dept.name}
                    </option>
                  ))
                ) : (
                  <option value="" disabled>No departments available</option>
                )}
              </select>
            </div>
          )}

          {/* Specialty Field (for students) */}
          {userData.role === 'etudiant' && (
            <div className="auth-input-group">
              <label className="auth-label">Specialty</label>
              <input
                className="auth-input"
                type="text"
                name="specialty"
                placeholder="e.g., CPI2, ING INFO1..."
                value={userData.specialty}
                onChange={handleChange}
                required
              />
            </div>
          )}

          {/* Password Fields */}
          <div className="auth-input-group">
            <label className="auth-label">Password</label>
            <input
              className="auth-input"
              type="password"
              name="password"
              placeholder="Password"
              value={userData.password}
              onChange={handleChange}
              required
            />
          </div>
          <div className="auth-input-group">
            <label className="auth-label">Confirm Password</label>
            <input
              className="auth-input"
              type="password"
              name="confirmPassword"
              placeholder="Confirm Password"
              value={userData.confirmPassword}
              onChange={handleChange}
              required
            />
          </div>

          {/* Error Messages */}
          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          <button
            type="submit"
            className="auth-button"
            disabled={loading}
          >
            {loading ? 'Creating Account...' : 'Create Account'}
          </button>
          <AuthLink 
            to="/login" 
            text="Already have an account? Sign in" 
          />
        </AuthForm>
      </div>
    </div>
  );
};

export default SignupPage;