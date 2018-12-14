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
        loading: [...Array(3)].map(_ => false),
        usersSeriesData: []
    }

    setLoading = i => value => this.setState((prevState, props) => {
        prevState.loading[i] = value
        return prevState
    })

    componentDidMount() {
        this.setLoading(1)(true)
        this.setLoading(2)(true)
        fetch('https://gr-esports.de:8092/api/ts3/server-users/daily-data' + convertToQueryString({
            from: config.startDate,
            to: moment().format('YYYY-MM-DD')
        }))
            .then(res => res.json())
            .then(data => {
                this.setState({usersSeriesData: data})
                this.setLoading(1)(false)
                this.setLoading(2)(false)
            })
    }

    render() {
        return (
            <div className='stats'>
                <Card className='stats-card' loading={this.state.loading[0]}>
                    <LastMonthUsersCount setLoading={this.setLoading(0)}/>
                </Card>
                <Card className='stats-card' loading={this.state.loading[1]}>
                    <AllTimeUsersCount data={this.state.usersSeriesData}/>
                </Card>
                <Card className='stats-card' loading={this.state.loading[2]}>
                    <DailyAverageUsersCount data={this.state.usersSeriesData}/>
                </Card>
            </div>
        )
    }
}

export default Statistics