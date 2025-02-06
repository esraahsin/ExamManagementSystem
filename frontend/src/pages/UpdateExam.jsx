import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import './UpdateExam.css';

// Liste des matières filtrées par département
const subjectsByDepartment = {
    Informatique: ['Programmation Java', 'Algorithmique', 'Bases de données', 'Systèmes d’exploitation'],
    Mathématiques: ['Analyse', 'Géométrie', 'Probabilités', 'Algèbre'],
    'Génie Logiciel': ['Développement web', 'Architecture logicielle', 'Tests logiciels'],
    'Data Science et Analyse de Données': ['Apprentissage automatique', 'Statistiques', 'Analyse de données'],
    'Réseaux et Télécommunications': ['Réseaux informatiques', 'Systèmes de communication', 'Sécurité des réseaux'],
};

const UpdateExam = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [exam] = useState(location.state?.exam || {});
    const [departments] = useState(Object.keys(subjectsByDepartment));
    const [selectedDepartment, setSelectedDepartment] = useState(exam.departmentName || '');
    const [selectedSubject, setSelectedSubject] = useState(exam.subject || '');
    const [coefficient, setCoefficient] = useState(exam.coefficient || 1);
    const [difficulty, setDifficulty] = useState(exam.difficulty || 1);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        if (selectedDepartment) {
            const subjects = subjectsByDepartment[selectedDepartment];
            if (!subjects.includes(selectedSubject)) {
                setSelectedSubject(subjects[0]);
            }
        }
    }, [selectedDepartment, selectedSubject]);

    const handleUpdate = async (e) => {
        e.preventDefault(); // Empêche le rechargement de la page lors de la soumission du formulaire

        if (!selectedDepartment || !selectedSubject) {
            setError('Veuillez sélectionner un département et une matière.');
            return;
        }

        try {
            const updatedExam = {
                ...exam,
                departmentName: selectedDepartment,
                subject: selectedSubject,
                coefficient,
                difficulty,
            };
            await axios.put(`http://localhost:8080/api/admin/exams/${exam.examId}`, updatedExam);
            setSuccess('Examen mis à jour avec succès !');
            setTimeout(() => {
                navigate('/admin/exams');
            }, 2000); // Redirige après 2 secondes
        } catch (error) {
            console.error('Error updating exam:', error);
            setError('Erreur lors de la mise à jour de l\'examen');
        }
    };

    return (
        <div className="update-exam-container">
            <h1>Modifier l'examen</h1>
            {error && <div className="error-message">{error}</div>}
            {success && <div className="success-message">{success}</div>}
            <form className="update-exam-form" onSubmit={handleUpdate}>
                <div className="form-group">
                    <label>Département :</label>
                    <select
                        value={selectedDepartment}
                        onChange={(e) => setSelectedDepartment(e.target.value)}
                    >
                        <option value="">Sélectionner un département</option>
                        {departments.map((dept) => (
                            <option key={dept} value={dept}>
                                {dept}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label>Matière :</label>
                    <select
                        value={selectedSubject}
                        onChange={(e) => setSelectedSubject(e.target.value)}
                        disabled={!selectedDepartment}
                    >
                        {selectedDepartment && subjectsByDepartment[selectedDepartment].map((subject) => (
                            <option key={subject} value={subject}>
                                {subject}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
    <label>Coefficient :</label>
    <div className="input-with-arrows">
        <span className="arrow" onClick={() => setCoefficient(coefficient > 1 ? coefficient - 1 : coefficient)}>
            &#9664; {/* Flèche gauche */}
        </span>
        <span>{coefficient}</span>
        <span className="arrow" onClick={() => setCoefficient(coefficient + 1)}>
            &#9654; {/* Flèche droite */}
        </span>
    </div>
</div>

<div className="form-group">
    <label>Difficulté :</label>
    <div className="input-with-arrows">
        <span className="arrow" onClick={() => setDifficulty(difficulty > 1 ? difficulty - 1 : difficulty)}>
            &#9664; {/* Flèche gauche */}
        </span>
        <span>{difficulty}</span>
        <span className="arrow" onClick={() => setDifficulty(difficulty + 1)}>
            &#9654; {/* Flèche droite */}
        </span>
    </div>
</div>

                <div className="form-group">
                    <button type="submit">Mettre à jour</button>
                </div>
            </form>
        </div>
    );
};

export default UpdateExam;
