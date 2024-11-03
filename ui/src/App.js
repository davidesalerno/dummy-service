import React, {useState, useEffect} from 'react';
import './App.css';
import Timeline from './Timeline.js';
import Advanced from './Advanced.js';
import { BrowserRouter, Route, Routes, Link} from 'react-router-dom';
import {getDataApi} from "./Utility";
import { css } from "@emotion/react";
import ClipLoader from "react-spinners/ClipLoader";

import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';

export default function App(props){
    const [url] = useState((process.env.REACT_APP_API_URI) ? "" + process.env.REACT_APP_API_URI : "http://localhost:8080");
    const [name, setName] = useState((process.env.REACT_SERVICE_NAME) ? "" + process.env.REACT_SERVICE_NAME : "Dummy Service");
    const [refresh]= useState(new Date().getMilliseconds());
    const [loading, setLoading]= useState(true);

    const override = css`
      display: block;
      margin: 20% auto;
      border-color: #343a40;
      border-width: 10px;
    `;

    console.log("API_URI: " + url);
    console.log("SERVICE NAME: " + name);

    useEffect(() => {
        let mounted = true
        getDataApi(url)
            .then(
                (result) => {
                    if(mounted){
                        setName(result.name);
                        setLoading(false);
                    }
                },
                (error) => {
                    if(mounted){
                        setLoading(false);
                        console.log(error);
                    }
                }
            )
        return function cleanup() {
            mounted = false
        }
    }, [url]);

    return (
        (loading) ? <ClipLoader loading={loading} size={150} css={override} /> :
            <div className="App">
                <BrowserRouter path="/ui">
                    <Navbar bg="dark" variant="dark" sticky="top">
                        <Navbar.Brand><h1>{name} UI</h1></Navbar.Brand><Navbar.Toggle aria-controls="basic-navbar-nav"/>
                        <Navbar.Collapse id="basic-navbar-nav">
                            <Nav className="mr-auto">
                                <Nav.Link as={Link} to="/">Home</Nav.Link>
                                <Nav.Link as={Link} to="/advanced">Advanced</Nav.Link>
                            </Nav>
                        </Navbar.Collapse>
                    </Navbar>
                    <Routes>
                        <Route exact path="/" element={<Timeline url={url} refresh={refresh}/>} />
                        <Route path="/advanced" element={ <Advanced url={url}/>} />
                    </Routes>
                </BrowserRouter>
            </div>

        );
};
