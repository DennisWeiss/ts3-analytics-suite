import React from 'react';
import {Col, Layout, Menu, Row} from 'antd';
import MainMenu from './MainMenu'
import 'antd/dist/antd.css';
import './App.css';
import SocialGraph from "./SocialGraph";
import axios from 'axios';


export default class App extends React.Component {




    render() {
        return(
            <div>
                <Row className='content'>
                    <Col span={4}>
                        <MainMenu />
                    </Col>
                    <Col span={20}>
                        <div className='graph'>
                            <SocialGraph/>
                        </div>
                    </Col>
                </Row>
            </div>
        );
    }
}
