import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css'
import Navbar from './components/NavBar';
import Home from './views/Home';
import Sets from './views/Sets';
import Flashcards from './views/Flashcards';

function App() {
    return (
        <Router>
            <Navbar />
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/sets' element={<Sets />} />
                <Route path={'/flashcard/cards/:flashcardSetId'} element={<Flashcards />}/>
            </Routes>
        </Router>
    )
}

export default App
