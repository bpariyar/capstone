import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

export default function ConfirmDelete() {
    const [set, setSet] = useState(null);
    const { flashcardSetId } = useParams();
    const navigate = useNavigate();

    function handleDelete() {
        fetch(`http://localhost:8080/flashcard/set/delete/${set.flashcardSetId}`, {
            method: 'DELETE',
        })
            .then(res => {
                navigate('/sets');
            })
            .catch(console.error);
    }

    useEffect(() => {
        if (flashcardSetId) {
            fetch(`http://localhost:8080/flashcard/set/${set.flashcardSetId}`)
                .then(res => res.json())
                .then(setSet)
                .catch(console.error);
        }
    }, [flashcardSetId]);

    if (!set) {
        return null;
    }

    return (
        <div>
            <h1>Delete Confirmation</h1>
            <p>Are you sure you want to delete this flashcard set? </p>
            <ul>
                <li>Title: {set.title}</li>
            </ul>
            <div>
                <button onClick={handleDelete} className='btn btn-danger me-2'>
                    Delete
                </button>
                <Link className='btn btn-secondary' to='/sets'>
                    Cancel
                </Link>
            </div>
        </div>
    );
}