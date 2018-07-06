import React from 'react';
import './UserDataOverview.css';
import './UserData.css';
import {Row, Col} from 'antd';
import isoCountries from './countries';
import Flag from 'react-world-flags';


export default class UserDataOverview extends React.Component {
    render() {
        let nickname = this.props.user.nickname == null || this.props.user.nickname === '' ? ' ' : this.props.user.nickname;

        let xs = 8;
        let sm = 8;
        let md = 8;
        let lg = 8;
        let xl = 8;

        return(
            <div className='overview'>
                <div className='heading'>
                    {nickname}
                </div>
                <div className='dataoverview'>
                    <Row>
                        <Col xs={xs} sm={sm} md={md} lg={lg} xl={xl}>
                            Global ID:
                        </Col>
                        <Col xs={24-xs} sm={24-sm} md={24-md} lg={24-lg} xl={24-xl}>
                            {this.props.user.uniqueID}<br />
                        </Col>
                        <Col xs={xs} sm={sm} md={md} lg={lg} xl={xl}>
                            IP:
                        </Col>
                        <Col xs={24-xs} sm={24-sm} md={24-md} lg={24-lg} xl={24-xl}>
                            {this.props.user.ip}<br />
                        </Col>
                        <Col xs={xs} sm={sm} md={md} lg={lg} xl={xl}>
                            City:
                        </Col>
                        <Col xs={24-xs} sm={24-sm} md={24-md} lg={24-lg} xl={24-xl}>
                            {this.props.user.city}<br />
                        </Col>
                        <Col xs={xs} sm={sm} md={md} lg={lg} xl={xl}>
                            Region:
                        </Col>
                        <Col xs={24-xs} sm={24-sm} md={24-md} lg={24-lg} xl={24-xl}>
                            {this.props.user.region}<br />
                        </Col>
                        <Col xs={xs} sm={sm} md={md} lg={lg} xl={xl}>
                            Country:
                        </Col>
                        <Col xs={24-xs} sm={24-sm} md={24-md} lg={24-lg} xl={24-xl}>
                            <Flag code={this.props.user.country} height={12}/> {isoCountries[this.props.user.country]}<br />
                        </Col>
                        <Col xs={xs} sm={sm} md={md} lg={lg} xl={xl}>
                            Postal code:
                        </Col>
                        <Col xs={24-xs} sm={24-sm} md={24-md} lg={24-lg} xl={24-xl}>
                            {this.props.user.postalCode}<br />
                        </Col>
                        <Col xs={xs} sm={sm} md={md} lg={lg} xl={xl}>
                            Hostname:
                        </Col>
                        <Col xs={24-xs} sm={24-sm} md={24-md} lg={24-lg} xl={24-xl}>
                            {this.props.user.hostName}<br />
                        </Col>
                        <Col xs={xs} sm={sm} md={md} lg={lg} xl={xl}>
                            Ass. Org/Provider:
                        </Col>
                        <Col xs={24-xs} sm={24-sm} md={24-md} lg={24-lg} xl={24-xl}>
                            {this.props.user.associatedOrg}<br />
                        </Col>
                    </Row>
                </div>
            </div>
        );
    }
}