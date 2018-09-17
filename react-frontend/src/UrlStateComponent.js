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
        this.valueResolvers = {}
    }

    componentDidMount() {
        const getIdResolverPromise = (state, urlState, resolvers, resolve) => {
            if (resolve == null) {
                return new Promise(resolve => getIdResolverPromise(state, urlState, resolvers, resolve))
            }
            const currentState = {...state}
            const currentUrlState = {...urlState}
            if (Object.keys(currentUrlState).length === 0) {
                resolve(currentState)
            } else {
                const key = Object.keys(currentUrlState)[1]
                if (resolvers[key] == null) {
                    currentState[key] = currentUrlState[key]
                    delete currentUrlState[key]
                    getIdResolverPromise(currentState, currentUrlState, resolvers, resolve)
                } else if (typeof resolvers[key] === 'function') {
                    resolvers[key](currentUrlState[key])
                        .then(value => {
                            currentState[key] = value
                            delete currentUrlState[key]
                            getIdResolverPromise(currentState, currentUrlState, resolvers, resolve)
                        })
                } else {
                    throw `resolver of ${key} has to be a function with a promise`
                }
            }
        }

        const state = {}
        Object.keys(this.state).forEach(key => state[key] = this.state[key])
        getIdResolverPromise(state, this.urlState, this.valueResolvers)
            .then(resolvedState => {
                this.setState(resolvedState)
            })

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
                            throw `No id mapper provided for ${key}! You always need to provide a mapper if the value is not a primitive data type`
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