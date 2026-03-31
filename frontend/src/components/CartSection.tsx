import React, { useState, useEffect, useCallback } from 'react';
import '../styles/CartSection.css';
import {orderService} from "../api/orderservice.ts";
import {type CreateOrderDTO, type FullOrderDTO, OrderType} from "../models/types/order.types.ts";
import {PaymentType} from "../models/types/payment.types.ts";
import type {MenuItemDTO} from "../models/types/menu.types.ts";
import {compositeservice} from "../api/compositeservice.ts";

interface CartSectionProps {
    onOrderCreated?: () => void;
}

const CartSection: React.FC<CartSectionProps> = ({ onOrderCreated }) => {
    const [fullCart, setFullCart] = useState<FullOrderDTO | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const [orderType, setOrderType] = useState<OrderType>(OrderType.IN_RESTAURANT);
    const [paymentType, setPaymentType] = useState<PaymentType>(PaymentType.CARD);
    const [deliveryTime, setDeliveryTime] = useState<string>('');
    const [userRequest, setUserRequest] = useState<string>('');
    const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

    const fetchCart = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const data = await compositeservice.getCart();
            setFullCart(data);
        } catch (err) {
            console.error(err);
            setError('Не удалось загрузить корзину');
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchCart();
    }, [fetchCart]);

    const refreshCart = async () => {
        await fetchCart();
    };

    const handleIncrement = async (menuItemId: number) => {
        try {
            await orderService.addToCart(menuItemId);
            await refreshCart();
        } catch (err) {
            console.error(err);
            setError('Не удалось добавить товар');
        }
    };

    const handleDecrement = async (menuItemId: number) => {
        try {
            await orderService.removeFromCart(menuItemId);
            await refreshCart();
        } catch (err) {
            console.error(err);
            setError('Не удалось удалить товар');
        }
    };

    const handleRemoveItem = async (menuItemId: number) => {
        try {
            await orderService.removeFromCart(menuItemId);
            await refreshCart();
        } catch (err) {
            console.error(err);
            setError('Не удалось удалить товар');
        }
    };

    const handleClearCart = async () => {
        try {
            await orderService.clearCart();
            await refreshCart();
        } catch (err) {
            console.error(err);
            setError('Не удалось очистить корзину');
        }
    };

    const handleSubmitOrder = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!fullCart || !fullCart.order.items || fullCart.order.items.length === 0) {
            setError('Корзина пуста');
            return;
        }

        setIsSubmitting(true);
        setError(null);

        const orderData: CreateOrderDTO = {
            type: orderType,
            paymentType: paymentType,
            userRequest: userRequest.trim() || undefined,
            deliveryTime: orderType === OrderType.DELIVERY_TO_HOTEL_NUMBER ? deliveryTime : undefined,
        };

        try {
            await orderService.createOrder(orderData);
            await refreshCart();
            if (onOrderCreated) onOrderCreated();
            setUserRequest('');
            setDeliveryTime('');
            alert('Заказ успешно оформлен!');
        } catch (err) {
            console.error(err);
            setError('Не удалось оформить заказ');
        } finally {
            setIsSubmitting(false);
        }
    };

    const menuItemMap = new Map<number, MenuItemDTO>();
    if (fullCart?.menuItem) {
        fullCart.menuItem.forEach(item => menuItemMap.set(item.id, item));
    }

    const cartItems = fullCart?.order?.items;
    const itemsArray = Array.isArray(cartItems) ? cartItems : [];

    const totalPrice = itemsArray.reduce((sum, item) => {
        const menuItem = menuItemMap.get(item.menuItemId);
        const price = menuItem?.price || 0;
        return sum + price * item.quantity;
    }, 0);

    if (loading && !fullCart) {
        return <div className="cart-section loading">Загрузка корзины...</div>;
    }

    if (error) {
        return <div className="cart-section error">Ошибка: {error}</div>;
    }

    const isEmpty = itemsArray.length === 0;

    return (
        <div className="cart-section">
            <h2>Ваша корзина</h2>

            {isEmpty ? (
                <div className="empty-cart">
                    <p>Корзина пуста</p>
                    <button className="continue-shopping" onClick={() => window.location.href = '/menu'}>
                        Перейти в меню
                    </button>
                </div>
            ) : (
                <>
                    <div className="cart-items">
                        {itemsArray.map((item) => {
                            const menuItem = menuItemMap.get(item.menuItemId);
                            if (!menuItem) return null;
                            const itemTotal = menuItem.price * item.quantity;
                            return (
                                <div key={item.menuItemId} className="cart-item">
                                    <div className="item-image">
                                        <img src={menuItem.imageUrl} alt={menuItem.name} />
                                    </div>
                                    <div className="item-details">
                                        <div className="item-info">
                                            <span className="item-name">{menuItem.name}</span>
                                            <span className="item-price">{menuItem.price} ₽</span>
                                        </div>
                                        <div className="item-actions">
                                            <button
                                                className="quantity-btn"
                                                onClick={() => handleDecrement(item.menuItemId)}
                                                disabled={isSubmitting}
                                            >
                                                -
                                            </button>
                                            <span className="item-quantity">{item.quantity}</span>
                                            <button
                                                className="quantity-btn"
                                                onClick={() => handleIncrement(item.menuItemId)}
                                                disabled={isSubmitting}
                                            >
                                                +
                                            </button>
                                            <button
                                                className="remove-btn"
                                                onClick={() => handleRemoveItem(item.menuItemId)}
                                                disabled={isSubmitting}
                                            >
                                                Удалить
                                            </button>
                                        </div>
                                    </div>
                                    <div className="item-total">{itemTotal} ₽</div>
                                </div>
                            );
                        })}
                    </div>

                    <div className="cart-summary">
                        <div className="total">
                            <span>Итого:</span>
                            <span>{totalPrice} ₽</span>
                        </div>
                        <button className="clear-cart" onClick={handleClearCart} disabled={isSubmitting}>
                            Очистить корзину
                        </button>
                    </div>

                    <div className="order-form">
                        <h3>Оформление заказа</h3>
                        <form onSubmit={handleSubmitOrder}>
                            <div className="form-group">
                                <label>Тип заказа:</label>
                                <select
                                    value={orderType}
                                    onChange={(e) => setOrderType(e.target.value as OrderType)}
                                    disabled={isSubmitting}
                                >
                                    <option value={OrderType.IN_RESTAURANT}>В ресторане</option>
                                    <option value={OrderType.DELIVERY_TO_HOTEL_NUMBER}>Доставка в отель</option>
                                </select>
                            </div>

                            <div className="form-group">
                                <label>Способ оплаты:</label>
                                <select
                                    value={paymentType}
                                    onChange={(e) => setPaymentType(e.target.value as PaymentType)}
                                    disabled={isSubmitting}
                                >
                                    <option value={PaymentType.CARD}>Карта</option>
                                    <option value={PaymentType.CASH}>Наличные</option>
                                </select>
                            </div>

                            {orderType === OrderType.DELIVERY_TO_HOTEL_NUMBER && (
                                <div className="form-group">
                                    <label>Время доставки:</label>
                                    <input
                                        type="datetime-local"
                                        value={deliveryTime}
                                        onChange={(e) => setDeliveryTime(e.target.value)}
                                        disabled={isSubmitting}
                                        required
                                    />
                                </div>
                            )}

                            <div className="form-group">
                                <label>Пожелания к заказу:</label>
                                <textarea
                                    value={userRequest}
                                    onChange={(e) => setUserRequest(e.target.value)}
                                    disabled={isSubmitting}
                                    rows={3}
                                />
                            </div>

                            <button type="submit" className="submit-order" disabled={isSubmitting}>
                                {isSubmitting ? 'Оформление...' : 'Оформить заказ'}
                            </button>
                        </form>
                    </div>
                </>
            )}
        </div>
    );
};

export default CartSection;