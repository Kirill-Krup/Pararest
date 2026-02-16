import React from 'react';
import '../../styles/admin/AdminNavbar.css';

interface AdminNavbarProps {
    activeSection: string;
    onSectionChange: (section: string) => void;
}

const AdminNavbar: React.FC<AdminNavbarProps> = ({ activeSection, onSectionChange }) => {
    const sections = [
        { id: 'users', label: 'Пользователи', icon: '👥' },
        { id: 'menu', label: 'Меню', icon: '📋' },
        { id: 'ingredients', label: 'Ингредиенты', icon: '🥑' },
        { id: 'supplies', label: 'Поставки', icon: '🚚' },
        { id: 'orders', label: 'Заказы', icon: '📦' },
        { id: 'transactions', label: 'Транзакции', icon: '💰' },
        { id: 'statistics', label: 'Статистика', icon: '📊' }
    ];

    return (
        <nav className="admin-navbar">
            <div className="admin-navbar-header">
                <h3>Панель управления</h3>
            </div>
            <ul className="admin-navbar-menu">
                {sections.map(section => (
                    <li
                        key={section.id}
                        className={`admin-navbar-item ${activeSection === section.id ? 'active' : ''}`}
                        onClick={() => onSectionChange(section.id)}
                    >
                        <span className="admin-navbar-icon">{section.icon}</span>
                        <span className="admin-navbar-label">{section.label}</span>
                    </li>
                ))}
            </ul>
        </nav>
    );
};

export default AdminNavbar;