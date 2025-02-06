import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ExamsPage from './pages/ExamsPage';
import AddExam from './pages/AddExam';
import UpdateExam from './pages/UpdateExam';
import Dashboard from './pages/Dashboard';
import CalendarView from "./pages/CalendarView"
import Login from './pages/Login';
import Signup from './pages/Signup'
function App() {
  return (
    <BrowserRouter>
    <Routes>
    <Route path="/exams" element={<ExamsPage />} />
    <Route path="/admin/exams" element={<ExamsPage />} />
    <Route path="/admin/exams/addexam" element={<AddExam />} />
<<<<<<< HEAD
    <Route path="/admin/exams/updateexam" element={<UpdateExam/>}/>
    <Route path='/admin/dashboard' element={<Dashboard/>}/>
    <Route path='/admin/calendar' element={<CalendarView/>}/>
    <Route path="/login" element={<Login />} />
    <Route path="/signup" element={<Signup />} />
=======
    
>>>>>>> 9f7c3a92 (UpdateExam.jsx)

    </Routes>
    </BrowserRouter>
  );
}

export default App;
