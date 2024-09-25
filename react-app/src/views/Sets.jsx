import { NavLink, Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";

export default function Sets() {
    const [sets, setSets] = useState();
    let navigate = useNavigate();

    const routeChange = () => {
        let path = '/sets/add';
        navigate(path);
    }

    useEffect(() => {
        fetch('http://localhost:8080/flashcard/set/all')
            .then(res => res.json())
            .then(setSets)
            .catch(console.error);
    }, []);

    return (
        <>
            <h1>Flashcard Sets</h1>
            <div className='mb-3'>
                <button type="button"
                    onClick={routeChange}>Add a Flashcard Set</button>
            </div>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-2 g-4">
                {sets?.map(set => (
                    <div key={set.flashcardSetId}>
                        <NavLink to={`/flashcard/cards/${set.flashcardSetId}`} key={set.flashcardSetId} >
                            <div className="col" key={set.flashcardSetId}>
                                <div className="card">
                                    <div className="card-body">
                                        <h5 className="card-title">{set.title}</h5>
                                    </div>
                                </div>
                            </div>
                        </NavLink>
                        <div className="card-footer text-body-secondary text-center" style={{ paddingTop: "8px" }}>
                            <Link to={`/sets/delete/${set.flashcardSetId}`} className="btn btn-danger me-2">Delete</Link>
                            <Link to={`/sets/update/${set.flashcardSetId}`} className="btn btn-info">Edit</Link>
                        </div>
                    </div>
                ))}
            </div>
        </>
    );
}