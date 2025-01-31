import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./ExamPage.css"
import { Link } from 'react-router-dom';
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