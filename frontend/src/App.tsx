import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import RegistrationPage from './pages/auth/RegisterPage.tsx';
import LoginPage from './pages/auth/LoginPage.tsx';
import HomePage from './pages/HomePage.tsx';
import {AuthProvider} from "./context/AuthContext.tsx";

function App() {
  return (
    <BrowserRouter>
        <AuthProvider>
            <Routes>
                <Route path="/" element={<Navigate to="/home" replace />} />
                <Route path="/register" element={<RegistrationPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/home" element={<HomePage />} />
            </Routes>
        </AuthProvider>
    </BrowserRouter>
  );
}

export default App;