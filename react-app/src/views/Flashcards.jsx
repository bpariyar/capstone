import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import Flashcard from './Flashcard';

export default function Flashcards() {
    const pathname = window.location.pathname;
    const segment = pathname.substring(pathname.lastIndexOf('/') + 1);
    const [cards, setCards] = useState();

    let navigate = useNavigate();
    const routeChange = () => {
        let path = '/add';
        navigate(path);
    }

    useEffect(() => {
        fetch('http://localhost:8080/flashcard/cards/' + segment)
            .then(res => res.json())
            .then(setCards)
            .catch(console.error);
    }, []);

    return (
        <>
            <h1 style={{ paddingBottom: "10px" }}>
                Flashcards
            </h1>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-2 g-4">
                {cards?.map(card => {
                    return <Flashcard card={card} key={card.flashcardId} />
                })}
            </div>
            <div className='mb-3' style={{ paddingTop: "15px" }}>
                <button type="button"
                    onClick={routeChange}>Add a Flashcard</button>
            </div>
        </>
    );
}