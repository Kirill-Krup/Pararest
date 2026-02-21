import type {MenuItemFormData} from "../api/menuservice.ts";

export interface MenuItemDTO {
    id: string;
    name: string;
    description: string;
    price: number;
    category: string;
    recipe?: string | null;
    imageUrl?: string | null;
    isActive: boolean;
    addedByManagerName?: string;
    createdAt: string;
    updatedAt?: string;
}

export interface AddMenuItemDTO {
    name: string;
    description: string;
    price: number;
    category: string;
    imageUrl?: string | null;
    isActive: boolean;
}

export interface AddMenuItemRequest {
    item: AddMenuItemDTO;
    ingredientsMap: Record<number, number>;
}

export function createAddMenuItemRequest(
    formData: MenuItemFormData,
    ingredientsMap: Record<number, number>
): AddMenuItemRequest {
    return {
        item: {
            name: formData.name,
            description: formData.description,
            price: formData.price,
            category: formData.category,
            imageUrl: formData.imageUrl,
            isActive: formData.isActive
        },
        ingredientsMap
    };
}