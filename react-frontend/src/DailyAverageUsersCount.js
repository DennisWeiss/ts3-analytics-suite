import React from 'react'
import ReactHighcharts from 'react-highcharts'
import moment from "moment";
import {convertToQueryString} from "./helper/helper-functions";
import config from "./configuration/config";

const getOptions = data => ({
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
            .then(data => {
                console.log(data)
                this.setState({data})
            })
    }

    render() {
        return (
            <ReactHighcharts config={getOptions(this.state.data)}/>
        )
    }
}

export default DailyAverageUsersCount