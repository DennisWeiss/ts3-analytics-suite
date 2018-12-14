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



const addTimeZoneOffset = dataPoint => ([dataPoint[0] - new Date().getTimezoneOffset() * 60000, dataPoint[1]])

class LastMonthUsersCount extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            usersSeriesData: []
        }
    }

    componentDidMount() {
        // this.refs.chart.getChart().showLoading()

        fetch('https://gr-esports.de:8092/api/ts3/server-users/series-data' + convertToQueryString({
            from: moment().subtract(32, 'days').toISOString(),
            to: moment().toISOString()
        }))
            .then(res => res.json())
            .then(data => {
                this.setState({usersSeriesData: data.map(addTimeZoneOffset)})
            })
    }

    render() {

        return (
            <HighchartsStockChart>
                <Chart onClick={this.handleClick} zoomType="x"/>

                <Title>Users online last month</Title>

                <RangeSelector>
                    <RangeSelector.Button count={1} type="day">1d</RangeSelector.Button>
                    <RangeSelector.Button count={7} type="day">7d</RangeSelector.Button>
                    <RangeSelector.Button count={1} type="month">1m</RangeSelector.Button>
                </RangeSelector>

                <Tooltip/>

                <XAxis>

                </XAxis>

                <YAxis>
                    <YAxis.Title>Users online</YAxis.Title>
                    <AreaSplineSeries id="user-online" name="Users online" data={this.state.usersSeriesData}/>
                </YAxis>

                <Navigator>
                    <Navigator.Series seriesId="users-online" />
                </Navigator>
            </HighchartsStockChart>
        )

    }
}

export default withHighcharts(LastMonthUsersCount, Highcharts)