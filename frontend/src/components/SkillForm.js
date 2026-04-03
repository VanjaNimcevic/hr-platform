import React, { useState, useEffect } from 'react';
import axios from 'axios';

function SkillForm() {
    const [skillName, setSkillName] = useState('');
    const [skills, setSkills] = useState([]);

    useEffect(() => {
        fetchSkills();
    }, []);

    const fetchSkills = () => {
        axios.get('http://localhost:8080/api/skills')
            .then(response => setSkills(response.data))
            .catch(() => alert('Greška pri učitavanju veština!'));
    };

    const handleSubmit = () => {
        if (!skillName) {
            alert('Unesite naziv veštine!');
            return;
        }
        axios.post('http://localhost:8080/api/skills', {
            skillName: skillName
        })
            .then(() => {
                alert('Veština uspešno dodata!');
                setSkillName('');
                fetchSkills(); // osvežavamo listu
            })
            .catch(() => {
                alert('Greška — veština možda već postoji!');
            });
    };

    const deleteSkill = (id) => {
        axios.delete(`http://localhost:8080/api/skills/${id}`)
            .then(() => {
                fetchSkills(); // osvežavamo listu
            })
            .catch(() => alert('Greška pri brisanju veštine!'));
    };

    return (
        <div className="mt-3">
            <div className="card p-4 mb-4">
                <h2 className="mb-3">Dodaj Veštinu</h2>
                <div className="mb-3">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Naziv veštine (npr. Java)"
                        value={skillName}
                        onChange={(e) => setSkillName(e.target.value)}
                    />
                </div>
                <button className="btn btn-success" onClick={handleSubmit}>
                    Dodaj
                </button>
            </div>

            {/* Lista svih veština */}
            <div className="card p-4">
                <h2 className="mb-3">Sve Veštine</h2>
                <table className="table table-bordered table-hover">
                    <thead className="table-primary">
                    <tr>
                        <th>ID</th>
                        <th>Naziv</th>
                        <th>Akcije</th>
                    </tr>
                    </thead>
                    <tbody>
                    {skills.map(skill => (
                        <tr key={skill.id}>
                            <td>{skill.id}</td>
                            <td>{skill.skillName}</td>
                            <td>
                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => deleteSkill(skill.id)}
                                >
                                    Obriši
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                {skills.length === 0 && (
                    <p className="text-center text-muted">Nema veština</p>
                )}
            </div>
        </div>
    );
}

export default SkillForm;