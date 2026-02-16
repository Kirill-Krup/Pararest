export interface MenuItemDTO {
    id: string;
    name: string;
    description: string;
    price: number;
    category: string;
    imageUrl?: string | null;
    recipe?: string | null;
    addedByManagerId: string;
    addedByManagerName: string;
    createdAt: string;
    updatedAt?: string;
    isActive: boolean;        
}