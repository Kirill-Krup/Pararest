import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import RegistrationPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
// import DashboardPage from './pages/DashboardPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Главная страница перенаправляет на регистрацию */}
        <Route path="/" element={<Navigate to="/register" replace />} />
        <Route path="/register" element={<RegistrationPage />} />
        <Route path="/login" element={<LoginPage />} />
        {/* <Route path="/dashboard" element={<DashboardPage />} /> */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;