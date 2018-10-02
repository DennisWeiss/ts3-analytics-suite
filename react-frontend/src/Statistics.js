import React from 'react'
import {Card} from 'antd'
import ReactHighcharts from 'react-highcharts'
// import Highcharts from 'highcharts'
import {convertToQueryString} from './helper/helper-functions'
import moment from 'moment'


class Statistics extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            usersSeriesData: []
        }
    }

    componentDidMount() {
        fetch('http://gr-esports.de:8092/api/ts3/server-users/series-data' + convertToQueryString({
            from: '2017-12-01T00:00:00Z',
            to: moment().format('YYYY-MM-DD') + 'T' + moment().format('HH:mm:ss') + 'Z'
        }))
            .then(res => res.json())
            .then(data => this.setState({usersSeriesData: data}))
    }

    render() {

        const options = {
            chart: {
                zoomType: 'x'
            },
            title: {
                text: 'Users connected count'
            },
            xAxis: {
                type: 'datetime'
            },
            yAxis: {
                title: {
                    text: 'Users online'
                }
            },
            legend: {
                enabled: false
            },
            plotOptions: {
                area: {
                    fillColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        // stops: [
                        //     [0, Highcharts.getOptions().colors[0]],
                        //     [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                        // ]
                    },
                },
                marker: {
                    radius: 2
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            },
            series: [{
                type: 'area',
                name: 'Users online',
                data: this.state.usersSeriesData
            }]
        }

        return (
            <div>
                <Card>
                    <ReactHighcharts config={options}/>
                </Card>
            </div>
        )

    }
}

export default Statistics