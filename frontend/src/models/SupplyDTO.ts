export interface SupplierDTO {
    id: number;
    name: string;
    phone: string;
    address: string;
}

export interface SupplyDTO {
    id: number;
    supplier: SupplierDTO;
    ingredient: {
        id: number;
        name: string;
        unit: string;
    };
    dateOfRequest: string;
    dateOfSupply: string | null;
}

export interface SupplyRequestDTO {
    supplierId: number;
    ingredientId: number;
    quantity: number;
}