import React from 'react'
import {Card} from 'antd'
import LastMonthUsersCount from './LastMonthUsersCount'
import AllTimeUsersCount from './AllTimeUsersCount'


const Statistics = props => {

    return (
        <div>
            <Card>
                <LastMonthUsersCount />
                <AllTimeUsersCount />
            </Card>
        </div>
    )
}

export default Statistics