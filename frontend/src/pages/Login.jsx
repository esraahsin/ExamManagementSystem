// LoginPage.jsx
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { AuthForm, AuthLink } from './Auth';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', {
        email,
        password
      });
      
      localStorage.setItem('token', response.data.token);
      navigate('/exams');
    } catch (err) {
      setError(err.response?.data?.message || 'Login failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
    <AuthForm title="Sign in to your account" onSubmit={handleSubmit}>
      <div className="auth-input-group">
        <label className="auth-label">Email address</label>
        <input
          className="auth-input"
          type="email"
          name="email"
          autoComplete="email"
          required
          placeholder="Email address"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
      </div>

<div className="auth-input-group">

      
<label className="auth-label">Password</label>

        <input
          id="password"
          name="password"
          type="password"
          autoComplete="current-password"
          required
          className="auth-input"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
</div>
      {error && <p className="text-red-500 text-sm">{error}</p>}

      <button className="auth-button" type="submit">
          {loading ? 'Signing in...' : 'Sign in'}
        </button>

        <AuthLink to="/signup" text="Don't have an account? Sign up" />
      </AuthForm>
    </div>
  );
};

export default LoginPage;