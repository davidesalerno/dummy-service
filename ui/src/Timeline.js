import { FlowChartWithState } from "@mrblenny/react-flow-chart";
import React, {useEffect, useState} from 'react';
import { processData } from './Data';

import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import {getDataApi} from "./Utility";

function NodeInnerCustom({ node, children, ...otherProps }){
    let className = "node";

    if (node.properties.response !== 200 && node.properties.response !== 0) {
        className = "node-error";
    }

    const ips = [];

    // if the node has ip addresses create individual elements for them
    if(node.properties.ip_addresses !== undefined) {
        for(var n=0;n<node.properties.ip_addresses.length;n++){
            ips.push(<div key={n}>{node.properties.ip_addresses[n]}</div>);
        }
    }

    return (
        <Container {...otherProps} className={className} key={node.properties.name}>
            <Row>
                <Col className="node-header">{node.properties.name}</Col>
            </Row>
            <Row>
                <Col className="node-uri">{node.properties.upstream_address}</Col>
            </Row>
            <Row>
                <Col>
                    <Container>
                        <Row>
                            <Col className="node-key" md={5}>Request URI</Col>
                            <Col className="node-value" md={1}>{node.properties.uri}</Col>
                        </Row>
                        <Row>
                            <Col className="node-key" md={5}>IP Address</Col>
                            <Col className="node-value" md={1}>{ips}</Col>
                        </Row>
                        <Row>
                            <Col className="node-key" md={5}>Duration</Col>
                            <Col className="node-value" md={1}>{node.properties.duration}</Col>
                        </Row>
                        <Row>
                            <Col className="node-key" md={5}>Type</Col>
                            <Col className="node-value" md={1}>{node.properties.type}</Col>
                        </Row>
                        <Row>
                            <Col className="node-key" md={5}>Response</Col>
                            <Col className="node-value" md={1}>{node.properties.response}</Col>
                        </Row>
                    </Container>
                </Col>
            </Row>
        </Container>
    )
}

export default function Timeline(props){

    const [url] = useState(props.url);
    const [refresh] = useState(props.refresh);
    const [loaded, setLoaded] = useState(false);
    const [data, setData] = useState();

    useEffect(() => {
        let mounted = true
        getDataApi(url)
            .then(
                (result) => {
                    if(mounted){
                        console.log("response from API:", result);
                        setData(processData(result));
                        setLoaded(true);
                    }
                },
                (error) => {
                    if(mounted){
                        setLoaded(false);
                        console.log(error);
                    }
                }
            )
        return function cleanup() {
            mounted = false
        }
    }, [url,refresh]);

    if (loaded === true) {
        return <FlowChartWithState initialValue={data} Components={{ NodeInner: NodeInnerCustom }} />
    }

    return null;

}
