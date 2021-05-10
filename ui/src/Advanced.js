import React, {useState} from 'react';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import Alert from 'react-bootstrap/Alert'
import ListServices from "./ListServices";
import {useDataApi} from "./Utility";

export default function Advanced(props) {
    const [bulk, setBulk] = useState(5);
    const [{ data, loaded, error }, doFetch, url] = useDataApi();
    const baseApiUrl = props.url ? props.url : 'https://run.mocky.io/v3/42b5958e-58d2-4647-8f80-a02a085a478e';

    function handleBulkChange(e) {
        setBulk(e.target.value);
    }

    function handleClickGo(e) {
        e.preventDefault();
        const now = Date.now();
        doFetch(`${baseApiUrl}?bulk=${bulk}&ts=${now}`);
    }

    return <Container fluid={"md"} className='mt-5'>
        <Row className='mt-5'>
            <Form inline>
                <Form.Group controlId="formBasicEmail" className='mr-2'>
                    <Form.Label className='mr-2'>Bulk calls:</Form.Label>
                    <Form.Control type="number" placeholder="Enter a number" value={bulk} onChange={handleBulkChange}/>
                </Form.Group>
                <Button variant="primary" type="submit" onClick={handleClickGo}>
                    Go
                </Button>
            </Form>
        </Row>

        {error ? (<Alert variant={"danger"} className='mt-5'>Something went wrong...</Alert>) :
            (url ? (
                        !loaded ? (
                            <Alert variant={"info"} className='mt-5'>Loading...</Alert>
                        ) : (
                            <Row className='mt-5'>
                                <ListServices services={data.upstream_calls}/>
                            </Row>
                        )
                    ) :
                <Alert variant={"primary"} className='mt-5'>Nothing to show, please put the desired number of bulk calls per service to perform and hit Go!</Alert>
            )
        }
    </Container>;
}