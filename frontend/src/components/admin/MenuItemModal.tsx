import React, { useState, useEffect } from 'react';
import { type MenuItemFormData } from '../../api/menuservice.ts';
import '../../styles/admin/MenuTableCss.css';
import type {MenuItemDTO} from "../../models/MenuItemDTO.ts";

interface MenuItemModalProps {
    isOpen: boolean;
    onClose: () => void;
    onSave: (data: MenuItemFormData) => Promise<void>;
    item: MenuItemDTO | null;
    categories: string[];
}

const MenuItemModal: React.FC<MenuItemModalProps> = ({
                                                         isOpen,
                                                         onClose,
                                                         onSave,
                                                         item,
                                                         categories
                                                     }) => {
    const [formData, setFormData] = useState<MenuItemFormData>({
        name: '',
        description: '',
        price: 0,
        category: '',
        recipe: '',
        imageUrl: '',
        isActive: true
    });
    const [newCategory, setNewCategory] = useState('');
    const [isAddingCategory, setIsAddingCategory] = useState(false);
    const [saving, setSaving] = useState(false);
    const [errors, setErrors] = useState<Record<string, string>>({});

    useEffect(() => {
        if (item) {
            setFormData({
                name: item.name,
                description: item.description,
                price: item.price,
                category: item.category,
                recipe: item.recipe || '',
                imageUrl: item.imageUrl || '',
                isActive: item.isActive
            });
        } else {
            setFormData({
                name: '',
                description: '',
                price: 0,
                category: categories[0] || '',
                recipe: '',
                imageUrl: '',
                isActive: true
            });
        }
    }, [item, categories]);

    const validate = (): boolean => {
        const newErrors: Record<string, string> = {};

        if (!formData.name.trim()) newErrors.name = 'Название обязательно';
        if (!formData.description.trim()) newErrors.description = 'Описание обязательно';
        if (formData.price <= 0) newErrors.price = 'Цена должна быть больше 0';
        if (!formData.category) newErrors.category = 'Категория обязательна';

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!validate()) return;

        try {
            setSaving(true);
            await onSave(formData);
            onClose();
        } catch (error) {
            console.error('Ошибка сохранения:', error);
        } finally {
            setSaving(false);
        }
    };

    const handleAddCategory = () => {
        if (newCategory.trim()) {
            setFormData({ ...formData, category: newCategory.trim() });
            setIsAddingCategory(false);
            setNewCategory('');
        }
    };

    if (!isOpen) return null;

    return (
        <div className="modal-overlay">
        <div className="modal-content">
        <div className="modal-header">
            <h3>{item ? 'Редактировать блюдо' : 'Добавить новое блюдо'}</h3>
            <button onClick={onClose} className="close-btn">&times;</button>
    </div>

    <form onSubmit={handleSubmit} className="modal-form">
    <div className="form-group">
        <label>Название *</label>
        <input
    type="text"
    value={formData.name}
    onChange={(e) => setFormData({ ...formData, name: e.target.value })}
    className={errors.name ? 'error' : ''}
    />
    {errors.name && <span className="error-message">{errors.name}</span>}
        </div>

        <div className="form-group">
        <label>Описание *</label>
        <textarea
        value={formData.description}
        onChange={(e) => setFormData({ ...formData, description: e.target.value })}
        rows={3}
        className={errors.description ? 'error' : ''}
        />
        {errors.description && <span className="error-message">{errors.description}</span>}
            </div>

            <div className="form-row">
        <div className="form-group">
            <label>Цена *</label>
            <input
            type="number"
            value={formData.price}
            onChange={(e) => setFormData({ ...formData, price: Number(e.target.value) })}
            min="0"
            step="0.01"
            className={errors.price ? 'error' : ''}
            />
            {errors.price && <span className="error-message">{errors.price}</span>}
                </div>

                <div className="form-group">
                <label>Категория *</label>
                {!isAddingCategory ? (
                        <div className="category-select-wrapper">
                        <select
                            value={formData.category}
                    onChange={(e) => setFormData({ ...formData, category: e.target.value })}
                    className={errors.category ? 'error' : ''}
                    >
                    <option value="">Выберите категорию</option>
                    {categories.map(cat => (
                        <option key={cat} value={cat}>{cat}</option>
                    ))}
                    </select>
                    <button
                    type="button"
                    onClick={() => setIsAddingCategory(true)}
                    className="add-category-btn"
                        >
                        + Новая
                        </button>
                        </div>
                ) : (
                    <div className="new-category-wrapper">
                    <input
                        type="text"
                    value={newCategory}
                    onChange={(e) => setNewCategory(e.target.value)}
                    placeholder="Название категории"
                    autoFocus
                    />
                    <button type="button" onClick={handleAddCategory} className="confirm-btn">✓</button>
                <button type="button" onClick={() => setIsAddingCategory(false)} className="cancel-btn">✗</button>
                </div>
                )}
                {errors.category && <span className="error-message">{errors.category}</span>}
                    </div>
                    </div>

                    <div className="form-group">
                    <label>URL изображения (необязательно)</label>
                <input
                    type="text"
                    value={formData.imageUrl || ''}
                    onChange={(e) => setFormData({ ...formData, imageUrl: e.target.value })}
                    placeholder="https://example.com/image.jpg"
                        />
                        </div>

                        <div className="form-group">
                    <label>Рецепт блюда</label>
                <textarea
                    value={formData.recipe || ''}
                    onChange={(e) => setFormData({ ...formData, recipe: e.target.value })}
                    rows={5}
                    placeholder="Ингредиенты и способ приготовления..."
                        />
                        </div>

                        <div className="form-group checkbox">
                <label>
                    <input
                        type="checkbox"
                    checked={formData.isActive}
                    onChange={(e) => setFormData({ ...formData, isActive: e.target.checked })}
                    />
                    Активно (доступно для заказа)
                    </label>
                    </div>

                    <div className="modal-footer">
                <button type="button" onClick={onClose} className="cancel-button">
                    Отмена
                    </button>
                    <button type="submit" className="save-button" disabled={saving}>
                    {saving ? 'Сохранение...' : item ? 'Сохранить' : 'Создать'}
                    </button>
                    </div>
                    </form>
                    </div>
                    </div>
                );
                };

                export default MenuItemModal;