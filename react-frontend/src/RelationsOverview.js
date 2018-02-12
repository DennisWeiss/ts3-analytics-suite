import React from 'react';
import './RelationsOverview.css';
import './UserData.css';
import axios from 'axios';
import {Table} from 'antd';


export default class RelationsOverview extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
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


    render() {
        return(
            <div className='overview' >
                <Table columns={this.state.columns} dataSource={this.props.relatedUsers} pagination={false} />
            </div>
        );
    }
}