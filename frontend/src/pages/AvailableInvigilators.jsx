import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import './AvailableInvigilators.css'; // Importation du fichier CSS

const AvailableInvigilators = () => {
  const location = useLocation();
  const { exam } = location.state || {}; 

  const [invigilators, setInvigilators] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [selectedInvigilator, setSelectedInvigilator] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (!exam) {
      setError("Aucun examen sélectionné.");
      return;
    }

    const { examId, examDate, startTime, endTime } = exam;

    const fetchInvigilators = async () => {
      setLoading(true);
      setError("");

      try {
        const response = await axios.get("http://localhost:8080/api/invigilators/available", {
          params: { examId, examDate, startTime, endTime },
        });

        setInvigilators(response.data);
        if (!Array.isArray(response.data)) {
          setError("Les données reçues sont invalides.");
        }
      } catch (err) {
        setError("Erreur lors de la récupération des surveillants disponibles.");
      } finally {
        setLoading(false);
      }
    };

    fetchInvigilators();
  }, [exam]);

  const handleSelectInvigilator = (invigilator) => {
    setSelectedInvigilator(invigilator);
    setInvigilators((prevInvigilators) =>
      prevInvigilators.filter((item) => item.userId !== invigilator.userId)
    );
  };

  const handleSubmitSelection = () => {
    if (selectedInvigilator) {
      alert(`Surveillant sélectionné : ${selectedInvigilator.name}`);
      navigate('/admin/exams');
    } else {
      alert("Veuillez sélectionner un surveillant.");
    }
  };

  return (
    <div className="container">
      <h2 className="text-xl font-bold mb-4">Surveillants disponibles</h2>

      {loading && <p className="loading">Chargement...</p>}
      {error && <p className="error">{error}</p>}

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Sélectionner</th>
          </tr>
        </thead>
        <tbody>
          {invigilators.length > 0 ? (
            invigilators.map((invigilator) => (
              <tr key={invigilator.userId}>
                <td>{invigilator.userId}</td>
                <td>{invigilator.name}</td>
                <td>
                  <button
                    onClick={() => handleSelectInvigilator(invigilator)}
                    className="button"
                  >
                    Sélectionner
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="3" className="text-center py-4">Aucun surveillant disponible</td>
            </tr>
          )}
        </tbody>
      </table>

      {selectedInvigilator && (
        <div className="selected-invigilator">
          <h3>Surveillant sélectionné :</h3>
          <p>{selectedInvigilator.name}</p>
        </div>
      )}

      <div className="mt-4">
        <button
          onClick={handleSubmitSelection}
          className="button"
        >
          Confirmer la sélection
        </button>
      </div>
    </div>
  );
};

export default AvailableInvigilators;
