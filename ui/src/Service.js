import React from 'react';
import Table from 'react-bootstrap/Table'
import Row from "react-bootstrap/Row";

export default function Service(props) {
    const {name, calls} = props;
    const listUpstreams = calls.map((upstream, index) =>
        <tr key={upstream.name+"-"+index}>
            <td>{index}</td>
            <td>{upstream.name}</td>
            <td>{upstream.body}</td>
            <td>{upstream.code}</td>
        </tr>
    );
    return <Row>
        <h4>{name}</h4>
        <Table striped bordered hover>
            <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Message</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            {listUpstreams}
            </tbody>
        </Table>
    </Row>
        ;
}
