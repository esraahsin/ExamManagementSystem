import { useState } from "react";
import axios from "axios";

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
      setExam({ examId: "", subject: "", department: "", examDate: "", startTime: "", endTime: "", difficulty: "", coefficient: "" });
    } catch (error) {
      console.error("Error adding exam", error);
      alert("Failed to add exam");
    }
  };

  return (
    <div className="max-w-lg mx-auto mt-10 p-6 bg-white shadow-lg rounded-lg">
      <h2 className="text-xl font-semibold mb-4">Add New Exam</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Change input type to "number" for examId */}
        <input 
          type="number" 
          name="examId" 
          value={exam.examId} 
          onChange={handleChange} 
          placeholder="Exam ID" 
          className="w-full p-2 border rounded" 
          min="1" 
          required 
        />
        {/* Keep other fields unchanged */}
        <input type="text" name="subject" value={exam.subject} onChange={handleChange} placeholder="Subject" className="w-full p-2 border rounded" required />
        <input type="text" name="department" value={exam.department} onChange={handleChange} placeholder="Department" className="w-full p-2 border rounded" required />
        <input type="date" name="examDate" value={exam.examDate} onChange={handleChange} className="w-full p-2 border rounded" required />
        <input type="time" name="startTime" value={exam.startTime} onChange={handleChange} className="w-full p-2 border rounded" required />
        <input type="time" name="endTime" value={exam.endTime} onChange={handleChange} className="w-full p-2 border rounded" required />
        <input type="number" name="difficulty" value={exam.difficulty} onChange={handleChange} placeholder="Difficulty" className="w-full p-2 border rounded" required />
        <input type="number" name="coefficient" value={exam.coefficient} onChange={handleChange} placeholder="Coefficient" className="w-full p-2 border rounded" required />
        <button type="submit" className="w-full p-2 bg-blue-500 text-white rounded">Add Exam</button>
      </form>
    </div>
  );
}