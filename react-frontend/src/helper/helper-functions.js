const convertToQueryString = queryParams =>
    Object.keys(queryParams).length === 0 ? '' :
        '?' + Object.keys(queryParams)
            .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(queryParams[key])}`)
            .join('&')


export {convertToQueryString}