import React from 'react'
import {Card} from 'antd'
import LastMonthUsersCount from './LastMonthUsersCount'
import AllTimeUsersCount from './AllTimeUsersCount'
import './Statistics.css'


const Statistics = props => {

    return (
        <div className='stats'>
            <Card>
                <LastMonthUsersCount />
                <AllTimeUsersCount />
            </Card>
        </div>
    )
}

export default Statistics