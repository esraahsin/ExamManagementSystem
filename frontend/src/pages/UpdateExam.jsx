import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";

const UpdateExam = () => {
    const { id } = useParams(); // Récupérer l'ID de l'examen depuis l'URL
    const navigate = useNavigate();

    // État pour stocker les données de l'examen
    const [exam, setExam] = useState({
        subject: "",
        department_id: "",
        exam_date: "",
        start_time: "",
        end_time: "",
        difficulty: 1,
        coefficient: 1,
        is_duplicate: false,
    });

    // Options pour les listes déroulantes
    const subjects = ["Mathématiques", "Informatique", "Physique", "Chimie"]; // Exemple de matières
    const departments = ["Informatique", "Mathematics", "Biologie"]; // Exemple de départements
    const difficulties = [1, 2, 3, 4, 5]; // Difficulté de 1 à 5
    const coefficients = [1, 2, 3, 4, 5]; // Coefficients possibles

    // Charger les données de l'examen à mettre à jour
    useEffect(() => {
        const fetchExam = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/admin/exams/${id}`);
                const examData = response.data;
                setExam({
                    subject: examData.subject,
                    department_id: examData.department_id,
                    exam_date: examData.exam_date,
                    start_time: examData.start_time,
                    end_time: examData.end_time,
                    difficulty: examData.difficulty,
                    coefficient: examData.coefficient,
                    is_duplicate: examData.is_duplicate,
                });
            } catch (error) {
                console.error("Erreur lors de la récupération de l'examen :", error);
            }
        };

        fetchExam();
    }, [id]);

    // Gérer les changements dans les champs du formulaire
    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setExam({
            ...exam,
            [name]: type === "checkbox" ? checked : value,
        });
    };

    // Soumettre le formulaire de mise à jour
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:8080/api/admin/exams/${id}`, exam);
            alert("Examen mis à jour avec succès !");
            navigate("/exams"); // Rediriger vers la liste des examens
        } catch (error) {
            console.error("Erreur lors de la mise à jour de l'examen :", error);
            alert("Une erreur s'est produite lors de la mise à jour de l'examen.");
        }
    };

    return (
        <div className="container">
            <h2>Modifier l'examen</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Matière :</label>
                    <select
                        name="subject"
                        value={exam.subject}
                        onChange={handleChange}
                        className="form-control"
                        required
                    >
                        {subjects.map((subject, index) => (
                            <option key={index} value={subject}>
                                {subject}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label>Département :</label>
                    <select
                        name="department_id"
                        value={exam.department_id}
                        onChange={handleChange}
                        className="form-control"
                        required
                    >
                        {departments.map((department, index) => (
                            <option key={index} value={department}>
                                {department}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label>Date de l'examen :</label>
                    <input
                        type="date"
                        name="exam_date"
                        value={exam.exam_date}
                        onChange={handleChange}
                        className="form-control"
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Heure de début :</label>
                    <input
                        type="time"
                        name="start_time"
                        value={exam.start_time}
                        onChange={handleChange}
                        className="form-control"
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Heure de fin :</label>
                    <input
                        type="time"
                        name="end_time"
                        value={exam.end_time}
                        onChange={handleChange}
                        className="form-control"
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Difficulté :</label>
                    <select
                        name="difficulty"
                        value={exam.difficulty}
                        onChange={handleChange}
                        className="form-control"
                        required
                    >
                        {difficulties.map((difficulty, index) => (
                            <option key={index} value={difficulty}>
                                {difficulty}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label>Coefficient :</label>
                    <select
                        name="coefficient"
                        value={exam.coefficient}
                        onChange={handleChange}
                        className="form-control"
                        required
                    >
                        {coefficients.map((coefficient, index) => (
                            <option key={index} value={coefficient}>
                                {coefficient}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label>
                        <input
                            type="checkbox"
                            name="is_duplicate"
                            checked={exam.is_duplicate}
                            onChange={handleChange}
                        />
                        Est une copie ?
                    </label>
                </div>

                <button type="submit" className="btn btn-primary">
                    Mettre à jour
                </button>
            </form>
        </div>
    );
};

export default UpdateExam;
