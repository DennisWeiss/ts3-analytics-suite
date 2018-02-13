import React from 'react';
import './RelationsOverview.css';
import './UserData.css';
import axios from 'axios';
import {Table} from 'antd';


export default class RelationsOverview extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            windowWidth: window.innerWidth,
            windowHeight: window.innerHeight,
            columns: [{
                title: 'User',
                dataIndex: 'username',
                key: 'username',
            }, {
                title: 'ID',
                dataIndex: 'id',
                key: 'id',
                render: text => <a href='#' onClick={() => this.props.handleSelect(text)} >{text}</a>
            }, {
                title: 'Relation',
                dataIndex: 'relation',
                key: 'relation',
                render: relation => ((100 * relation).toFixed(2))
            }]
        }
    }

    updateDimensions() {
        this.setState({
            windowWidth: window.innerWidth,
            windowHeight: window.innerHeight
        });
    }

    componentDidMount() {
        window.addEventListener('resize', () => this.updateDimensions());
    }


    render() {
        let size = 'default';
        console.log(this.state.windowWidth);

        if (this.state.windowWidth <= 900) {
            size = 'middle';
        }

        return(
            <div className='overview' >
                <Table size={size} columns={this.state.columns} dataSource={this.props.relatedUsers} pagination={false} />
            </div>
        );
    }
}