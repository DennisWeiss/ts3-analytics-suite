import React from 'react';
import {Menu} from 'antd';
import 'antd/dist/antd.css';
import './MainMenu.css';


export default class MainMenu extends React.Component {
    render() {
        return(
            <Menu className='main-menu' mode='inline'>
                <Menu.SubMenu key='relations' title='Relations'>
                    <Menu.Item key='graph'>Graph</Menu.Item>
                    <Menu.Item key='data'>Data</Menu.Item>
                </Menu.SubMenu>
                <Menu.SubMenu key='server-usage' title='Server Usage'>
                    <Menu.Item key='usage-statistics'>Statistics</Menu.Item>
                </Menu.SubMenu>
            </Menu>
        );
    }
}