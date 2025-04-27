import { useEffect, useState } from 'react';
import axios from 'axios';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';  
import { jsPDF } from 'jspdf'; 

const StudentExams = () => {
  const [exams, setExams] = useState([]);
  const [error, setError] = useState('');
  const [selectedDate, setSelectedDate] = useState(new Date()); 
  const [filteredExams, setFilteredExams] = useState([]); 
  const [upcomingExams, setUpcomingExams] = useState([]); 

  useEffect(() => {
    const fetchExams = async () => {
      try {
        const email = localStorage.getItem('email');
        const response = await axios.get(`http://localhost:8080/api/student/exams?email=${email}`);
        setExams(response.data);
      } catch (err) {
        console.error("Error fetching exams:", err);
        setError('Failed to load exams');
      }
    };

    fetchExams();
  }, []);

  useEffect(() => {
    const formattedDate = selectedDate.toLocaleDateString('en-GB');
    const filtered = exams.filter(exam => formatDate(exam.examDate) === formattedDate);
    setFilteredExams(filtered);

    const today = new Date();
    const upcoming = exams.filter(exam => new Date(exam.examDate) > today);
    setUpcomingExams(upcoming);
  }, [selectedDate, exams]);

  const formatTime = (timeString) => {
    if (!timeString) return '';
    const time = timeString.split(':');
    return `${time[0]}:${time[1]}`;
  };

  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-GB');
  };

  const generatePDF = () => {
    const doc = new jsPDF();
    const today = new Date().toLocaleDateString();

    doc.setFontSize(18);
    doc.text('Exam Schedule', 20, 20);
    doc.setFontSize(12);
    doc.text(`Date: ${today}`, 20, 30);

    const sortedExams = [...exams].sort((a, b) => new Date(a.examDate) - new Date(b.examDate));

    let yOffset = 40; 
    sortedExams.forEach((exam, index) => {
      doc.text(`${index + 1}. ${exam.subject}`, 20, yOffset);
      doc.text(`Date: ${formatDate(exam.examDate)}`, 100, yOffset);
      doc.text(`Start: ${formatTime(exam.startTime)}`, 150, yOffset);
      doc.text(`End: ${formatTime(exam.endTime)}`, 180, yOffset);
      yOffset += 10;
    });
    doc.save('exam_schedule.pdf');
  };

  return (
    <div className="container py-5">
      <div className="card shadow-lg">
        <div className="card-header bg-primary text-white">
          <h2 className="h4 mb-0">Exam Schedule</h2>
        </div>
        
        <div className="card-body">
          {error && (
            <div className="alert alert-danger" role="alert">
              {error}
            </div>
          )}

          <div className="row">
            <div className="col-md-4">
              <Calendar
                onChange={setSelectedDate}
                value={selectedDate}
              />
            </div>
            <div className="col-md-8">
              <h4>Exams on {formatDate(selectedDate)}</h4>
              {filteredExams.length === 0 ? (
                <div className="text-center py-4">
                  <div className="alert alert-info">No exams on this date</div>
                </div>
              ) : (
                <div className="table-responsive">
                  <table className="table table-hover table-striped mb-0">
                    <thead className="thead-dark">
                      <tr>
                        <th scope="col">#</th>
                        <th scope="col">Subject</th>
                        <th scope="col">Date</th>
                        <th scope="col">Start</th>
                        <th scope="col">End</th>
                      </tr>
                    </thead>
                    <tbody>
                      {filteredExams.map((exam, index) => (
                        <tr key={exam.examId}>
                          <th scope="row">{index + 1}</th>
                          <td className="font-weight-bold">{exam.subject}</td>
                          <td>{formatDate(exam.examDate)}</td>
                          <td className="text-success">{formatTime(exam.startTime)}</td>
                          <td className="text-danger">{formatTime(exam.endTime)}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}

              <h4 className="mt-5">Upcoming Exams</h4>
              {upcomingExams.length === 0 ? (
                <div className="text-center py-4">
                  <div className="alert alert-info">No upcoming exams</div>
                </div>
              ) : (
                <div className="table-responsive">
                  <table className="table table-hover table-striped mb-0">
                    <thead className="thead-dark">
                      <tr>
                        <th scope="col">#</th>
                        <th scope="col">Subject</th>
                        <th scope="col">Date</th>
                        <th scope="col">Start</th>
                        <th scope="col">End</th>
                      </tr>
                    </thead>
                    <tbody>
                      {upcomingExams.map((exam, index) => (
                        <tr key={exam.examId}>
                          <th scope="row">{index + 1}</th>
                          <td className="font-weight-bold">{exam.subject}</td>
                          <td>{formatDate(exam.examDate)}</td>
                          <td className="text-success">{formatTime(exam.startTime)}</td>
                          <td className="text-danger">{formatTime(exam.endTime)}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          </div>

          <div className="text-center mt-4">
            <button className="btn btn-success" onClick={generatePDF}>Generate PDF</button>
          </div>
        </div>

        <div className="card-footer bg-light">
          <small className="text-muted">
            Total exams: {exams.length} • Last updated: {new Date().toLocaleTimeString()}
          </small>
        </div>
      </div>
    </div>
  );
};

export default StudentExams;
