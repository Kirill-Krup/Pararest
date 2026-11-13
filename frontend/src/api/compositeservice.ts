import axios from "axios";
import type {FullOrderDTO} from "../models/types/order.types.ts";
import type {MenuItemFormData} from "./menuservice.ts";

const API_BASE = 'http://localhost:8080/api/v1/composite';

const api = axios.create({
    baseURL: API_BASE,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const compositeservice = {
    async createMenuItemWithRecipe(formData: MenuItemFormData, ingredientsMap: Record<number, number>): Promise<boolean> {
        try {
            const request = {
                item: {
                    name: formData.name,
                    description: formData.description,
                    price: formData.price,
                    category: { id: 0, name: formData.category },
                    imageUrl: formData.imageUrl || null,
                    preparationTime: null
                },
                ingredientsMap: ingredientsMap
            };

            const response = await api.post<boolean>('/menu/addNewDish', request);
            return response.data;
        } catch (error) {
            console.error('Ошибка при создании блюда с рецептом:', error);
            throw error;
        }
    },

    async getCart(): Promise<FullOrderDTO> {
        try {
            const response = await api.get<FullOrderDTO>('/orders/cart');
            return response.data;
        } catch (error) {
            console.error('Ошибка при получении корзины:', error);
            throw error;
        }
    }
};
