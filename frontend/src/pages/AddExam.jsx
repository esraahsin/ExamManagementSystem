import { useState } from "react";
import axios from "axios";
import "./AddExam.css"; // Importez le fichier CSS
import { Link } from "react-router-dom"; // Ajoutez cette ligne

export default function AddExam() {
  const [exam, setExam] = useState({
    examId: "", 
    subject: "",
    department: "",
    examDate: "",
    startTime: "",
    endTime: "",
    difficulty: "",
    coefficient: "",
    speciality: "" // Nouveau champ
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    // Parse examId, difficulty, and coefficient to integers
    const parsedValue = ["examId", "difficulty", "coefficient"].includes(name) 
      ? parseInt(value, 10) || "" // Handle invalid numbers gracefully
      : value;
    setExam({ ...exam, [name]: parsedValue });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/api/admin/exams", exam);
      alert("Exam added successfully!");
      setExam({ 
        examId: "", 
        subject: "", 
        department: "", 
        examDate: "", 
        startTime: "", 
        endTime: "", 
        difficulty: "", 
        coefficient: "",
        speciality: "" // Réinitialiser le champ
      });
    } catch (error) {
      console.error("Error adding exam", error);
      alert("Failed to add exam");
    }
  };

  return (
    <div className="add-exam-container">
      <h2 className="add-exam-title">Add New Exam</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Exam ID */}
        <input 
          type="number" 
          name="examId" 
          value={exam.examId} 
          onChange={handleChange} 
          placeholder="Exam ID" 
          className="add-exam-input" 
          min="1" 
          required 
        />
        {/* Subject */}
        <input 
          type="text" 
          name="subject" 
          value={exam.subject} 
          onChange={handleChange} 
          placeholder="Subject" 
          className="add-exam-input" 
          required 
        />
        {/* Department */}
        <input 
          type="text" 
          name="department" 
          value={exam.department} 
          onChange={handleChange} 
          placeholder="Department" 
          className="add-exam-input" 
          required 
        />
        {/* Exam Date */}
        <input 
          type="date" 
          name="examDate" 
          value={exam.examDate} 
          onChange={handleChange} 
          className="add-exam-input" 
          required 
        />
        {/* Start Time */}
        <input 
          type="time" 
          name="startTime" 
          value={exam.startTime} 
          onChange={handleChange} 
          className="add-exam-input" 
          required 
        />
        {/* End Time */}
        <input 
          type="time" 
          name="endTime" 
          value={exam.endTime} 
          onChange={handleChange} 
          className="add-exam-input" 
          required 
        />
        {/* Difficulty */}
        <input 
          type="number" 
          name="difficulty" 
          value={exam.difficulty} 
          onChange={handleChange} 
          placeholder="Difficulty" 
          className="add-exam-input" 
          required 
        />
        {/* Coefficient */}
        <input 
          type="number" 
          name="coefficient" 
          value={exam.coefficient} 
          onChange={handleChange} 
          placeholder="Coefficient" 
          className="add-exam-input" 
          required 
        />
        {/* Speciality */}
        <input 
          type="text" 
          name="speciality" 
          value={exam.speciality} 
          onChange={handleChange} 
          placeholder="Speciality (e.g., Computer Science, TIC)" 
          className="add-exam-input" 
          required 
        />
        <div className="button-group">
          <button type="submit" className="add-exam-button">
            Add Exam
          </button>
          <Link to="/admin/exams" className="cancel-button">
            Cancel
          </Link>
          </div>
      </form>
    </div>
  );
}