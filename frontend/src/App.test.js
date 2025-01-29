// frontend/src/App.js
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ExamsPage from './pages/ExamsPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/exams" element={<ExamsPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;