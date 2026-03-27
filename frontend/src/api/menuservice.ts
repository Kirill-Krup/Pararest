import axios from 'axios';
import type {CategoryDTO, MenuItemDTO} from "../models/types/menu.types.ts";

const API_BASE = 'http://localhost:8080/api/v1';

const api = axios.create({
    baseURL: API_BASE,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json',
    },
});

export interface MenuCategoryDTO{
    id: number,
    name: string
}

export interface AddMenuItemDTO {
    name: string;
    description: string;
    price: number;
    category: MenuCategoryDTO;
    imageUrl?: string | null;
    preparationTime?: number | null
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


    async createMenuItem(data: AddMenuItemDTO): Promise<MenuItemDTO> {
        try {
            const response = await api.post<MenuItemDTO>('/menu/addNewMenuItemNoRecipe', data);
            return response.data;
        } catch (error) {
            console.error('Ошибка при создании позиции меню:', error);
            throw error;
        }
    },

    async updateMenuItem(id: string, data: Partial<AddMenuItemDTO>): Promise<MenuItemDTO> {
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
    },

    async getAllCategories(): Promise<CategoryDTO[]>{
        try{
            const response = await api.get<CategoryDTO[]>(`/categories/getAll`);
            return response.data;
        }catch (error) {
            console.error('Ошибка при изменении статуса активности:', error);
            throw error;
        }
    }
};