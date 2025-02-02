import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ExamsPage from './pages/ExamsPage';
import AddExam from './pages/AddExam';
import UpdateExam from './pages/UpdateExam';
import AddSupervisorPage from './pages/AddSupervisorPage';
import AddRoomPage from './pages/AddRoomPage';

function App() {
  return (
    <BrowserRouter>
    <Routes>
    <Route path="/exams" element={<ExamsPage />} />
    <Route path="/admin/exams" element={<ExamsPage />} />
    <Route path="/admin/exams/addexam" element={<AddExam />} />
    <Route path='/admin/exams/updateexam' element={<UpdateExam/>}/>
    <Route path='/admin/exams/AddSupervisorPage' element={<AddSupervisorPage/>}/>
    <Route path='/admin/exams/AddRoomPage' element={<AddRoomPage/>}/>
    </Routes>
    </BrowserRouter>
  );
}

export default App;
