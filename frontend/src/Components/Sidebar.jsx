import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css'; // Importez le fichier CSS

const Sidebar = ({ isOpen, toggleSidebar }) => {
    return (
        <div className={`sidebar ${isOpen ? 'open' : 'closed'}`}>
            {/* Logo / Nom du Système */}
            <div className="sidebar-header">
                <h1>Admin Dashboard</h1>
            </div>

            {/* Menu Items */}
            <nav className="sidebar-nav">
                <Link to="/dashboard" className="menu-item">
                    🏠 Tableau de Bord
                </Link>
                <div className="menu-item">
                    📅 Gestion des Examens
                    <div className="submenu">
                        <Link to="/admin/exams">Liste des Examens</Link>
                        <Link to="/admin/exams/addexam">Ajouter un Examen</Link>
                        <Link to="/admin/calendar">Calendrier</Link>
                    </div>
                </div>
                <div className="menu-item">
                    👤 Gestion des Utilisateurs
                    <div className="submenu">
                        <Link to="/users">Liste des Utilisateurs</Link>
                        <Link to="/users/add">Ajouter un Utilisateur</Link>
                        <Link to="/roles">Rôles et Permissions</Link>
                    </div>
                </div>
                <Link to="/departments" className="menu-item">
                    🏛️ Gestion des Départements
                </Link>
                <Link to="/reports" className="menu-item">
                    📊 Rapports et Statistiques
                </Link>
                <Link to="/settings" className="menu-item">
                    ⚙️ Paramètres du Système
                </Link>
                <Link to="/support" className="menu-item">
                    ❓ Support et Aide
                </Link>
                <button onClick={() => alert('Déconnexion')} className="menu-item">
                    🔒 Déconnexion
                </button>
            </nav>

            {/* Toggle Button */}
            <button
                onClick={toggleSidebar}
                className="toggle-button"
            >
                {isOpen ? '◄' : '►'}
            </button>
        </div>
    );
};

export default Sidebar;