import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ExamsPage from './pages/ExamsPage';
import AddExam from './pages/AddExam';

function App() {
  return (
    <BrowserRouter>
    <Routes>
    <Route path="/exams" element={<ExamsPage />} />
    <Route path="/admin/exams" element={<ExamsPage />} />
    <Route path="/admin/exams/addexam" element={<AddExam />} />
    

    </Routes>
    </BrowserRouter>
  );
}

export default App;
