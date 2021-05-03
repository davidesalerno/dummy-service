import React from 'react';
import './App.css';
import './Chart.js'
import Timeline from './Chart.js';

import Navbar from 'react-bootstrap/Navbar';

class App extends React.Component {

  constructor(props) {
    super(props);

    console.log(process.env.REACT_APP_API_URI);
    var baseUrl = (process.env.REACT_APP_API_URI) ? "" + process.env.REACT_APP_API_URI : "http://localhost:8080";
    console.log("API_URI: " + baseUrl);

    this.state = {
      baseUrl: baseUrl,
      url: baseUrl,
      refresh: new Date().getMilliseconds()
    };
  }


  render() {
    return (
        <div className="App">
          <Navbar bg="dark" variant="dark" fixed="top">
            <Navbar.Brand><h1>Dummy Service UI</h1></Navbar.Brand>
          </Navbar>
          <Timeline url={this.state.url} refresh={this.state.refresh} />
        </div>
    );
  }
}

export default App;
