import React, { useEffect, useState } from 'react';
import { paymentService } from '../../api/paymentservice.ts';
import type { PaymentDTO } from '../../models/types/payment.types.ts';
import '../../styles/admin/OperationsTables.css';

const PaymentsTable: React.FC = () => {
    const [payments, setPayments] = useState<PaymentDTO[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const fetchPayments = async () => {
        try {
            setLoading(true);
            setError(null);
            const data = await paymentService.getAllPayments();
            setPayments(data);
        } catch (err) {
            setError('Не удалось загрузить платежи');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchPayments();
    }, []);

    const formatDate = (dateStr: string) => {
        if (!dateStr) return '—';
        return new Date(dateStr).toLocaleString('ru-RU');
    };

    if (loading && payments.length === 0) {
        return <div className="admin-lite-state">Загрузка платежей...</div>;
    }

    return (
        <div className="operations-section">
            <div className="operations-header">
                <h2>Платежи</h2>
                <button onClick={fetchPayments} className="refresh-btn">Обновить</button>
            </div>

            {error && <div className="error-message">{error}</div>}

            <div className="table-responsive">
                <table className="operations-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Пользователь</th>
                        <th>Сумма</th>
                        <th>Тип</th>
                        <th>Статус</th>
                        <th>Заказ</th>
                        <th>Дата</th>
                    </tr>
                    </thead>
                    <tbody>
                    {payments.length === 0 ? (
                        <tr>
                            <td colSpan={7} className="empty-message">Платежи не найдены</td>
                        </tr>
                    ) : (
                        payments.map((payment) => (
                            <tr key={payment.id}>
                                <td>{payment.id}</td>
                                <td>{payment.userId}</td>
                                <td>{payment.totalAmount.toLocaleString('ru-RU')} ₽</td>
                                <td>{payment.paymentType}</td>
                                <td>{payment.paymentStatus}</td>
                                <td>{payment.orderId ?? '—'}</td>
                                <td>{formatDate(payment.paymentDate)}</td>
                            </tr>
                        ))
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default PaymentsTable;
