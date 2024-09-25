import { Link, useNavigate, useParams, useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';

const INITIAL_FLASHCARD = {
    flashcardId: 0,
    frontData: '',
    backData: '',
};

export default function FlashcardForm() {
    const [flashcard, setFlashcard] = useState(INITIAL_FLASHCARD);
    const navigate = useNavigate();
    const { flashcardId } = useParams();
    const location = useLocation(); 
    const errorDetail = location.state?.error?.message ?? 'Unavailable';

    useEffect(() => {
        if (flashcardId) {
            fetch(`http://localhost:8080/flashcard/${flashcardId}`)
                .then(res => res.json())
                .then(setFlashcard)
                .catch(console.error);
        }
    }, [flashcardId]);

    const [errors, setErrors] = useState([]);

    function handleChange(event) {
        const updatedFlashcard = { ...flashcard };
        updatedFlashcard[event.target.name] = event.target.value;
        setFlashcard(updatedFlashcard);
        console.log(updatedFlashcard);
    }

    function doCreate() {
        const config = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(flashcard)
        }

        fetch("http://localhost:8080/flashcard/add", config)
            .then(response => {
                if (response.ok) {
                    navigate(-1);
                } else {
                    return response.json();
                }
            })
            .then(errs => {
                if (errs) {
                    return Promise.reject(errs);
                }
            })
            .catch(errs => {
                if (errs.length) {
                    setErrors(errs);
                } else {
                    setErrors([errs]);
                }
            });
    }

    function doUpdate() {
        fetch(`http://localhost:8080/flashcard/update/${flashcard.flashcardId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(flashcard),
        })
            .then(response => {
                if (response.ok) {
                    navigate(-1);
                } else {
                    return response.json();
                }
            })
            .then(errs => {
                if (errs) {
                    return (
                    <p>Error detail: {errorDetail}</p>
                    )
                }
            })
            .catch(errs => {
                if (errs.length) {
                    setErrors(errs);
                } else {
                    setErrors([errs]);
                }
            });
    }


    function handleSubmit(event) {
        event.preventDefault();
        if (flashcard.flashcardId > 0) {
            doUpdate();
        } else {
            doCreate();
        }
    }

    return (
        <div className='container'>
            <form onSubmit={handleSubmit}>
            <h1>{flashcard.flashcardId > 0 ? 'Update A Flashcard' : 'Add A Flashcard'}</h1>
            <div className='mb-3'>
                    <label className='form-label' htmlFor='FrontData'>
                        Front Data
                    </label>
                    <input
                        className='form-control'
                        type='text'
                        id='frontData'
                        name='frontData'
                        value={flashcard.frontData}
                        onChange={handleChange}
                    />
                </div>
                <div className='mb-3'>
                    <label className='form-label' htmlFor='BackData'>
                        Back Data
                    </label>
                    <input
                        className='form-control'
                        type='text'
                        id='backData'
                        name='backData'
                        value={flashcard.backData}
                        onChange={handleChange}
                    />
                </div>
                <div className='mb-3'>
                    <button type='submit' className='btn btn-primary me-2'>
                        Submit
                    </button>
                    <Link className='btn btn-secondary' to={-1}>
                        Cancel
                    </Link>
                </div>
            </form>
        </div>
    );
}