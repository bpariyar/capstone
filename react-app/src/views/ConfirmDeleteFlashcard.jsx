import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

export default function ConfirmDeleteFlashcard() { 
    const [flashcard, setFlashcard] = useState(null);
    const { flashcardId } = useParams();
    const navigate = useNavigate();
    const pathname = window.location.pathname;
    const segment = pathname.substring(pathname.lastIndexOf('/') + 1);

    function handleDelete() {
        fetch(`http://localhost:8080/flashcard/delete/${flashcard.flashcardId}`, {
            method: 'DELETE',
        })
            .then(res => {
                navigate(-1);
            })
            .catch(console.error);
    }

    useEffect(() => {
        if (flashcardId) {
            fetch(`http://localhost:8080/flashcard/${flashcardId}`)
                .then(res => res.json())
                .then(setFlashcard)
                .catch(console.error);
        }
    }, [flashcardId]);

    if (!flashcard) {
        return null;
    }

    return (
        <div>
            <h1>Delete Confirmation</h1>
            <p>Are you sure you want to delete this flashcard? </p>
                <p>Front Data: {flashcard.frontData}</p>
                <p>Back Data: {flashcard.backData}</p>
            <div>
                <button onClick={handleDelete} className='btn btn-danger me-2'>
                    Delete
                </button>
                <Link className='btn btn-secondary' to={-1}>
                    Cancel
                </Link>
            </div>
        </div>
    );
}