import React from 'react';
import {Icon, Menu} from 'antd';
import 'antd/dist/antd.css';
import './MainMenu.css';
import 'font-awesome/css/font-awesome.min.css';



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
                <Menu.SubMenu key='users' title={<span><Icon type='user' /><span>Users</span></span>}>
                    <Menu.Item key='relations'><i className='fa fa-share-alt' style={{'margin-right': '10px'}}/>Relations</Menu.Item>
                    <Menu.Item key='data'><Icon type='table'/>Data</Menu.Item>
                    <Menu.Item key='heatmap'><i className='fa fa-map' style={{'margin-right': '10px'}}/>Location Heatmap</Menu.Item>
                </Menu.SubMenu>
                <Menu.SubMenu key='server' title={<span><Icon type='cloud-upload-o'/><span>Server Usage</span></span>}>
                    <Menu.Item key='usage-statistics'><Icon type='line-chart'/>Statistics</Menu.Item>
                </Menu.SubMenu>
            </Menu>
        );
    }
}