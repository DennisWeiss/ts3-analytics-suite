import React from 'react';
import {Icon, Menu} from 'antd';
import 'antd/dist/antd.css';
import './MainMenu.css';
import 'font-awesome/css/font-awesome.min.css';
import {Link} from "react-router-dom";



export default class MainMenu extends React.Component {

    render() {
        return(
            <Menu
                className='main-menu'
                mode='inline'
                selectedKeys={[this.props.selected]}
                onClick={this.props.handleChange}
                defaultOpenKeys={['users', 'server']}>
                <Menu.SubMenu key='users' title={<span><Icon type='user' /><span>Users</span></span>}>
                    <Menu.Item key='relations'>
                        <Link to='/'>
                            <i className='fa fa-share-alt' style={{'margin-right': '10px'}}/>Relations
                        </Link>
                    </Menu.Item>
                    <Menu.Item key='data'>
                        <Link to='/user-data'>
                            <Icon type='table'/>Data</Link>
                    </Menu.Item>
                    <Menu.Item key='heatmap'>
                        <Link to='/heatmap'>
                            <i className='fa fa-map' style={{'margin-right': '10px'}}/>Location Heatmap
                        </Link>
                    </Menu.Item>
                </Menu.SubMenu>
                <Menu.SubMenu key='server' title={<span><Icon type='cloud-upload-o'/><span>Server Usage</span></span>}>
                    <Menu.Item key='usage-statistics'>
                        <Link to='/stats'>
                            <Icon type='line-chart'/>Statistics
                        </Link>
                    </Menu.Item>
                </Menu.SubMenu>
            </Menu>
        );
    }
}