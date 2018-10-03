import React from 'react'
import Highcharts from 'highcharts/highstock'
import {convertToQueryString} from './helper/helper-functions'
import moment from 'moment'
import {
    HighchartsStockChart,
    Chart,
    Title,
    RangeSelector,
    Tooltip,
    XAxis,
    YAxis,
    Navigator,
    AreaSplineSeries
} from 'react-jsx-highstock'
import {withHighcharts} from 'react-jsx-highcharts'
import config from './configuration/config'


class AllTimeUsersCount extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            usersSeriesData: []
        }
    }

    componentDidMount() {
        fetch('http://gr-esports.de:8092/api/ts3/server-users/daily-data' + convertToQueryString({
            from: config.startDate,
            to: moment().format('YYYY-MM-DD')
        }))
            .then(res => res.json())
            .then(data => this.setState({usersSeriesData: data}))
    }


    render() {
        return (
            <HighchartsStockChart>
                <Chart onClick={this.handleClick} zoomType="x"/>

                <Title>Users online all time</Title>

                <RangeSelector>
                    <RangeSelector.Button count={1} type="day">1d</RangeSelector.Button>
                    <RangeSelector.Button count={7} type="day">7d</RangeSelector.Button>
                    <RangeSelector.Button count={1} type="month">1m</RangeSelector.Button>
                    <RangeSelector.Button count={6} type="month">6m</RangeSelector.Button>
                    <RangeSelector.Button count={1} type="year">1y</RangeSelector.Button>
                </RangeSelector>

                <Tooltip/>

                <XAxis>

                </XAxis>

                <YAxis>
                    <YAxis.Title>Users online</YAxis.Title>
                    <AreaSplineSeries id="user-online" name="Users online" data={this.state.usersSeriesData}/>
                </YAxis>

                <Navigator>
                    <Navigator.Series seriesId="users-online"/>
                </Navigator>
            </HighchartsStockChart>
        )
    }
}

export default withHighcharts(AllTimeUsersCount, Highcharts)