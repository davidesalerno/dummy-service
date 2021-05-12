import React from 'react';
import './App.css';
import './Chart.js'
import Timeline from './Chart.js';
import Advanced from './Advanced.js';
import { BrowserRouter, Route, Switch, Link} from 'react-router-dom';

import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';

class App extends React.Component {

  constructor(props) {
    super(props);
    var baseUrl = (process.env.REACT_APP_API_URI) ? "" + process.env.REACT_APP_API_URI : "http://localhost:8080";
    var serviceName = (process.env.REACT_SERVICE_NAME) ? "" + process.env.REACT_SERVICE_NAME : "Dummy Service";
    console.log("API_URI: " + baseUrl);
    console.log("SERVICE NAME: " + serviceName);

      this.state = {
      baseUrl: baseUrl,
      url: baseUrl,
      refresh: new Date().getMilliseconds(),
      serviceName: serviceName
    };
  }


  render() {
    return (
        <div className="App">
            <BrowserRouter basename="/ui">
            <Navbar bg="dark" variant="dark" sticky="top">
                <Navbar.Brand><h1>{this.state.serviceName} UI</h1></Navbar.Brand><Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link as={Link} to="/">Home</Nav.Link>
                        <Nav.Link as={Link} to="/advanced">Advanced</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
                <Switch>
                    <Route exact path="/">
                        <Timeline url={this.state.url} refresh={this.state.refresh}/>
                    </Route>
                    <Route path="/advanced">
                        <Advanced url={this.state.url}/>
                    </Route>
                </Switch>
            </BrowserRouter>
        </div>
    );
  }
}

export default App;
