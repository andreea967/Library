import {BehaviorSubject} from 'rxjs';
import Cookies from 'universal-cookie'

const cookies = new Cookies()

function getLoggedInUser(){
    const item = sessionStorage.getItem('loggedInUser')
    if (item)
        return JSON.parse(item);
    return null
}

const loggedInUser = new BehaviorSubject(getLoggedInUser());

function login(jwt, cookie){
    sessionStorage.setItem('loggedInUser', JSON.stringify({"jwt": jwt}));
    cookies.set(' token-value', cookie);
}

function setBehaviourSubjectValue(value){
    loggedInUser.next(value)
}

function logout(){
    sessionStorage.clear()
    cookies.remove('token-value')
    window.location = "http://localhost:3000/"
}

export const userService = {
    loggedInUser: loggedInUser.asObservable(),
    get loggedInUserValue() {return loggedInUser.value},
    getLoggedInUserValue: getLoggedInUser,
    login: login,
    logout: logout,
    setBehaviourSubjectValue: setBehaviourSubjectValue
}