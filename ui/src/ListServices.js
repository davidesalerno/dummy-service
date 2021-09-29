import React from 'react';
import Service from "./Service";
import Container from "react-bootstrap/Container";
import {groupBy} from "./Utility";

export default function ListServices(props) {
    if(props.services){
        const {services} = props;
        const groupedServices = groupBy(services, service => service.name);
        console.log(groupedServices);
        const listServices = [];
        groupedServices.forEach((value, key) => {
            console.log(value, key);
            listServices.push(<Service key={key} name={key} calls={value}/>);
        })
        console.log(listServices);
        return <Container>{listServices}</Container>;
    }
    return <Container>No upstream service to show :-(</Container>;
}