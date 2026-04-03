import React, { useState } from 'react';
import axios from 'axios';

function CandidateForm() {

    const [fullName, setFullName] = useState('');
    const [dob, setDob] = useState('');
    const [phone, setPhone] = useState('');
    const [email, setEmail] = useState('');

    const handleSubmit = () => {

        if (!fullName || !dob || !email || !phone) {
            alert('Ime, datum rođenja, email i telefon su obavezni!');
            return;
        }

        axios.post('http://localhost:8080/api/candidates', {
            fullName: fullName,
            dob: dob,
            phone: phone,
            email: email
        })
            .then(() => {
                alert('Kandidat uspešno dodat!');
                setFullName('');
                setDob('');
                setPhone('');
                setEmail('');
            })
            .catch(() => {
                alert('Greška — kandidat možda već postoji!');
            });
    };

    return (
        <div className="card p-4 mt-3">
            <h2 className="mb-3">Dodaj Kandidata</h2>

            <div className="mb-3">
                <label className="form-label">Ime i prezime *</label>
                <input
                    type="text"
                    className="form-control"
                    placeholder="npr. Marko Marković"
                    value={fullName}
                    onChange={(e) => setFullName(e.target.value)}
                />
            </div>

            <div className="mb-3">
                <label className="form-label">Datum rođenja *</label>
                <input
                    type="date"
                    className="form-control"
                    value={dob}
                    onChange={(e) => setDob(e.target.value)}
                />
            </div>

            <div className="mb-3">
                <label className="form-label">Broj telefona</label>
                <input
                    type="text"
                    className="form-control"
                    placeholder="npr. 0641234567"
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                />
            </div>

            <div className="mb-3">
                <label className="form-label">Email *</label>
                <input
                    type="email"
                    className="form-control"
                    placeholder="npr. marko@gmail.com"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>

            <button className="btn btn-success" onClick={handleSubmit}>
                Dodaj Kandidata
            </button>
        </div>
    );
}

export default CandidateForm;