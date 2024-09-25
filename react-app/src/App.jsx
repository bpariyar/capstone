import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css'
import Navbar from './components/NavBar';
import Home from './views/Home';
import Sets from './views/Sets';
import Flashcards from './views/Flashcards';
import FlashcardForm  from './views/FlashcardForm';
import SetForm from './views/SetForm';
import ConfirmDeleteSet from './views/ConfirmDeleteSet';

function App() {
    return (
        <Router>
            <Navbar />
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/sets' element={<Sets />} />
                <Route path={'/flashcard/cards/:flashcardSetId'} element={<Flashcards />}/>
                <Route path={'/add'} element={<FlashcardForm />}/>
                <Route path={'/sets/add'} element={<SetForm />}/>
                <Route path={'/sets/update/:flashcardSetId'} element={<SetForm />}/>
                <Route path={'/sets/delete/:flashcardSetId'} element={<ConfirmDeleteSet />}/>
            </Routes>
        </Router>
    )
}

export default App
