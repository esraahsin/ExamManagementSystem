import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ExamsPage from './pages/ExamsPage';
import AddExam from './pages/AddExam';
import UpdateExam from './pages/UpdateExam';

import AvailableInvigilators from './pages/AvailableInvigilators'
import AddRoomPage from './pages/AddRoomPage';



import Dashboard from './pages/Dashboard';
import CalendarView from "./pages/CalendarView"
import Login from './pages/Login';
import Signup from './pages/Signup';

import TeacherSchedule from './pages/TeacherSchedule';


import StudentExams from './pages/StudentExams';

function App() {
  
  return (
    <BrowserRouter>
    <Routes>
    <Route path="/exams" element={<ExamsPage />} />
    <Route path="/admin/exams" element={<ExamsPage />} />
    <Route path="/admin/exams/addexam" element={<AddExam />} />

    <Route path='/admin/exams/updateexam' element={<UpdateExam/>}/>
    <Route path='/admin/exams/AvailableInvigilators' element={<AvailableInvigilators/>}/>
    <Route path='/admin/exams/AddRoomPage' element={<AddRoomPage/>}/>
    <Route path="/teacher-schedule" element={<TeacherSchedule />} />
    <Route path="/admin/exams/updateexam" element={<UpdateExam/>}/>
    <Route path='/admin/dashboard' element={<Dashboard/>}/>
    <Route path='/admin/calendar' element={<CalendarView/>}/>
    <Route path="/login" element={<Login />} />
    <Route path="/signup" element={<Signup />} />
    <Route path="/student/exam" element={<StudentExams />} />
    </Routes>
    </BrowserRouter>
  );
}

export default App;
