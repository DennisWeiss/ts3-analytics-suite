import React from 'react'
import {Col, Layout} from 'antd'
import MainMenu from './MainMenu'
import 'antd/dist/antd.css'
import './App.css'
import SocialGraph from "./SocialGraph"
import axios from 'axios';
import UserData from './UserData'
import LocationHeatmap from './LocationHeatmap'
import {Route} from "react-router";


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
                     lng: res.data[i].location.longitude,
                     lat: res.data[i].location.latitude,
                     weight: 800
                 })
             }
             this.setState({
                 points: points
             }, () => console.log('points', this.state.points));
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
                    <Route path='/' exact component={() => <div className='graph'><SocialGraph /></div>}/>
                    <Route path='/user-data' exact component={UserData}/>
                    <Route path='/heatmap' exact component={() => <div className='graph'><LocationHeatmap points={this.state.points}/></div>}/>
                </Layout.Content>
            </Layout>
        );
    }
}
