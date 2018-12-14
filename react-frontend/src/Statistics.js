import React from 'react'
import {Card} from 'antd'
import LastMonthUsersCount from './LastMonthUsersCount'
import AllTimeUsersCount from './AllTimeUsersCount'
import './Statistics.css'
import DailyAverageUsersCount from "./DailyAverageUsersCount";
import {convertToQueryString} from "./helper/helper-functions";
import config from "./configuration/config";
import moment from "moment";


class Statistics extends React.Component {

    state = {
        loading0: false,
        loading1: false,
        loading2: false,
        usersSeriesData: []
    }

    setLoading0(value) {
        this.setState((prevState, props) => ({
            loading0: value
        }))
    }

    setLoading1(value) {
        this.setState((prevState, props) => ({
            loading1: value
        }))
    }

    setLoading2(value) {
        this.setState((prevState, props) => ({
            loading2: value
        }))
    }

    componentDidMount() {
        this.setLoading1(true)
        fetch('https://gr-esports.de:8092/api/ts3/server-users/daily-data' + convertToQueryString({
            from: config.startDate,
            to: moment().format('YYYY-MM-DD')
        }))
            .then(res => res.json())
            .then(data => {
                this.setState({usersSeriesData: data})
                this.setLoading1(false)
            })
    }

    render() {
        return (
            <div className='stats'>
                <Card className='stats-card' >
                    <LastMonthUsersCount setLoading={this.setLoading0.bind(this)}/>
                </Card>
                <Card className='stats-card' loading={this.state.loading1}>
                    <AllTimeUsersCount data={this.state.usersSeriesData}/>
                </Card>
                <Card className='stats-card' >
                    <DailyAverageUsersCount setLoading={this.setLoading2.bind(this)}/>
                </Card>
            </div>
        )
    }
}

export default Statistics