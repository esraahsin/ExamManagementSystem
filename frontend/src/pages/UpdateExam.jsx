import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import "./ExamPage.css";

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
    const [exam] = useState(location.state?.exam || {}); // Suppression de setExam inutilisé
    const [departments] = useState(Object.keys(subjectsByDepartment)); // Suppression de setDepartments inutilisé
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
    }, [selectedDepartment, selectedSubject]); // Ajout de selectedSubject dans les dépendances

    const handleUpdate = async () => {
        if (!selectedDepartment || !selectedSubject) {
            setError("Veuillez sélectionner un département et une matière.");
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
            await axios.put(`http://localhost:8080/api/exams/${exam.examId}`, updatedExam);
            setSuccess("Examen mis à jour avec succès !");
            setTimeout(() => {
                navigate('/admin/exams');
            }, 2000); // Redirige après 2 secondes
        } catch (error) {
            console.error("Error updating exam:", error);
            setError("Erreur lors de la mise à jour de l'examen");
        }
    };

    return (
        <div className="update-exam-container">
            <h1>Modifier l'examen</h1>
            {error && <div className="error-message">{error}</div>}
            {success && <div className="success-message">{success}</div>}
            <form className="update-exam-form">
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
                    <button type="button" onClick={() => setCoefficient(coefficient + 1)}>+</button>
                    <span>{coefficient}</span>
                    <button type="button" onClick={() => setCoefficient(coefficient > 1 ? coefficient - 1 : coefficient)}>-</button>
                </div>

                <div className="form-group">
                    <label>Difficulté :</label>
                    <button type="button" onClick={() => setDifficulty(difficulty + 1)}>+</button>
                    <span>{difficulty}</span>
                    <button type="button" onClick={() => setDifficulty(difficulty > 1 ? difficulty - 1 : difficulty)}>-</button>
                </div>

                <div className="form-group">
                    <button type="button" onClick={handleUpdate}>Mettre à jour</button>
                </div>
            </form>
        </div>
    );
};

export default UpdateExam;