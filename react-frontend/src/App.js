import React from 'react';
import {Col, Layout} from 'antd';
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
            <Layout>
                <Layout.Sider breakpoint='md' width={210} style={{background: '#ffffff', padding: 0}}>
                    <MainMenu handleChange={this.handleMenuClick.bind(this)}/>
                </Layout.Sider>
                <Layout.Content style={{background: '#f9f9f9'}}>
                    {content}
                </Layout.Content>
            </Layout>
        );
    }
}
