import { Link, useNavigate, useParams, useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';

const INITIAL_SET = {
    flashcardSetId: 0,
    title: '',
};

export default function SetForm() {
    const [set, setSet] = useState(INITIAL_SET);
    const navigate = useNavigate();
    const { flashcardSetId } = useParams();
    const location = useLocation(); 
    const errorDetail = location.state?.error?.message ?? 'Unavailable';


    useEffect(() => {
        if (flashcardSetId) {
            fetch(`http://localhost:8080/flashcard/set/${flashcardSetId}`)
                .then(res => res.json())
                .then(setSet)
                .catch(console.error);
        }
    }, [flashcardSetId]);

    const [errors, setErrors] = useState([]);

    function handleChange(event) {
        const updatedSet = { ...set };
        updatedSet[event.target.name] = event.target.value;
        setSet(updatedSet);
        console.log(updatedSet);
    }

    function doCreate() {
        const config = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(set)
        }

        fetch("http://localhost:8080/flashcard/set/add", config)
            .then(response => {
                if (response.ok) {
                    navigate('/sets');
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
        fetch(`http://localhost:8080/flashcard/set/update/${set.flashcardSetId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(set),
        })
            .then(response => {
                if (response.ok) {
                    navigate('/sets');
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
        if (set.flashcardSetId > 0) {
            doUpdate();
        } else {
            doCreate();
        }
    }

    return (
        <div className='container'>
            <form onSubmit={handleSubmit}>
            <h1>{set.flashcardSetId > 0 ? 'Update A Flashcard Set' : 'Add A Flashcard Set'}</h1>
            <div className='mb-3'>
                    <label className='form-label' htmlFor='Title'>
                        Title
                    </label>
                    <input
                        className='form-control'
                        type='text'
                        id='title'
                        name='title'
                        value={set.title}
                        onChange={handleChange}
                    />
                </div>
                <div className='mb-3'>
                    <button type='submit' className='btn btn-primary me-2'>
                        Submit
                    </button>
                    <Link className='btn btn-secondary' to='/sets'>
                        Cancel
                    </Link>
                </div>
            </form>
        </div>
    );
}