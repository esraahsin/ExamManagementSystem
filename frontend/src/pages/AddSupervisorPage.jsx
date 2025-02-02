import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';

const AddSupervisorPage = () => {
    const { state } = useLocation();
    const { exam } = state;  // Récupérer les informations de l'examen
    const [rooms, setRooms] = useState([]);
    const [selectedSupervisor, setSelectedSupervisor] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const handleAddSupervisor = async () => {
        // Vérifier que la salle et le surveillant sont valides
        if (!selectedSupervisor) {
            alert('Please select  supervisor 5');
            return;
        }

        // Logique pour ajouter le surveillant à l'examen et l'assigner à une salle
        try {
            await axios.post(`http://localhost:8080/api/exams/${exam.examId}/supervisor`, {
                supervisorId: selectedSupervisor,
                
            });
            alert('Supervisor added successfully');
            navigate('/admin/exams');
        } catch (error) {
            console.error('Error adding supervisor:', error);
            alert('Failed to add supervisor.');
        }
    };

    if (error) return <div>Error: {error}</div>;

    return (
        <div>
            <h2>Assign Supervisor to Exam: {exam.subject}</h2>
            
            {/* Sélection du surveillant */}
            <label>Select Supervisor:</label>
            <select
                value={selectedSupervisor}
                onChange={(e) => setSelectedSupervisor(e.target.value)}
            >
                <option value="">-- Select Supervisor --</option>
                {/* Remplir avec la liste des surveillants */}
                {/* Exemple fictif de surveillants */}
                <option value="1">Supervisor 1</option>
                <option value="2">Supervisor 2</option>
            </select>
            
            
            
            <button onClick={handleAddSupervisor}>Add Supervisor</button>
        </div>
    );
};

export default AddSupervisorPage;
