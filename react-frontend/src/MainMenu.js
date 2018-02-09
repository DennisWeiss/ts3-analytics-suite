import React from 'react';
import {Menu} from 'antd';
import 'antd/dist/antd.css';
import './MainMenu.css';


export default class MainMenu extends React.Component {
    constructor(props) {
        super(props);
    }


    render() {
        return(
            <Menu
                className='main-menu'
                mode='inline'
                onClick={this.props.handleChange}
                defaultSelectedKeys={['relations']}
                defaultOpenKeys={['users', 'server']}>
                <Menu.SubMenu key='users' title='Users'>
                    <Menu.Item key='relations'>Relations</Menu.Item>
                    <Menu.Item key='data'>Data</Menu.Item>
                    <Menu.Item key='heatmap'>Location Heatmap</Menu.Item>
                </Menu.SubMenu>
                <Menu.SubMenu key='server' title='Server Usage'>
                    <Menu.Item key='usage-statistics'>Statistics</Menu.Item>
                </Menu.SubMenu>
            </Menu>
        );
    }
}