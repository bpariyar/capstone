import { NavLink } from 'react-router-dom';

export default function NavBar() {
    return (
        <header className='mb-3'>
            <nav className='navbar navbar-expand'>
                <div className='d-flex'>
                    <ul className='navbar-nav'>
                        <li className='nav-item'>
                            <NavLink className='nav-link' to='/'>
                                Home
                            </NavLink>
                        </li>
                        <li className='nav-item'>
                            <NavLink className='nav-link' to='/sets'>
                                Flashcard Sets
                            </NavLink>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
    );
}