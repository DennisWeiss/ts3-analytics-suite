import React from 'react'
import ReactHighcharts from 'highcharts-react-official'
import countries from './countries'
import Highcharts from 'highcharts/highmaps'

const map = require('./highmaps/world')
//
// console.log(map)
//
// Highcharts.maps = {}
// Highcharts.maps['custom/world'] = map
//
// console.log(Highcharts.maps)


const aggregatedUserData = users => {
    const originToUsers = {}
    users.forEach(user => {
        if (user.country in countries) {
            if (user.country in originToUsers) {
                originToUsers[user.country]++
            } else {
                originToUsers[user.country] = 1
            }
        }
    })
    return Object.keys(originToUsers)
        .map(country => ({
            'iso-a2': country,
            value: originToUsers[country]
        }))
}

const getOptions = data => ({
    title: {
        text: null
    },
    mapNavigation: {
        enabled: false
    },
    colorAxis: {
        min: 0,
        stops: [
            [0, '#EFEFFF'],
            [0.5, Highcharts.getOptions().colors[0]],
            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).brighten(-0.5).get()]
        ]
    },
    legend: {
        layout: 'vertical',
        align: 'left',
        verticalAlign: 'bottom'
    },
    plotOptions: {
        map: {
            allAreas: true,
            joinBy: 'iso-a2',
            mapData: map
        }
    },
    series: [{
        data: data,
        name: 'Users'
    }]
})

class UserOriginMapChart extends React.Component {

    state = {
        mapData: []
    }

    componentDidMount() {
        console.log('component did mount')
        fetch('https://gr-esports.de:8092/api/ts3/users')
            .then(res => res.json())
            .then(users => {
                console.log(aggregatedUserData(users))
                this.setState({mapData: aggregatedUserData(users)})
            })
    }

    render() {
        console.log(this.state.mapData)
        return <ReactHighcharts
            highcharts={Highcharts}
            constructorType={'mapChart'}
            options={getOptions(this.state.mapData)}/>
    }
}

export default UserOriginMapChart