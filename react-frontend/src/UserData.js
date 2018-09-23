import React from 'react'
import './UserData.css'
import {Select, Row, Col} from 'antd'
import UserDataOverview from './UserDataOverview'
import RelationsOverview from './RelationsOverview'
import Location from './Location'
import UserSocialGraph from './UserSocialGraph'
import {initializeReactUrlState} from 'react-url-state'
import 'whatwg-fetch'
import {convertToQueryString} from './helper/helper-functions'


const cardSizes = {
    xxl: 12,
    xl: 12,
    lg: 12,
    md: 24,
    sm: 24,
    xs: 24
};


const reactUrlStateOptions = {
    fromIdResolvers: {
        user: id => new Promise((resolve, reject) => {
            fetch('http://gr-esports.de:8081/ts3/users')
                .then(res => res.json())
                .then(data => {
                    const user = data.find(user => user.uniqueID === id);
                    console.log('id', id);
                    console.log('user', user);
                    resolve(user != null ? user : {});
                })
                .catch(reject);
        })
    },
    toIdMappers: {
        user: user => user.uniqueID
    },
    pathname: '/user-data'
};

export default class UserData extends ReactUrlStateComponent {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            user: {},
            relatedUsers: [],
            loading: true,
        };
    }


    componentDidMount() {
        this.reactUrlState = initializeReactUrlState(this)(reactUrlStateOptions);

        fetch('http://gr-esports.de:8081/ts3/users')
            .then(res => res.json())
            .then(data => {
                this.setState({
                    users: data,
                }, () => {
                    if (this.state.user.uniqueID == null) {
                        this.reactUrlState.setUrlState({
                            user: this.state.users.length > 0 ? this.state.users[Math.floor(this.state.users.length * Math.random())] : {}
                        });
                    }
                });

                this.setRelations(this.state.user);
            });
    }

    setRelations(user) {
        if (user != null && user.uniqueID != null) {
            this.setState({loading: true});
            fetch('http://gr-esports.de:8081/ts3/relation' + convertToQueryString({user: user.uniqueID}))
                .then(res => res.json())
                .then(data => {
                fetch('http://gr-esports.de:8081/ts3/users')
                    .then(res => res.json())
                    .then(data2 => {
                        let relatedUsers = this.state.relatedUsers.splice();
                        let username = '';
                        for (let i = 0; i < data.length; i++) {
                            for (let j = 0; j < data2.length; j++) {
                                if (data[i].otherUser === data2[j].uniqueID) {
                                    username = data2[j].nickname;
                                }
                            }
                            relatedUsers.push({
                                key: data[i].otherUser,
                                username: username,
                                id: data[i].otherUser,
                                relation: data[i].totalRelation
                            });
                        }
                        this.setState({
                            relatedUsers: relatedUsers,
                            loading: false
                        });
                });
            });
        }
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

        this.reactUrlState.setUrlState({
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

        this.reactUrlState.setUrlState({
            user: user,
        });
    }

    render() {

        let location = {};
        if (this.state.user == null || this.state.user.location == null) {
            location = {lat: 0, lng: 0};
        } else {
            location = {lat: this.state.user.location.latitude, lng: this.state.user.location.longitude};
        }

        return (
            <div className='user-data-content'>
                <div className='selector'>
                    {this.state.n}
                    <Select
                        showSearch
                        style={{width: 200}}
                        placeholder='Search for a user'
                        value={this.state.user != null && this.state.users != null && this.state.users.length > 0 ? this.state.user.uniqueID : null}
                        optionsFilterProp='children'
                        onChange={this.handleSelect.bind(this)}
                        onFocus={this.handleFocus}
                        onBlur={this.handleBlur}
                        filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}>
                        {this.state.users.map(item =>
                            <Select.Option value={item.uniqueID} key={item.uniqueID}>{item.nickname}</Select.Option>
                        )}
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
                            <UserDataOverview user={this.state.user != null ? this.state.user : {}}
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