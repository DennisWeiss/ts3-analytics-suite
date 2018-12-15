import React from 'react'
import ReactHighcharts from 'react-highcharts'
import moment from "moment";

const getOptions = data => ({
    title: {
        text: 'Daily Average user count'
    },
    xAxis: {
        labels: {
            formatter: function() {
                const timestamp = moment.utc(60 * 1000 * this.value);
                return `${timestamp.format('HH')}:${timestamp.format('mm')}`
            }
        }
    },
    yAxis: {
        title: {
            text: 'User count'
        }
    },
    tooltip: {
        formatter: function(tooltip) {
            const timestamp = moment.utc(60 * 1000 * this.x);
            return `<span style="font-size: 10px">${timestamp.format('HH')}:${timestamp.format('mm')}</span>
                    <br/>${this.series.name}: <b>${this.y}</b>`
        }
    },
    series: [{
        name: 'Daily average users',
        data: data.map(dataPoint => dataPoint.users)
    }]
})

class DailyAverageUsersCount extends React.Component {

    state = {
        data: []
    }

    componentDidMount() {
        fetch('https://gr-esports.de:8092/api/ts3/server-users/daily-average-data')
            .then(res => res.json())
            .then(data => this.setState({data}))
    }

    render() {
        return (
            <ReactHighcharts config={getOptions(this.state.data)}/>
        )
    }
}

export default DailyAverageUsersCount