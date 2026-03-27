export interface CategoryDTO{
    name: string
}

export interface MenuItemDTO{
    id: number,
    name: string,
    description: string,
    price: number,
    category: CategoryDTO,
    imageUrl: string,
    preparationTime: number
}