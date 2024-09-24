import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';

export default function Flashcards() {
    const pathname = window.location.pathname;
    const segment = pathname.substring(pathname.lastIndexOf('/') + 1);

    const [cards, setCards] = useState();

    useEffect(() => {
        fetch('http://localhost:8080/flashcard/cards/' + segment)
            .then(res => res.json())
            .then(setCards)
            .catch(console.error);
    }, []);

    return (
        <>
            <h1>Flashcards</h1>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-2 g-4">
                {cards?.map(card => (
                    <div className="col" key={card.flashcardId}>
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">{card.frontData}</h5>

                            </div>
                            <div className="card-footer text-body-secondary text-center">
                                <Link to={`/delete/${card.flashcardId}`} className="btn btn-danger me-2">Delete</Link>
                                <Link to={`/update/${card.flashcardId}`} className="btn btn-info">Edit</Link>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </>
    );
}