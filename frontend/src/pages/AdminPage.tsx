import React from 'react';

import '../styles/admin/AdminPage.css';
import AdminHeader from "../components/layout/Header/AdminHeader.tsx";
import UsersTable from "../components/admin/UsersTable.tsx";
import MenuTable from "../components/admin/MenuTable.tsx";

const AdminPage: React.FC = () => {
    return (
        <div className="admin-layout">
            <AdminHeader />
            <main className="admin-content-single">
                <section className="admin-section">
                    <UsersTable />
                </section>
                <section className="admin-section">
                    <MenuTable />
                </section>
                {/*<section className="admin-section">
                    <OrdersTable />
                </section>
                <section className="admin-section">
                    <TransactionsTable />
                </section>
                <section className="admin-section">
                    <StatisticsPlaceholder />
                </section>*/}
            </main>
        </div>
    );
};

export default AdminPage;