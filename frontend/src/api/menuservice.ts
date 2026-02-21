import axios from 'axios';
import type { MenuItemDTO, AddMenuItemDTO, AddMenuItemRequest } from '../models/MenuItemDTO';

const API_BASE = 'http://localhost:8080/api/v1';

const api = axios.create({
    baseURL: API_BASE,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json',
    },
});

export interface MenuItemFormData {
    name: string;
    description: string;
    price: number;
    category: string;
    recipe?: string | null;
    imageUrl?: string | null;
    isActive: boolean;
}

export const menuService = {
    async getAllMenuItems(): Promise<MenuItemDTO[]> {
        try {
            const response = await api.get<MenuItemDTO[]>('/menu/getAll');
            return response.data;
        } catch (error) {
            console.error('Ошибка при получении меню:', error);
            throw error;
        }
    },

    async getMenuItemById(id: string): Promise<MenuItemDTO> {
        try {
            const response = await api.get<MenuItemDTO>(`/menu/${id}`);
            return response.data;
        } catch (error) {
            console.error('Ошибка при получении позиции меню:', error);
            throw error;
        }
    },

    async createMenuItemWithRecipe(formData: MenuItemFormData, ingredientsMap: Record<number, number>): Promise<boolean> {
        try {
            const itemDTO: AddMenuItemDTO = {
                name: formData.name,
                description: formData.description,
                price: formData.price,
                category: formData.category,
                imageUrl: formData.imageUrl || null,
                isActive: formData.isActive
            };

            const request: AddMenuItemRequest = {
                item: itemDTO,
                ingredientsMap: ingredientsMap
            };

            const response = await api.post<boolean>('/composite/menu/addNewDish', request);
            return response.data;
        } catch (error) {
            console.error('Ошибка при создании блюда с рецептом:', error);
            throw error;
        }
    },

    async createMenuItem(data: MenuItemFormData): Promise<MenuItemDTO> {
        try {
            const response = await api.post<MenuItemDTO>('/menu/create', data);
            return response.data;
        } catch (error) {
            console.error('Ошибка при создании позиции меню:', error);
            throw error;
        }
    },

    async updateMenuItem(id: string, data: Partial<MenuItemFormData>): Promise<MenuItemDTO> {
        try {
            const response = await api.put<MenuItemDTO>(`/menu/update/${id}`, data);
            return response.data;
        } catch (error) {
            console.error('Ошибка при обновлении позиции меню:', error);
            throw error;
        }
    },

    async deleteMenuItem(id: string): Promise<void> {
        try {
            await api.delete(`/menu/delete/${id}`);
        } catch (error) {
            console.error('Ошибка при удалении позиции меню:', error);
            throw error;
        }
    },

    async toggleActive(id: string): Promise<MenuItemDTO> {
        try {
            const response = await api.patch<MenuItemDTO>(`/menu/toggle-active/${id}`);
            return response.data;
        } catch (error) {
            console.error('Ошибка при изменении статуса активности:', error);
            throw error;
        }
    }
};