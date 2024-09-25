import { Link } from 'react-router-dom';
import { useState } from 'react';

const Flashcard = ({ card }) => {
    const [x, setX] = useState(true);

    return (
        <>
            <div >
                <div className="col" key={card.flashcardId}>
                    <div className="card">
                        <div className="card-body" onClick={() => { setX(!x); }}>
                            {x ?
                                <h5 className="card-title">{card.frontData}</h5> :
                                <h5 className="card-title">{card.backData}</h5>
                            }
                        </div>
                        <div className="card-footer text-body-secondary text-center">
                            <Link to={`/delete/${card.flashcardId}`} className="btn btn-danger me-2">Delete</Link>
                            <Link to={`/update/${card.flashcardId}`} className="btn btn-info">Edit</Link>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Flashcard