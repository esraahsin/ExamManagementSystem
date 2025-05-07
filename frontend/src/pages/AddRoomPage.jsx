import React, { useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import './AddRoom.css'; // Replace with your actual CSS path

const AddRoomPage = () => {
    const location = useLocation();
    const { exam, availableRooms } = location.state;
    const [selectedRoom, setSelectedRoom] = useState(null);
    const navigate = useNavigate();

    const handleSelectRoom = async () => {
        if (!selectedRoom) return;

        try {
            await axios.post(`http://localhost:8080/api/exam_rooms/${exam.examId}/${selectedRoom.roomId}`, {
                created_at: new Date().toISOString().slice(0, 19).replace("T", " ") // Format SQL datetime
            });
            alert(`Room ${selectedRoom.roomName} assigned successfully!`);
            navigate('/admin/exams');
        } catch (error) {
            console.error('Error assigning room:', error);
            alert('Failed to assign room.');
        }
    };

    return (
        <div className="add-room-container">
            <h2>Select a Room for {exam.subject}</h2>
            <div className="rooms-list">
                {availableRooms.length === 0 ? (
                    <p>No available rooms at the moment.</p>
                ) : (
                    availableRooms.map(room => (
                        <div key={room.roomId} className={`room-card ${selectedRoom?.roomId === room.roomId ? 'selected' : ''}`}>
                            <h3>{room.roomName}</h3>
                            <p>Capacity: {room.capacity}</p>
                            <button onClick={() => setSelectedRoom(room)}>
                                {selectedRoom?.roomId === room.roomId ? 'Selected' : 'Select'}
                            </button>
                        </div>
                    ))
                )}
            </div>
            <button onClick={handleSelectRoom} disabled={!selectedRoom}>
                Assign Room
            </button>
        </div>
    );
};

export default AddRoomPage;
