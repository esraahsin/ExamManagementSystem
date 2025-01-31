import React, { useState } from 'react';
import Sidebar from '../Components/Sidebar';
import './Dashboard.css'; // Importez le fichier CSS

const Dashboard = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(true);

    return (
        <>
            <Sidebar isOpen={isSidebarOpen} toggleSidebar={() => setIsSidebarOpen(!isSidebarOpen)} />
            <div className={`dashboard-container ${isSidebarOpen ? '' : 'sidebar-closed'}`}>
                <div className="dashboard-header">
                    <h1>Dashboard</h1>
                </div>
                {/* Ajoutez le reste du contenu ici */}
            </div>
        </>
    );
};

export default Dashboard;