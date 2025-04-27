import { useState, useEffect } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import axios from 'axios';
import './CalendarView.css'; // Importez le fichier CSS
import { Link } from 'react-router-dom';

const localizer = momentLocalizer(moment);

const CalendarView = () => {
  const [exams, setExams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch exams from your API
  useEffect(() => {
    const fetchExams = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/exams');
        setExams(response.data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };
    fetchExams();
  }, []);

  // Convert exam data to calendar events
  const events = exams.map(exam => ({
    title: `${exam.subject} - ${exam.departmentName || exam.department?.name}`,
    start: moment(`${exam.examDate}T${exam.startTime}`).toDate(),
    end: moment(`${exam.examDate}T${exam.endTime}`).toDate(),
    allDay: false,
    resource: {
      id: exam.examId,
      speciality: exam.speciality,
      difficulty: exam.difficulty,
      coefficient: exam.coefficient
    }
  }));

  // Handle event click
  const handleExamClick = (exam) => {
    alert(`Exam Details:\n
      Subject: ${exam.subject}\n
      Department: ${exam.departmentName}\n
      Date: ${moment(exam.examDate).format('LL')}\n
      Time: ${exam.startTime} - ${exam.endTime}`);
  };

  // Handle slot selection (for creating new exams)
  const handleSlotClick = (slotInfo) => {
    const start = moment(slotInfo.start).format('YYYY-MM-DDTHH:mm');
    const end = moment(slotInfo.end).format('YYYY-MM-DDTHH:mm');
    console.log('Selected slot:', { start, end });
  };

  if (loading) return <div className="loading-message">Loading calendar...</div>;
  if (error) return <div className="error-message">Error: {error}</div>;

  return (
    <div className="calendar-container">
      <div className="calendar-header">
        <h1>Exam Calendar</h1>
        <Link to="/admin/exams/addexam">Add Exam</Link>
      </div>
      
      <div className="calendar-wrapper">
        <Calendar
          localizer={localizer}
          events={events}
          startAccessor="start"
          endAccessor="end"
          style={{ height: 600 }}
          onSelectEvent={(event) => handleExamClick(event.resource)}
          onSelectSlot={handleSlotClick}
          selectable
          defaultView="month"
          views={['month', 'week', 'day']}
          eventPropGetter={(event) => ({
            style: {
              backgroundColor: '#3B82F6',
              borderRadius: '4px',
              border: 'none',
              color: 'white'
            }
          })}
        />
      </div>
    </div>
  );
};

export default CalendarView;