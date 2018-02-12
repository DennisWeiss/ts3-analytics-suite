import React from 'react';
import GoogleMap from 'google-map-react';
import './Location.css';
import './UserData.css';


const AnyReactComponent = ({ text }) => <div>{text}</div>;

export default class Location extends React.Component {
    static defaultProps = {
        center: {lat: 59.95, lng: 30.33},
        zoom: 11
    };

    render() {
        return(
            <div className='overview'>
                <GoogleMap/>
            </div>
        );
    }
}