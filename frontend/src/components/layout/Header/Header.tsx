import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../../context/AuthContext.tsx";
import UserDropdown from "../Header/UserDropdown.tsx";
import "../../../styles/layout/Header/UserDropdown.css";

const Header: React.FC = () => {
    const [isScrolled, setIsScrolled] = useState(false);
    const { isAuthenticated } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        const handleScroll = () => {
            setIsScrolled(window.scrollY > 50);
        };
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    const scrollToSection = (sectionId: string) => {
        if (window.location.pathname === '/') {
            const element = document.getElementById(sectionId);
            if (element) {
                element.scrollIntoView({ behavior: 'smooth' });
            }
        } else {
            navigate(`/#${sectionId}`);
        }
    };

    return (
        <header className={`header ${isScrolled ? 'scrolled' : ''}`}>
            <div className="header-container">
                <div className="logo">
                    <Link to={"/"}>
                        <h1>Pararest</h1>
                        <span className="tagline">Restaurant & Hotel</span>
                    </Link>
                </div>

                <nav className="nav">
                    <button
                        className="nav-link"
                        onClick={() => scrollToSection('hero')}
                    >
                        Главная
                    </button>

                    <button
                        className="nav-link"
                        onClick={() => navigate('/reservation')}
                    >
                        Бронирование
                    </button>

                    <button
                        className="nav-link"
                        onClick={() => scrollToSection('menu')}
                    >
                        Меню
                    </button>

                    <button
                        className="nav-link"
                        onClick={() => scrollToSection('about')}
                    >
                        О нас
                    </button>
                </nav>

                <div className="header-right">
                    {isAuthenticated ? (
                        <UserDropdown />
                    ) : (
                        <div className="auth-buttons">
                            <button
                                className="btn btn-login"
                                onClick={() => navigate('/login')}
                            >
                                Войти
                            </button>
                            <button
                                className="btn btn-register"
                                onClick={() => navigate('/register')}
                            >
                                Регистрация
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </header>
    );
};

export default Header;