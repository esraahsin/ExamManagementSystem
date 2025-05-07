import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Sidebar from '../Components/Sidebar'; // Ensure Sidebar component is correctly imported
import './Dashboard.css';

const Dashboard = () => {
    const [stats, setStats] = useState({
        departments: 0,
        students: 0,
        teachers: 0,
        exams: 0,
        upcomingExams: 0
    });

    const [upcomingExams, setUpcomingExams] = useState([]);
    const [isSidebarOpen, setIsSidebarOpen] = useState(true); // Added missing state for sidebar

    useEffect(() => {
        const fetchData = async () => {
            try {
                const statsResponse = await axios.get('http://localhost:8080/api/stats');
                setStats(statsResponse.data);

                const examsResponse = await axios.get('http://localhost:8080/api/admin/exams');
                setUpcomingExams(examsResponse.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        fetchData();
    }, []);

    const formatDateTime = (dateString, timeString) => {
        const options = { 
            weekday: 'short', 
            year: 'numeric', 
            month: 'short', 
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        };
        const dateTime = new Date(`${dateString}T${timeString}`);
        return dateTime.toLocaleDateString('en-US', options);
    };

    const calculateDuration = (startTime, endTime) => {
        const [startHours, startMinutes] = startTime.split(':').map(Number);
        const [endHours, endMinutes] = endTime.split(':').map(Number);
        
        const totalMinutes = (endHours * 60 + endMinutes) - (startHours * 60 + startMinutes);
        return `${Math.floor(totalMinutes / 60)}h ${totalMinutes % 60}m`;
    };

    return (
        <div className={`dashboard-container ${isSidebarOpen ? '' : 'sidebar-closed'}`}>
            <Sidebar isOpen={isSidebarOpen} toggleSidebar={() => setIsSidebarOpen(!isSidebarOpen)} />
            <div className="dashboard-wrapper">
                <div className="stats-grid">
                    <div className="stat-card">
                        <h3>Departments</h3>
                        <p>{stats.departments}</p>
                    </div>
                    <div className="stat-card">
                        <h3>Students</h3>
                        <p>{stats.students}</p>
                    </div>
                    <div className="stat-card">
                        <h3>Teachers</h3>
                        <p>{stats.teachers}</p>
                    </div>
                    <div className="stat-card">
                        <h3>Exams</h3>
                        <p>{stats.exams}</p>
                    </div>
                </div>

                <div className="upcoming-exams-section">
                    <h2>Upcoming Exams</h2>
                    <div className="exams-list">
                        {upcomingExams.map(exam => (
                            <div key={exam.examId} className="exam-card">
                                <div className="exam-date">
                                    {formatDateTime(exam.examDate, exam.startTime)}
                                </div>
                                <div className="exam-details">
                                    <h3>{exam.subject}</h3>
                                    <p>Duration: {calculateDuration(exam.startTime, exam.endTime)}</p>
                                    <p>Difficulty: {'★'.repeat(exam.difficulty)}</p>
                                    <p>Coefficient: {exam.coefficient}</p>
                                    {exam.departmentName && <p>Department: {exam.departmentName}</p>}
                                    {exam.speciality && <p>Speciality: {exam.speciality}</p>}
                                </div>
                            </div>
                        ))}
                        {upcomingExams.length === 0 && (
                            <div className="no-exams">No upcoming exams</div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
