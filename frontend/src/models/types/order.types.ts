export interface OrderInfoDTO{
    id: number,
    menuItemId: number,
    quantity: number
}


export interface OrderDTO{
    id: number,
    items: OrderInfoDTO,
    userId:string,
    status: string,
    type: string,
    orderTime: string,
    deliveryTime: string,
    totalPrice: number,
    paymentStatus: string,
    paymentType: string,
    userRequest: string
}

export interface FullOrderDTO{

}