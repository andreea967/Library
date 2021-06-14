
import React from "react";
import "./Navbar.css"

import {userService} from "../../services/UserService";

export default class Navbar extends React.Component {
    render() {
        return (
            <nav className="navbar w-100 p-3" >
                <div className="container-fluid ">
                    <div className="navbar-header">
                        <a className="navbar-brand" href="#">Biblioteca online</a>
                    </div>
                    <ul className="nav nav-tabs nav-default">
                        <li className="nav-item">
                            <a className="nav-link p-3 navElement" href="/home" >Home</a>
                        </li>
                        <li className="nav-item ">
                            <a className="nav-link p-3 navElement" href="/books"> Books</a>
                        </li>
                        <li className="nav-item ">
                            <a className="nav-link p-3 navElement" onClick={userService.logout}>Log out</a>
                        </li>
                    </ul>
                </div>
            </nav>
        );
    }
}
