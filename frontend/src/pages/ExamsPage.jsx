import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ExamsPage = () => {
    const [exams, setExams] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchExams = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/exams');
                console.log('Response data:', response.data); // Debug log to see the data structure
                
                // Ensure we're working with an array
                const examsArray = Array.isArray(response.data) ? response.data : [];
                setExams(examsArray);
            } catch (error) {
                console.error('Error fetching exams:', error);
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchExams();
    }, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;
    if (!Array.isArray(exams)) return <div>Invalid data format received</div>;
    if (exams.length === 0) return <div>No exams found</div>;

    return (
        <div>
            <h1>Exams</h1>
            <ul>
                {exams.map(exam => (
                    <li key={exam.examId}>
                        {exam.subject} - {new Date(exam.examDate).toLocaleDateString()}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ExamsPage;