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


const plotOptions = {
    tooltip: {
        valueDecimals: 3
    }
}

const mapData = dataPoint => ([1000 * moment(dataPoint.date).unix() - new Date(dataPoint.date).getTimezoneOffset() * 60000, dataPoint.users])

class AllTimeUsersCount extends React.Component {

    render() {
        return (
            <HighchartsStockChart plotOption={plotOptions}>
                <Chart onClick={this.handleClick} zoomType="x"/>

                <Title>Users online all time</Title>

                <RangeSelector>
                    <RangeSelector.Button count={7} type="day">7d</RangeSelector.Button>
                    <RangeSelector.Button count={1} type="month">1m</RangeSelector.Button>
                    <RangeSelector.Button count={6} type="month">6m</RangeSelector.Button>
                    <RangeSelector.Button count={1} type="year">1y</RangeSelector.Button>
                </RangeSelector>

                <Tooltip />

                <XAxis>

                </XAxis>

                <YAxis>
                    <YAxis.Title>Users online</YAxis.Title>
                    <AreaSplineSeries id="user-online" name="Users online" data={this.props.data.map(mapData)}/>
                </YAxis>

                <Navigator>
                    <Navigator.Series seriesId="users-online"/>
                </Navigator>
            </HighchartsStockChart>
        )
    }
}

export default withHighcharts(AllTimeUsersCount, Highcharts)