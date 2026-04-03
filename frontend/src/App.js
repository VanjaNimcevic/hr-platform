import React, { useState } from 'react';
import CandidateList from './components/CandidateList';
import CandidateForm from './components/CandidateForm';
import SkillForm from './components/SkillForm';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {

    const [activeTab, setActiveTab] = useState('candidates');

    return (
        <div className="container mt-4">


            <h1 className="text-center mb-4">HR Platform</h1>

            <ul className="nav nav-tabs mb-4">
                <li className="nav-item">
                    <button
                        className={`nav-link ${activeTab === 'candidates' ? 'active' : ''}`}
                        onClick={() => setActiveTab('candidates')}
                    >
                        Kandidati
                    </button>
                </li>
                <li className="nav-item">
                    <button
                        className={`nav-link ${activeTab === 'addCandidate' ? 'active' : ''}`}
                        onClick={() => setActiveTab('addCandidate')}
                    >
                        Dodaj Kandidata
                    </button>
                </li>
                <li className="nav-item">
                    <button
                        className={`nav-link ${activeTab === 'addSkill' ? 'active' : ''}`}
                        onClick={() => setActiveTab('addSkill')}
                    >
                        Dodaj Veštinu
                    </button>
                </li>
            </ul>

            {activeTab === 'candidates' && <CandidateList />}
            {activeTab === 'addCandidate' && <CandidateForm />}
            {activeTab === 'addSkill' && <SkillForm />}

        </div>
    );
}

export default App;