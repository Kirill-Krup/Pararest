import type {AddMenuItemDTO, AddMenuItemRequest} from "../models/MenuItemDTO.ts";
import axios from "axios";
const API_BASE = 'http://localhost:8080/api/v1';

const api = axios.create({
    baseURL: API_BASE,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json',
    },
});
export const compositeservice = {
    async createMenuItemWithRecipe(formData: AddMenuItemDTO, ingredientsMap: Record<number, number>): Promise<boolean> {
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
    }
}