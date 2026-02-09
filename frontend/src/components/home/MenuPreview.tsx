import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/home/MenuPreview.css';

interface MenuItem {
    id: number;
    name: string;
    description: string;
    price: number;
    category: string;
    popular?: boolean;
}

const MenuPreview: React.FC = () => {
    const navigate = useNavigate();
    const [activeCategory, setActiveCategory] = useState('Все');

    const menuItems: MenuItem[] = [
        {
            id: 1,
            name: 'Стейк Рибай',
            description: 'Сочный стейк из мраморной говядины с овощами гриль',
            price: 2500,
            category: 'Основные блюда',
            popular: true,
        },
        {
            id: 2,
            name: 'Лосось на гриле',
            description: 'Филе лосося с лимонным соусом и рисом',
            price: 1800,
            category: 'Основные блюда',
        },
        {
            id: 3,
            name: 'Цезарь с креветками',
            description: 'Классический салат с тигровыми креветками',
            price: 890,
            category: 'Салаты',
            popular: true,
        },
        {
            id: 4,
            name: 'Тирамису',
            description: 'Итальянский десерт с кофе и маскарпоне',
            price: 650,
            category: 'Десерты',
        },
        {
            id: 5,
            name: 'Том Ям',
            description: 'Острый тайский суп с морепродуктами',
            price: 750,
            category: 'Супы',
        },
        {
            id: 6,
            name: 'Крем-брюле',
            description: 'Нежный крем с карамельной корочкой',
            price: 550,
            category: 'Десерты',
            popular: true,
        },
    ];

    const categories = ['Все', 'Основные блюда', 'Салаты', 'Супы', 'Десерты'];

    const filteredItems = activeCategory === 'Все'
        ? menuItems
        : menuItems.filter(item => item.category === activeCategory);

    return (
        <section id="menu" className="menu-preview-section">
            <div className="container">
                <div className="section-header">
                    <span className="section-label">Меню</span>
                    <h2 className="section-title">Наши фирменные блюда</h2>
                    <p className="section-subtitle">
                        Попробуйте самые популярные позиции из нашего меню
                    </p>
                </div>

                <div className="category-filter">
                    {categories.map(category => (
                        <button
                            key={category}
                            className={`category-btn ${activeCategory === category ? 'active' : ''}`}
                            onClick={() => setActiveCategory(category)}
                        >
                            {category}
                        </button>
                    ))}
                </div>

                <div className="menu-grid">
                    {filteredItems.map(item => (
                        <div key={item.id} className="menu-card">
                            {item.popular && (
                                <span className="popular-badge">Популярное</span>
                            )}

                            <div className="menu-card-image">
                                {/* Placeholder для изображения */}
                                <div className="image-placeholder">
                                    <span>🍽️</span>
                                </div>
                            </div>

                            <div className="menu-card-content">
                                <h3 className="menu-card-title">{item.name}</h3>
                                <p className="menu-card-description">{item.description}</p>

                                <div className="menu-card-footer">
                                    <span className="menu-card-price">{item.price} ₽</span>
                                    <button className="add-to-cart-btn">
                                        Добавить
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>

                <div className="menu-cta">
                    <button
                        className="btn btn-primary"
                        onClick={() => navigate('/menu')}
                    >
                        Посмотреть полное меню
                    </button>
                </div>
            </div>
        </section>
    );
};

export default MenuPreview;