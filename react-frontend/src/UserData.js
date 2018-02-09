import React from 'react';
import './UserData.css';
import {Select, Row, Col} from 'antd';
import axios from 'axios';
import UserDataOverview from './UserDataOverview';
import RelationsOverview from "./RelationsOverview";


export default class UserData extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            user: ''
        }
    }

    componentDidMount() {
        axios.get('http://gr-esports.de:8080/ts3/users').then(res => {
            this.setState({
                users: res.data,
                user: res.data[Math.floor(res.data.length * Math.random())]
            });
        });
    }

    handleSelect(value) {
        let user = {};
        for (let i = 0; i < this.state.users.length; i++) {
            if (this.state.users[i].uniqueID == value) {
                user = this.state.users[i];
            }
        }
        this.setState({
            user: user
        });
    }

    render() {
        return(
            <div>
                <div className='selector'>
                    <Select
                        showSearch
                        style={{width: 200}}
                        placeholder='Search for a user'
                        value={this.state.user.uniqueID}
                        optionsFilterProp='children'
                        onChange={this.handleSelect.bind(this)}
                        onFocus={this.handleFocus}
                        onBlur={this.handleBlur}
                        filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}>
                        {this.state.users.map((item, index) => {
                            return(
                                <Select.Option value={item.uniqueID}>{item.nickname}</Select.Option>
                            );
                        })}
                    </Select>
                </div>
                <Row>
                    <Col span={12}>
                        <UserDataOverview user={this.state.user}/>
                        <RelationsOverview/>
                    </Col>
                    <Col span={12}>

                    </Col>
                </Row>
            </div>
        );
    }
}