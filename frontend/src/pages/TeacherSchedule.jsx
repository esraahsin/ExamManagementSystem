import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import { useLocation } from 'react-router-dom';
import { format } from 'date-fns';

const TeacherSchedule = () => {
  const location = useLocation();
  const { schedule } = location.state || { schedule: [] };

  const [selectedDate, setSelectedDate] = useState(new Date());

  // Formatter la date sélectionnée au format "yyyy-MM-dd"
  const formattedDate = format(selectedDate, 'yyyy-MM-dd');

  // Vérifier si la date sélectionnée correspond à un examen
  const examsForSelectedDate = schedule.filter((exam) => exam.exam_date === formattedDate);

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Emploi du temps de surveillance</h1>

      {/* Petit calendrier */}
      <div className="flex justify-center mb-6">
        <Calendar
          onChange={setSelectedDate}
          value={selectedDate}
          tileClassName={({ date }) => {
            const dateString = format(date, 'yyyy-MM-dd');
            return schedule.some((exam) => exam.exam_date === dateString) ? 'bg-green-200' : '';
          }}
        />
      </div>

      {/* Affichage des examens du jour sélectionné */}
      <div className="mt-4">
        <h2 className="text-xl font-semibold">Examens du {formattedDate}</h2>
        {examsForSelectedDate.length > 0 ? (
          <ul className="mt-2 space-y-2">
            {examsForSelectedDate.map((exam, index) => (
              <li key={index} className="border p-3 rounded bg-gray-100 shadow-sm">
                <strong>{exam.subject}</strong>  
                <br /> ⏰ {exam.start_time} - {exam.end_time}  
                <br /> 📍 Salle : {exam.room_name}, {exam.location}
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-500 mt-2">Aucun examen prévu pour ce jour.</p>
        )}
      </div>
    </div>
  );
};

export default TeacherSchedule;
