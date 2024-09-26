import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css'
import NavBar from './components/NavBar';
import Home from './views/Home';
import Sets from './views/Sets';
import Flashcards from './views/Flashcards';
import FlashcardForm  from './views/FlashcardForm';
import SetForm from './views/SetForm';
import ConfirmDeleteSet from './views/ConfirmDeleteSet';
import ConfirmDeleteFlashcard from './views/ConfirmDeleteFlashcard';

function App() {
    return (
        <Router>
            <NavBar />
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/sets' element={<Sets />} />
                <Route path={'/flashcard/cards/:flashcardSetId'} element={<Flashcards />}/>
                <Route path={'/sets/add'} element={<SetForm />}/>
                <Route path={'/sets/update/:flashcardSetId'} element={<SetForm />}/>
                <Route path={'/sets/delete/:flashcardSetId'} element={<ConfirmDeleteSet />}/>
                <Route path={'/add/:flashcardSetId'} element={<FlashcardForm />}/>
                <Route path={'/update/:flashcardId'} element={<FlashcardForm />}/>
                <Route path={'/delete/:flashcardId'} element={<ConfirmDeleteFlashcard />}/>
            </Routes>
        </Router>
    )
}

export default App
