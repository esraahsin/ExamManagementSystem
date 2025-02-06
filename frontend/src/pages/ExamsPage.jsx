import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import "./ExamPage.css"
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
    const navigate = useNavigate(); // Add this line
    const fetchAvailableRooms = async (exam) => {
        try {
            const response = await axios.get('http://localhost:8080/api/rooms/available', {
                params: {
                    examDate: exam.examDate,
                    startTime: exam.startTime,
                    endTime: exam.endTime,
                    minCapacity: exam.numberOfStudents // Supposons que numberOfStudents est une propriété de l'examen
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching available rooms:', error);
            return [];
        }
    };
    const handleAddRoom = async (exam) => {
        const availableRooms = await fetchAvailableRooms(exam);
        navigate(`/admin/exams/AddRoomPage`, { state: { exam, availableRooms } });
    };


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
        if (!window.confirm("Are you sure you want to delete this exam?")) return;

        try {
            await axios.delete(`http://localhost:8080/api/exams/${examId}`);
            setExams(exams.filter(exam => exam.examId !== examId)); 
        } catch (error) {
            console.error("Error deleting exam:", error);
            alert("Failed to delete exam.");
        }
    };

    const handleUpdate = (exam) => {
        navigate(`/admin/exams/updateexam`, { state: { exam } });
    };
  const handleFilterChange = (e) => {
    setFilters({
        ...filters,
        [e.target.name]: e.target.value
    });
};
const handleAddSupervisor = (exam) => {
    // Naviguer vers la page d'ajout de surveillant avec l'examen sélectionné
    navigate(`/admin/exams/AvailableInvigilators`, { state: { exam } });
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
                                <button 
                                className="add-supervisor-btn"
                                onClick={() => handleAddSupervisor(exam)}
                            >
                                Add Supervisor
                            </button>
                            <button 
    className="add-room-btn"
    onClick={() => handleAddRoom(exam)}
>
    Add Room
</button>
                                
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );

};

export default ExamsPage;