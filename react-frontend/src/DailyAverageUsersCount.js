import React from 'react'
import ReactHighcharts from 'react-highcharts'
import moment from "moment";

const getOptions = usersSeriesData => ({
    title: {
        text: 'Daily Average user count'
    },
    chart: {
        zoomType: 'x'
    },
    xAxis: {
        labels: {
            formatter: function() {
                const timestamp = moment(this.value);
                return `${timestamp.format('MMM')} '${timestamp.format('YY')}`
            }
        }
    },
    yAxis: {
        title: {
            text: 'User count'
        }
    },
    series: [{
        name: 'Daily average',
        data: usersSeriesData
    }]
})

const getDailyAverageData = usersSeriesData => {
    const dailyAverage = {}
    for (let i = 0; i < usersSeriesData.length; i++) {
        if (dailyAverage[usersSeriesData[i].])
    }
}

class DailyAverageUsersCount extends React.Component {

    render() {
        return (
            <ReactHighcharts config={getOptions(this.props.data)}/>
        )
    }
}

export default DailyAverageUsersCount