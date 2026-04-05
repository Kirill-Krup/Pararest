import React, { useEffect, useState } from 'react';
import { orderService } from '../../api/orderservice.ts';
import { type OrderDTO } from '../../models/types/order.types.ts';
import '../../styles/admin/OperationsTables.css';

const OrdersTable: React.FC = () => {
    const [orders, setOrders] = useState<OrderDTO[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const fetchOrders = async () => {
        try {
            setLoading(true);
            setError(null);
            const data = await orderService.getAllOrders();
            setOrders(data);
        } catch (err) {
            setError('Не удалось загрузить заказы');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchOrders();
    }, []);

    const formatDate = (dateStr?: string) => {
        if (!dateStr) return '—';
        return new Date(dateStr).toLocaleString('ru-RU');
    };

    if (loading && orders.length === 0) {
        return <div className="admin-lite-state">Загрузка заказов...</div>;
    }

    return (
        <div className="operations-section">
            <div className="operations-header">
                <h2>Заказы</h2>
                <button onClick={fetchOrders} className="refresh-btn">Обновить</button>
            </div>

            {error && <div className="error-message">{error}</div>}

            <div className="table-responsive">
                <table className="operations-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Пользователь</th>
                        <th>Статус</th>
                        <th>Тип</th>
                        <th>Сумма</th>
                        <th>Оплата</th>
                        <th>Позиций</th>
                        <th>Время заказа</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.length === 0 ? (
                        <tr>
                            <td colSpan={8} className="empty-message">Заказы не найдены</td>
                        </tr>
                    ) : (
                        orders.map((order) => (
                            <tr key={order.id}>
                                <td>{order.id}</td>
                                <td>{order.userId}</td>
                                <td>{order.status}</td>
                                <td>{order.type}</td>
                                <td>{order.totalPrice.toLocaleString('ru-RU')} ₽</td>
                                <td>{order.paymentType} / {order.paymentStatus}</td>
                                <td>{order.items?.length ?? 0}</td>
                                <td>{formatDate(order.orderTime)}</td>
                            </tr>
                        ))
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default OrdersTable;
