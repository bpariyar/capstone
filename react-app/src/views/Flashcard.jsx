import { Link } from 'react-router-dom';
import { useState } from 'react';
import ReactCardFlip from 'react-card-flip';

const Flashcard = ({ card }) => {
    const [isFlipped, setIsFlipped] = useState(false);

    function flipCard() {
        setIsFlipped(!isFlipped);
    }

    return (
        <>
            <div >
                <div className="col" key={card.flashcardId}>
                    <ReactCardFlip flipDirection='horizontal' isFlipped={isFlipped}>
                    <div className="card" onClick={flipCard}>
                        <div className="card-body">
                                <h5 className="card-title">{card.frontData}</h5> 
                        </div>
                        <div>
                            <Link to={`/delete/${card.flashcardId}`} className="btn btn-danger me-2">Delete</Link>
                            <Link to={`/update/${card.flashcardId}`} className="btn btn-info">Edit</Link>
                        </div>
                    </div>
                    <div className="card" onClick={flipCard}>
                        <div className="card-body">
                                <h5 className="card-title">{card.backData}</h5>
                        </div>
                        <div>
                            <Link to={`/delete/${card.flashcardId}`} className="btn btn-danger me-2">Delete</Link>
                            <Link to={`/update/${card.flashcardId}`} className="btn btn-info">Edit</Link>
                        </div>
                    </div>
                    </ReactCardFlip>
                </div>
            </div>
        </>
    );
}

export default Flashcard