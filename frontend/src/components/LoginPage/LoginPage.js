import React from 'react';
import "./LoginPage.css"

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHome, faUser, faKey} from '@fortawesome/free-solid-svg-icons'
import {userService} from "../../services/UserService";

export default class LoginPage extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: ''
        }
        this.handleLogin = this.handleLogin.bind(this)
    }

    handleLogin(e){
        console.log('Username: ' + this.state.username);
        console.log('Password: ' + this.state.password);
        if (this.state.username === "" || this.state.password === ""){
            e.preventDefault();
            alert("Username si parola nu trebuie sa fie goale");
        }
        else {
            e.preventDefault();
            fetch('http://localhost:5000/api/auth', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(this.state)
            })
                .then(data => data.json())
                .then((data) => {

                if (data.msg !== undefined){
                    e.preventDefault();
                    alert("Invalid credentials");
                } else {
                    userService.login(data.token, data.cookie);
                    userService.setBehaviourSubjectValue(data.token);
                }
            })
        }
    }

    render(){
        return <div className="login_container">
        <div className="d-flex justify-content-center h-100">
                <div className="card">
                    <div className="card-header justify-content-center text-center">
                        <h3>Sign In</h3>

                    </div>
                    <div className="card-body">
                        <form onSubmit={this.handleLogin}>
                            <div className="input-group form-group">
                                <div className="input-group-prepend">
                                    <span className="input-group-text"><FontAwesomeIcon icon={faUser} /></span>
                                </div>
                                <input type="text" className="form-control" placeholder="username" onChange={e => this.setState({username: e.target.value})}/>

                            </div>
                            <div className="input-group form-group">
                                <div className="input-group-prepend">
                                    <span className="input-group-text"><FontAwesomeIcon icon={faKey} /></span>
                                </div>
                                <input type="password" className="form-control" placeholder="password" onChange={e => this.setState({password: e.target.value})}/>
                            </div>
                            <div className="form-group justify-content-center text-center ">
                                <button type="submit" className="btn btn-danger w-100" >Login</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    }
}