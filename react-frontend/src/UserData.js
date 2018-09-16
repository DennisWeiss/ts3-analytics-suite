import React from 'react';
import './UserData.css';
import {Select, Row, Col} from 'antd';
import axios from 'axios';
import UserDataOverview from './UserDataOverview';
import RelationsOverview from "./RelationsOverview";
import Location from "./Location";
import UserSocialGraph from "./UserSocialGraph";
import createHistory from 'history/createBrowserHistory'
import queryString from 'query-string'

const history = createHistory()

const cardSizes = {
    xxl: 12,
    xl: 12,
    lg: 12,
    md: 24,
    sm: 24,
    xs: 24
}

export default class UserData extends React.Component {
    constructor(props) {
        super(props)

        const parsedSearch = queryString.parse(history.location.search)

        this.state = {
            users: [],
            user: parsedSearch.user,
            relatedUsers: [],
            loading: true,
        }
    }

    setSearchPath = () => history.push({
        pathname: '/user-data',
        search: `?user=${encodeURIComponent(this.state.user != null ? this.state.user.uniqueID : '')}`
    })

    componentDidMount() {
        axios.get('http://gr-esports.de:8081/ts3/users').then(res => {
            let user = this.state.user != null ? res.data.find(user => user.uniqueID === this.state.user) : res.data[Math.floor(res.data.length * Math.random())];
            this.setState({
                users: res.data,
                user: user
            }, this.setSearchPath);

            this.setRelations(user);
        });
    }

    setRelations(user) {
        this.setState({loading: true})
        axios.get('http://gr-esports.de:8081/ts3/relation', {params: {user: user.uniqueID}}).then(res => {
            axios.get('http://gr-esports.de:8081/ts3/users').then(res2 => {
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
                this.setState({
                    relatedUsers: relatedUsers,
                    loading: false
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
        }, this.setSearchPath);
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
        }, this.setSearchPath);
    }

    render() {

        let location = {}
        if (this.state.user == null || this.state.user.location == null) {
            location = {lat: 0, lng: 0}
        } else {
            location = {lat: this.state.user.location.latitude, lng: this.state.user.location.longitude}
        }

        return (
            <div className='user-data-content'>
                <div className='selector'>
                    <Select
                        showSearch
                        style={{width: 200}}
                        placeholder='Search for a user'
                        value={this.state.user != null ? this.state.user.uniqueID : null}
                        optionsFilterProp='children'
                        onChange={this.handleSelect.bind(this)}
                        onFocus={this.handleFocus}
                        onBlur={this.handleBlur}
                        filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}>
                        {this.state.users.map(item => {
                            return (
                                <Select.Option value={item.uniqueID} key={item.uniqueID}>{item.nickname}</Select.Option>
                            );
                        })}
                    </Select>
                </div>
                <Row gutter={16}>
                    <Col xs={cardSizes.xs}
                         sm={cardSizes.sm}
                         md={cardSizes.md}
                         lg={cardSizes.lg}
                         xl={cardSizes.xl}
                         xxl={cardSizes.xxl}>
                        <div className='card-element'>
                            <UserDataOverview user={this.state.user != null ? this.state.user : {}} loading={this.state.loading}/>
                        </div>
                    </Col>
                    <Col xs={cardSizes.xs}
                         sm={cardSizes.sm}
                         md={cardSizes.md}
                         lg={cardSizes.lg}
                         xl={cardSizes.xl}
                         xxl={cardSizes.xxl}>
                        <div className='card-element'>
                            <Location lat={location.lat}
                                      lng={location.lng}
                                      username={this.state.user != null ? this.state.user.username : ''}
                                      loading={this.state.loading}/>
                        </div>
                    </Col>
                    <Col xs={cardSizes.xs}
                         sm={cardSizes.sm}
                         md={cardSizes.md}
                         lg={cardSizes.lg}
                         xl={cardSizes.xl}
                         xxl={cardSizes.xxl}>
                        <div className='card-element'>
                            <RelationsOverview relatedUsers={this.state.relatedUsers}
                                               handleSelect={this.handleSelect.bind(this)}
                                               loading={this.state.loading}/>
                        </div>
                    </Col>
                    <Col xs={cardSizes.xs}
                         sm={cardSizes.sm}
                         md={cardSizes.md}
                         lg={cardSizes.lg}
                         xl={cardSizes.xl}
                         xxl={cardSizes.xxl}>
                        <div className='card-element'>
                            <UserSocialGraph user={this.state.user} onRef={ref => this.socialGraph = ref}
                                             onSelect={this.handleGraphSelect.bind(this)}
                                             loading={this.state.loading}/>
                        </div>
                    </Col>
                </Row>
            </div>
        );
    }
}