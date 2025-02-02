import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./ExamPage.css";
import { Link } from 'react-router-dom';
import jsPDF from 'jspdf'; // Import jsPDF

const ExamsPage = () => {
    const [exams, setExams] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filters, setFilters] = useState({
        search: '',
        startDate: '',
        endDate: '',
        startTime: '',
        endTime: '',
        department: '' // Add department filter
    });

    useEffect(() => {
        fetchExams();
    }, []);

    const fetchExams = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/exams');
            setExams(Array.isArray(response.data) ? response.data : []);
        } catch (error) {
            console.error('Error fetching exams:', error);
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    // Fonction pour générer le PDF
    const generatePDF = () => {
        try {
            const doc = new jsPDF();
            const pageWidth = doc.internal.pageSize.width;
            let yOffset = 15;
    
            // Style configuration
            const mainColor = '#2c3e50';
            const accentColor = '#3498db';
            const fontSizeLarge = 16;
            const fontSizeMedium = 12;
            const fontSizeSmall = 10;
    
            // Group exams by speciality
            const examsBySpeciality = exams.reduce((acc, exam) => {
                const speciality = exam.speciality || 'No Speciality';
                if (!acc[speciality]) acc[speciality] = [];
                acc[speciality].push(exam);
                return acc;
            }, {});
    
            // Create a page for each speciality
            Object.entries(examsBySpeciality).forEach(([speciality, exams], index) => {
                // Add new page for each speciality except the first
                if (index !== 0) {
                    doc.addPage();
                    yOffset = 15;
                }
    
                // Speciality Header
                doc.setFontSize(fontSizeLarge);
                doc.setTextColor(mainColor);
                doc.text(`Speciality: ${speciality}`, 15, yOffset);
                yOffset += 10;
    
                // Column headers
                doc.setFontSize(fontSizeMedium);
                doc.setTextColor(accentColor);
                doc.text('Date', 15, yOffset);
                doc.text('Subject', 45, yOffset);
                doc.text('Time', 100, yOffset);
                doc.text('Department', 130, yOffset);
                doc.text('Details', 170, yOffset);
                yOffset += 8;
    
                // Horizontal line
                doc.setDrawColor(mainColor);
                doc.line(15, yOffset, pageWidth - 15, yOffset);
                yOffset += 10;
    
                // Exams list
                doc.setFontSize(fontSizeSmall);
                doc.setTextColor('#000000');
    
                exams.forEach((exam, examIndex) => {
                    // Check page height
                    if (yOffset > 250) {
                        doc.addPage();
                        yOffset = 15;
                    }
    
                    // Ensure all fields have valid values
                    const examDate = exam.examDate ? new Date(exam.examDate).toLocaleDateString() : 'N/A';
                    const examTime = exam.startTime && exam.endTime ? `${exam.startTime} - ${exam.endTime}` : 'N/A';
                    const examSubject = exam.subject || 'N/A';
                    const examDepartment = exam.departmentName || 'N/A';
                    const examDetails = `Diff: ${exam.difficulty || 'N/A'} | Coeff: ${exam.coefficient || 'N/A'}`;
    
                    // Add exam details
                    doc.text(examDate, 15, yOffset);
                    doc.text(examSubject, 45, yOffset);
                    doc.text(examTime, 100, yOffset);
                    doc.text(examDepartment, 130, yOffset);
                    doc.text(examDetails, 170, yOffset);
    
                    yOffset += 10;
    
                    // Add separation line between exams
                    if (examIndex < exams.length - 1) {
                        doc.setDrawColor(200);
                        doc.line(15, yOffset + 2, pageWidth - 15, yOffset + 2);
                        yOffset += 5;
                    }
                });
    
                // Add footer
                doc.setFontSize(fontSizeSmall);
                doc.setTextColor(100);
                doc.text(`Generated on ${new Date().toLocaleDateString()}`, 15, 280);
            });
    
            doc.save("organized-exam-schedule.pdf");
        } catch (error) {
            console.error("Error generating PDF:", error);
            alert("Failed to generate PDF. Please check the console for details.");
        }
    };
    const handleDelete = async (examId) => {
       if (window.confirm('Are you sure you want to delete this exam?')) {
            try {
                await axios.delete(`http://localhost:8080/api/admin/exams/${examId}`);
                setExams(exams.filter(exam => exam.examId !== examId));
            } catch (error) {
                console.error('Error deleting exam:', error);
                alert('Failed to delete exam');
            }
        
    };}

    const handleUpdate = (exam) => {
        // For full update functionality, you'd typically open a modal or navigate to an edit page
      /*  setEditingExam(exam);
        alert(`Edit exam with ID: ${exam.examId}\nImplement edit functionality here`);
  */  };
  const handleFilterChange = (e) => {
    setFilters({
        ...filters,
        [e.target.name]: e.target.value
    });
};
const filteredExams = exams.filter(exam => {
    const examDate = new Date(exam.examDate);
    const examStart = new Date(`${exam.examDate}T${exam.startTime}`);
    const examEnd = new Date(`${exam.examDate}T${exam.endTime}`);

    // Text filter (now only searches subject)
    const searchLower = filters.search.toLowerCase();
    const matchesSearch = exam.subject.toLowerCase().includes(searchLower);

    // Department filter
    const matchesDepartment = !filters.department || 
                            exam.departmentName === filters.department;

    // Date filter (existing)
    let matchesDate = true;
    if (filters.startDate) {
        const startDate = new Date(filters.startDate);
        matchesDate = examDate >= startDate;
    }
    if (filters.endDate) {
        const endDate = new Date(filters.endDate);
        matchesDate = matchesDate && examDate <= endDate;
    }

    // Time filter (existing)
    let matchesTime = true;
    if (filters.startTime) {
        const [startHours, startMinutes] = filters.startTime.split(':');
        const startTime = new Date(exam.examDate);
        startTime.setHours(startHours, startMinutes);
        matchesTime = examStart >= startTime;
    }
    if (filters.endTime) {
        const [endHours, endMinutes] = filters.endTime.split(':');
        const endTime = new Date(exam.examDate);
        endTime.setHours(endHours, endMinutes);
        matchesTime = matchesTime && examEnd <= endTime;
    }

    return matchesSearch && matchesDepartment && matchesDate && matchesTime;
});

    if (loading) return <div className="p-4 text-gray-500">Loading...</div>;
    if (error) return <div className="p-4 text-red-500">Error: {error}</div>;
    if (!Array.isArray(exams)) return <div className="p-4 text-red-500">Invalid data format received</div>;
    return (
        <div className="exams-container">
            <div className="search-header">
<nav className="bg-gray-800 p-4">
  <div className="max-w-7xl mx-auto flex justify-between items-center">
    <div className="text-white text-xl font-bold">Exam Portal</div>
    <button
      onClick={() => {
        localStorage.removeItem('token');
        window.location.href = '/login';
      }}
      className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded"
    >
      Logout
    </button>
  </div>
</nav>
                <h1>Exams List</h1>
                <div className="filters">
                    <input
                        type="text"
                        name="search"
                        placeholder="Search..."
                        className="search-input"
                        value={filters.search}
                        onChange={handleFilterChange}
                    />
                    <select
                        name="department"
                        className="department-filter"
                        value={filters.department}
                        onChange={handleFilterChange}
                    >
                        <option value="">All Departments</option>
                        {[...new Set(exams.map(exam => exam.departmentName))].map(dept => (
                            <option key={dept} value={dept}>{dept}</option>
                        ))}
                    </select>
                    <div className="time-filters">
                        <div className="filter-group">
                            <label>Start Date:</label>
                            <input
                                type="date"
                                name="startDate"
                                value={filters.startDate}
                                onChange={handleFilterChange}
                            />
                            <input
                                type="time"
                                name="startTime"
                                value={filters.startTime}
                                onChange={handleFilterChange}
                            />
                        </div>
                        
                        <div className="filter-group">
                            <label>End Date:</label>
                            <input
                                type="date"
                                name="endDate"
                                value={filters.endDate}
                                onChange={handleFilterChange}
                                min={filters.startDate}
                            />
                            <input
                                type="time"
                                name="endTime"
                                value={filters.endTime}
                                onChange={handleFilterChange}
                            />
                        </div>
                    </div>
                </div>
            </div>

            {/* Bouton pour générer le PDF */}
            <button onClick={generatePDF} className="pdf-button">
                Generate PDF by Speciality
            </button>

            {filteredExams.length === 0 ? (
                <div className="no-results">No matching exams found</div>
            ) : (
                <div className="exams-list">
                    {filteredExams.map(exam => (
                        <div key={exam.examId} className="exam-card">
                            <div className="exam-content">
                                <h3>{exam.subject}</h3>
                                <p>
                                    {exam.departmentName} - 
                                    {new Date(exam.examDate).toLocaleDateString()} | 
                                    {exam.startTime} to {exam.endTime}
                                </p>
                                <div className="badges">
                                    <span className="difficulty-badge">
                                        Difficulty: {exam.difficulty}
                                    </span>
                                    <span className="coefficient-badge">
                                        Coefficient: {exam.coefficient}
                                    </span>
                                </div>
                            </div>
                            <div className="button-group">
                                <button 
                                    className="edit-btn"
                                    onClick={() => handleUpdate(exam)}
                                >
                                    Edit
                                </button>
                                <button 
                                    className="delete-btn"
                                    onClick={() => handleDelete(exam.examId)}
                                >
                                    Delete
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            )}
            <Link to="/admin/dashboard">Go back to Dashboard</Link>
        </div>
    );
};

export default ExamsPage;