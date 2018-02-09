import React from 'react';
import {Col, Layout, Menu, Row} from 'antd';
import MainMenu from './MainMenu'
import 'antd/dist/antd.css';
import './App.css';
import SocialGraph from "./SocialGraph";
import axios from 'axios';
import UserData from "./UserData";


export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            current: 'relations'
        }
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
        }

        return(
            <div>
                <Row className='content'>
                    <Col span={4}>
                        <MainMenu handleChange={this.handleMenuClick.bind(this)}/>
                    </Col>
                    <Col span={20}>
                        {content}
                    </Col>
                </Row>
            </div>
        );
    }
}
