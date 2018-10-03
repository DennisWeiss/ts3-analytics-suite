import React from 'react'
import {Card} from 'antd'
import Highcharts from 'highcharts/highstock'
import {convertToQueryString} from './helper/helper-functions'
import moment from 'moment'
import {
    HighchartsStockChart,
    Chart,
    Title,
    Legend,
    RangeSelector,
    Tooltip,
    XAxis,
    YAxis,
    Navigator,
    SplineSeries,
    AreaSplineSeries
} from 'react-jsx-highstock'
import {withHighcharts} from 'react-jsx-highcharts'



const addTimeZoneOffset = dataPoint => ([dataPoint[0] - new Date().getTimezoneOffset() * 60000, dataPoint[1]])

class Statistics extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            usersSeriesData: []
        }
    }

    componentDidMount() {
        fetch('http://gr-esports.de:8092/api/ts3/server-users/series-data' + convertToQueryString({
            from: moment().subtract(32, 'days').toISOString(),
            to: moment().toISOString()
        }))
            .then(res => res.json())
            .then(data => this.setState({usersSeriesData: data
                    .sort((a, b) => a[0] - b[0])
                    .map(addTimeZoneOffset)}))
    }

    render() {

        if (this.state.usersSeriesData == null || this.state.usersSeriesData.length === 0) {
            return (
                <div>
                    <Card></Card>
                </div>
            )
        }

        return (
            <div>
                <Card>
                    <HighchartsStockChart>
                        <Chart onClick={this.handleClick} zoomType="x" />

                        <Title>Users online last month</Title>

                        <RangeSelector>
                            <RangeSelector.Button count={1} type="day">1d</RangeSelector.Button>
                            <RangeSelector.Button count={7} type="day">7d</RangeSelector.Button>
                            <RangeSelector.Button count={1} type="month">1m</RangeSelector.Button>
                        </RangeSelector>

                        <Tooltip />

                        <XAxis>

                        </XAxis>

                        <YAxis>
                            <YAxis.Title>Users online</YAxis.Title>
                            <AreaSplineSeries id="user-online" name="Users online" data={this.state.usersSeriesData} />
                        </YAxis>

                        <Navigator>
                            <Navigator.Series seriesId="users-online" />
                        </Navigator>
                    </HighchartsStockChart>
                </Card>
            </div>
        )

    }
}

export default withHighcharts(Statistics, Highcharts)