import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Modal, Button } from 'react-bootstrap';

function CandidateList() {

    const [candidates, setCandidates] = useState([]);
    const [searchName, setSearchName] = useState('');
    const [searchSkill, setSearchSkill] = useState('');
    const [showModal, setShowModal] = useState(false);
    const [selectedCandidate, setSelectedCandidate] = useState(null);
    const [skillId, setSkillId] = useState('');
    const [allSkills, setAllSkills] = useState([]);
    const [showEditModal, setShowEditModal] = useState(false);
    const [editCandidate, setEditCandidate] = useState(null);

    useEffect(() => {
        fetchCandidates();
    }, []);

    const fetchCandidates = () => {
        axios.get('http://localhost:8080/api/candidates')
            .then(response => setCandidates(response.data))
            .catch(() => alert('Greška pri učitavanju kandidata!'));
    };

    const searchByName = () => {
        if (!searchName) {
            fetchCandidates();
            return;
        }
        axios.get(`http://localhost:8080/api/candidates/search?name=${searchName}`)
            .then(response => setCandidates(response.data))
            .catch(() => alert('Greška pri pretrazi!'));
    };

    const searchBySkill = () => {
        if (!searchSkill) {
            fetchCandidates();
            return;
        }
        axios.get(`http://localhost:8080/api/candidates/search/skills?skillNames=${searchSkill}`)
            .then(response => setCandidates(response.data))
            .catch(() => alert('Greška pri pretrazi!'));
    };

    const deleteCandidate = (id) => {
        console.log('Brisanje kandidata sa ID:', id);  // ← dodaj ovo
        if (window.confirm('Da li ste sigurni?')) {
            axios.delete(`http://localhost:8080/api/candidates/${id}`)
                .then(() => fetchCandidates())
                .catch((error) => {
                    console.log('Greška:', error.response);  // ← i ovo
                    alert('Greška pri brisanju!');
                });
        }
    };
    const updateCandidate = () => {
        axios.put(`http://localhost:8080/api/candidates/${editCandidate.id}`, {
            fullName: editCandidate.fullName,
            dob: editCandidate.dob,
            phone: editCandidate.phone,
            email: editCandidate.email
        })
            .then(() => {
                fetchCandidates();
                setShowEditModal(false);
            })
            .catch(() => alert('Greška pri ažuriranju kandidata!'));
    };

    const openModal = (candidate) => {
        setSelectedCandidate(candidate);
        setShowModal(true);
        axios.get('http://localhost:8080/api/skills')
            .then(response => setAllSkills(response.data))
            .catch(() => alert('Greška pri učitavanju veština!'));
    };

    const addSkillToCandidate = () => {
        if (!skillId) {
            alert('Izaberite veštinu!');
            return;
        }
        axios.post(`http://localhost:8080/api/candidates/${selectedCandidate.id}/skills/${skillId}`)
            .then(() => {
                alert('Veština dodata!');
                fetchCandidates();
                setShowModal(false);
            })
            .catch(() => alert('Greška pri dodavanju veštine!'));
    };

    const removeSkillFromCandidate = (skillId) => {
        axios.delete(`http://localhost:8080/api/candidates/${selectedCandidate.id}/skills/${skillId}`)
            .then(() => {
                alert('Veština uklonjena!');
                fetchCandidates();
                setShowModal(false);
            })
            .catch(() => alert('Greška pri uklanjanju veštine!'));
    };

    return (
        <div className="mt-3">
            <h2>Lista Kandidata</h2>

            <div className="row mb-3">
                <div className="col">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Pretraži po imenu..."
                        value={searchName}
                        onChange={(e) => setSearchName(e.target.value)}
                    />
                </div>
                <div className="col-auto">
                    <button className="btn btn-primary" onClick={searchByName}>
                        Pretraži
                    </button>
                </div>
                <div className="col-auto">
                    <button className="btn btn-secondary" onClick={fetchCandidates}>
                        Prikaži sve
                    </button>
                </div>
            </div>

            <div className="row mb-3">
                <div className="col">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Pretraži po veštini, koristiti zarez za vise veština (primer:Java,React)"
                        value={searchSkill}
                        onChange={(e) => setSearchSkill(e.target.value)}
                    />
                </div>
                <div className="col-auto">
                    <button className="btn btn-primary" onClick={searchBySkill}>
                        Pretraži po veštini
                    </button>
                </div>
            </div>

            <table className="table table-bordered table-hover">
                <thead className="table-primary">
                <tr>
                    <th>ID</th>
                    <th>Ime i prezime</th>
                    <th>Datum rođenja</th>
                    <th>Telefon</th>
                    <th>Email</th>
                    <th>Veštine</th>
                    <th>Akcije</th>
                </tr>
                </thead>
                <tbody>
                {candidates.map(candidate => (
                    <tr key={candidate.id}>
                        <td>{candidate.id}</td>
                        <td>{candidate.fullName}</td>
                        <td>{candidate.dob}</td>
                        <td>{candidate.phone}</td>
                        <td>{candidate.email}</td>
                        <td>
                            {candidate.skills.map(skill => (
                                <span key={skill.id} className="badge bg-success me-1">
                                        {skill.skillName}
                                    </span>
                            ))}
                        </td>
                        <td>
                            <button
                                className="btn btn-primary btn-sm me-2"
                                onClick={() => openModal(candidate)}
                            >
                                Veštine
                            </button>
                            <button
                                className="btn btn-warning btn-sm me-2"
                                onClick={() => {
                                    setEditCandidate(candidate);
                                    setShowEditModal(true);
                                }}
                            >
                                Izmeni
                            </button>
                            <button
                                className="btn btn-danger btn-sm"
                                onClick={() => deleteCandidate(candidate.id)}
                            >
                                Obriši
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            {candidates.length === 0 && (
                <p className="text-center text-muted">Nema kandidata</p>
            )}

            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>
                        Veštine — {selectedCandidate?.fullName}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <h6>Trenutne veštine:</h6>
                    {selectedCandidate?.skills.length === 0 && (
                        <p className="text-muted">Nema veština</p>
                    )}
                    {selectedCandidate?.skills.map(skill => (
                        <span key={skill.id} className="badge bg-success me-1 mb-2">
                            {skill.skillName}
                            <button
                                className="btn-close btn-close-white ms-1"
                                style={{ fontSize: '0.5rem' }}
                                onClick={() => removeSkillFromCandidate(skill.id)}
                            />
                        </span>
                    ))}

                    <h6 className="mt-3">Dodaj veštinu:</h6>
                    <select
                        className="form-select"
                        value={skillId}
                        onChange={(e) => setSkillId(e.target.value)}
                    >
                        <option value="">Izaberi veštinu...</option>
                        {allSkills.map(skill => (
                            <option key={skill.id} value={skill.id}>
                                {skill.skillName}
                            </option>
                        ))}
                    </select>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>
                        Zatvori
                    </Button>
                    <Button variant="primary" onClick={addSkillToCandidate}>
                        Dodaj Veštinu
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showEditModal} onHide={() => setShowEditModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Izmeni Kandidata</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="mb-3">
                        <label className="form-label">Ime i prezime</label>
                        <input
                            type="text"
                            className="form-control"
                            value={editCandidate?.fullName || ''}
                            onChange={(e) => setEditCandidate({
                                ...editCandidate,
                                fullName: e.target.value
                            })}
                        />
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Datum rođenja</label>
                        <input
                            type="date"
                            className="form-control"
                            value={editCandidate?.dob || ''}
                            onChange={(e) => setEditCandidate({
                                ...editCandidate,
                                dob: e.target.value
                            })}
                        />
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Telefon</label>
                        <input
                            type="text"
                            className="form-control"
                            value={editCandidate?.phone || ''}
                            onChange={(e) => setEditCandidate({
                                ...editCandidate,
                                phone: e.target.value
                            })}
                        />
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Email</label>
                        <input
                            type="email"
                            className="form-control"
                            value={editCandidate?.email || ''}
                            onChange={(e) => setEditCandidate({
                                ...editCandidate,
                                email: e.target.value
                            })}
                        />
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowEditModal(false)}>
                        Odustani
                    </Button>
                    <Button variant="primary" onClick={updateCandidate}>
                        Sačuvaj
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default CandidateList;