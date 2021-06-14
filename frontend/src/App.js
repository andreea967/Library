import React from 'react';
import {BrowserRouter, Route, Switch, Redirect} from 'react-router-dom';
import './App.css';
import BookPage from "./components/BookPage/BookPage"
import HomePage from "./components/HomePage/HomePage"
import LoginPage from "./components/LoginPage/LoginPage"
import Navbar from "./components/Navbar/Navbar"
import {userService} from "./services/UserService";

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedInUser: userService.getLoggedInUserValue()
        }
    }

    componentDidMount() {
        userService.loggedInUser.subscribe(user => this.setState({loggedInUser: user}));
    }

    render() {
        if (!this.state || !this.state.loggedInUser){
            return <LoginPage/>
        }

        return (
            <div className="container">
                <Navbar />
                <BrowserRouter>
                    <Switch>
                <Route exact path="/">
                    <Redirect to="/home"/>
                </Route>
                <Route path="/home">
                    <HomePage />
                </Route>
                <Route path="/books">
                    <BookPage />
                </Route>
                    </Switch>
                </BrowserRouter>
            </div>
        );
  }
}

export default App;
