import { Link } from 'react-router-dom';
import './Auth.css';

export const AuthForm = ({ title, onSubmit, children }) => {
  return (
    <div className="auth-container">
      <div className="auth-form">
        <h2 className="auth-title">{title}</h2>
        <form onSubmit={onSubmit}>
          <div className="auth-input-group">
            {children}
          </div>
        </form>
      </div>
    </div>
  );
};

export const AuthLink = ({ to, text }) => (
  <div className="auth-link">
    <Link to={to}>{text}</Link>
  </div>
);