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
      // Étape 1 : Authentification de l'utilisateur
      console.log('Envoi de la requête pour l\'authentification');

      const authResponse = await axios.post('http://localhost:8080/api/auth/login', {
        email,
        password,
      });


      console.log('Auth Response:', authResponse.data); // Log de la réponse de l'API

      const { token, user } = authResponse.data;

      // Vérifier que user.userId est valide
      const userId = Number(user.userId);
      if (!user.userId || isNaN(userId) || userId <= 0) {
        console.error("Erreur : userId est invalide !", user.userId);
        setError("Erreur interne : ID utilisateur manquant ou invalide.");
        return;
      }

      console.log("User ID envoyé :", userId, "Type :", typeof userId);

      // Stocker le token et les informations de l'utilisateur dans le localStorage
      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(user));

      // Vérifier le rôle de l'utilisateur et naviguer en conséquence
      switch (user.role) {
        case 'ADMIN':
          navigate('/exams');
          break;

        case 'TEACHER':
          console.log('Fetching invigilations for teacher...'); // Log pour vérifier
          const invigilatorResponse = await axios.get(
            `http://localhost:8080/api/invigilators?user_id=${userId}`,
            {
              headers: { Authorization: `Bearer ${token}` },
            }
          );

          console.log('Invigilator Response:', invigilatorResponse.data); // Log pour vérifier

          const invigilations = invigilatorResponse.data;

          // Étape 3 : Récupérer les détails des examens et des salles
          const schedule = await Promise.all(
            invigilations.map(async (invigilation) => {
              // Récupérer les détails de l'examen
              const examResponse = await axios.get(
                `http://localhost:8080/api/exams/${invigilation.exam_id}`,
                {
                  headers: { Authorization: `Bearer ${token}` },
                }
              );

              console.log('Exam Response:', examResponse.data); // Log pour vérifier

              const exam = examResponse.data;

              // Récupérer les détails de la salle
              const roomResponse = await axios.get(
                `http://localhost:8080/api/rooms/${invigilation.room_id}`,
                {
                  headers: { Authorization: `Bearer ${token}` },
                }
              );

              console.log('Room Response:', roomResponse.data); // Log pour vérifier

              const room = roomResponse.data;

              // Retourner les données formatées
              return {
                exam_date: exam.examDate,
                start_time: exam.startTime,
                end_time: exam.endTime,
                subject: exam.subject,
                room_name: room.roomName,
                location: room.location,
              };
            })
          );

          // Rediriger vers la page de l'emploi du temps de l'enseignant avec les données
          navigate('/teacher-schedule', { state: { schedule } });
          break;

        default:
          setError('Rôle non reconnu');
          break;
      }
    
      localStorage.setItem('email', email);
      if (authResponse.data.role === 'ETUDIANT') {
        navigate('/student/exam');
      } 
      else {
        navigate('/exams');
        }

    } catch (err) {
      console.error('Error:', err); // Log de l'erreur
      setError(err.response?.data?.message || 'Une erreur s\'est produite lors de la connexion.');
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