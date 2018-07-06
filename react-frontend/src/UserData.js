import React from 'react';
import './UserData.css';
import {Select} from 'antd';
import axios from 'axios';
import UserDataOverview from './UserDataOverview';
import RelationsOverview from "./RelationsOverview";
import Location from "./Location";
import UserSocialGraph from "./UserSocialGraph";


export default class UserData extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            user: {},
            relatedUsers: []
        }
    }

    componentDidMount() {
        axios.get('http://gr-esports.de:8080/ts3/users').then(res => {
            let user = res.data[Math.floor(res.data.length * Math.random())];
            this.setState({
                users: res.data,
                user: user
            });
            this.setRelations(user);
        });
    }

    setRelations(user) {
        axios.get('http://gr-esports.de:8080/ts3/relation', {params: {user: user.uniqueID}}).then(res => {
            axios.get('http://gr-esports.de:8080/ts3/users').then(res2 => {
                let relatedUsers = this.state.relatedUsers.splice();
                let username = '';
                for (let i = 0; i < res.data.length; i++) {
                    for (let j = 0; j < res2.data.length; j++) {
                        if (res.data[i].otherUser === res2.data[j].uniqueID) {
                            username = res2.data[j].nickname;
                        }
                    }
                    relatedUsers.push({
                        key: res.data[i].otherUser,
                        username: username,
                        id: res.data[i].otherUser,
                        relation: res.data[i].totalRelation
                    });
                }
                console.log(relatedUsers);
                this.setState({
                    relatedUsers: relatedUsers
                });
            })
        });
    }

    handleSelect(value) {
        this.socialGraph.select(value);
        let user = {};
        for (let i = 0; i < this.state.users.length; i++) {
            if (this.state.users[i].uniqueID === value) {
                user = this.state.users[i];
            }
        }

        this.setRelations(user);

        this.setState({
            user: user,
        });
    }

    handleGraphSelect(value) {
        if (this.socialGraph != null) {
            this.socialGraph.select(value);
        }
        let user = {};
        for (let i = 0; i < this.state.users.length; i++) {
            if (this.state.users[i].uniqueID === value) {
                user = this.state.users[i];
            }
        }

        this.setRelations(user);

        this.setState({
            user: user,
        });
    }

    render() {
        console.log(this.state.user);

        let location = {};
        if (this.state.user.location == null) {
            location = {lat: 0, lng: 0}
        } else {
            location = {lat: this.state.user.location.latitude, lng: this.state.user.location.longitude}
        }

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
                <div>
                    <UserDataOverview user={this.state.user}/>
                    <Location lat={location.lat} lng={location.lng} username={this.state.user.uername}/>
                    <RelationsOverview relatedUsers={this.state.relatedUsers} handleSelect={this.handleSelect.bind(this)}/>
                    <UserSocialGraph user={this.state.user} onRef={ref => this.socialGraph = ref} onSelect={this.handleGraphSelect.bind(this)}/>
                </div>
            </div>
        );
    }
}