import React from 'react';
import {Col, Layout} from 'antd';
import MainMenu from './MainMenu'
import 'antd/dist/antd.css';
import './App.css';
import SocialGraph from "./SocialGraph";
import axios from 'axios';
import UserData from "./UserData";
import LocationHeatmap from './LocationHeatmap';

/* global google */


export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            current: 'relations',
            points: []
        }
    }

    componentWillMount() {
        axios.get('http://gr-esports.de:8080/ts3/users').then(res => {
             let points = [];
             for (let i = 0; i < res.data.length; i++) {
                 points.push({
                     location: new google.maps.LatLng(
                         res.data[i].location.latitude, res.data[i].location.longitude),
                     weight: 5
                 })
                 /*points.push({
                     lat: res.data[i].location.latitude,
                     lng: res.data[i].location.longitude
                 });*/
             }
             this.setState({
                 points: points
             }, () => console.log(this.state.points));
        });
    }

    handleMenuClick(e) {
        console.log(e);
        this.setState({
            current: e.key
        });
    }


    render() {
        let content = '';
        if (this.state.current == 'relations') {
            content = <div className='graph'><SocialGraph /></div>;
        } else if (this.state.current == 'data') {
            content = <UserData/>;
        } else if (this.state.current == 'heatmap') {
            console.log('points', this.state.points);
            content = <div className='graph'><LocationHeatmap points={this.state.points}/></div>
        }

        return(
            <Layout>
                <Layout.Sider breakpoint='md' width={210} style={{background: '#ffffff', padding: 0}}>
                    <MainMenu handleChange={this.handleMenuClick.bind(this)} />
                </Layout.Sider>
                <Layout.Content className='content' >
                    {content}
                </Layout.Content>
            </Layout>
        );
    }
}
