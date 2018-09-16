import React from 'react'
import queryString from "query-string"
import createHistory from 'history/createBrowserHistory'


const history = createHistory()

const isPrimitiveType = a => typeof a === 'string' || typeof a === 'number' || typeof  b === 'boolean'

export default class UrlStateComponent extends React.Component {

    constructor() {
        super()
        const parsedSearch = queryString.parse(history.location.search)
        this.urlState = {...parsedSearch}
        this.valueMappers = {}
    }

    componentDidMount() {
        const state = {}
        Object.keys(this.state).forEach(key => state[key] = this.state[key])
        Object.keys(this.urlState).forEach(key => {
            if (this.valueMappers != null && this.valueMappers[key] != null && typeof this.valueMappers[key] === 'function') {
                state[key] = this.valueMappers[key](this.urlState[key])
            }
        })
        this.setState(state)
    }

    setUrlState(urlState, idMappers, callback) {
        const getSearchString = state => {
            if (Object.keys(state).length === 0) {
                return ''
            }
            return '?' + Object.keys(state)
                .map(key => {
                    if (isPrimitiveType(state[key])) {
                        return `${key}=${encodeURIComponent(state[key] != null ? state[key] : '')}`
                    } else {
                        if (idMappers[key] == null) {
                            throw `No id mapper provided for ${key}! 
                            You always need to provide a mapper if the value is not a primitive data type`
                        } else if (typeof idMappers[key] !== 'function' && !isPrimitiveType(state[key])) {
                            throw `Id mapper of ${key} has to be a function!`
                        } else {
                            const value = idMappers[key](state[key])
                            return `${key}=${encodeURIComponent(value != null ? value : '')}`
                        }
                    }
                })
                .join('&')
        }

        const convertToHistory = (state, pathname) => ({
            pathname: pathname,
            search: getSearchString(state)
        })

        this.setState(urlState, () => {
            history.push(convertToHistory(urlState, this.props.pathname))
            if (typeof callback === 'function') {
                callback()
            }
        })

    }

}