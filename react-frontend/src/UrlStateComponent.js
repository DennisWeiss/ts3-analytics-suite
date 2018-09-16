import React from 'react'
import queryString from "query-string"
import createHistory from 'history/createBrowserHistory'


const history = createHistory()

export default class UrlStateComponent extends React.Component {

    constructor() {
        super()
        const parsedSearch = queryString.parse(history.location.search)
        this.urlState = {...parsedSearch}
    }

    componentDidMount() {
        const state = {}
        Object.keys(this.state).forEach(key => state[key] = this.state[key])
        Object.keys(this.urlState).forEach(key => state[key] = this.urlState[key])
        this.setState(state)
    }

    setUrlState(urlState) {

        const getSearchString = state => {
            if (Object.keys(state).length === 0) {
                return ''
            }
            return '?' + Object.keys(state)
                .map(key => `${key}=${encodeURIComponent(state[key] != null ? state[key] : '')}`)
                .join('&')
        }

        const convertToHistory = (state, pathname) => ({
            pathname: pathname,
            search: getSearchString(state)
        })
        this.setState(urlState, () => history.push(convertToHistory(urlState, this.props.pathname)))
    }

}