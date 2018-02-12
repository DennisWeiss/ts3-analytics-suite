import React from 'react';
import {withScriptjs, withGoogleMap, GoogleMap, Marker} from 'react-google-maps'
import './Location.css';
import './UserData.css';


const key = 'AIzaSyDOC-TY0tIePGLZ2IlTgzaIIo3UBjV3dCM';

export default class Location extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const MapComponent = withScriptjs(withGoogleMap((props) =>
            <GoogleMap defaultZoom={6} defaultCenter={{lat: this.props.lat, lng: this.props.lng}}>
                <Marker position={{lat: this.props.lat, lng: this.props.lng}}/>
            </GoogleMap>));

        return(
            <div className='overview'>
                <MapComponent
                    googleMapURL={'https://maps.googleapis.com/maps/api/js?key=' + key + '&v=3.exp&libraries=geometry,drawing,places'}
                    loadingElement={<div style={{ height: `100%` }} />}
                    containerElement={<div style={{ height: `400px` }} />}
                    mapElement={<div style={{ height: `100%` }} />} />
            </div>
        );
    }
}