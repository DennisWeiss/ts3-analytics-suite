import React from 'react';
import {withScriptjs, withGoogleMap, GoogleMap, Marker} from 'react-google-maps'
import './LocationHeatmap.css';
import HeatmapLayer from "react-google-maps/lib/components/visualization/HeatmapLayer";

/* global google */


const key = 'AIzaSyDOC-TY0tIePGLZ2IlTgzaIIo3UBjV3dCM';

export default class LocationHeatmap extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        console.log('prop-points', this.props.points);

        const MapComponent = withScriptjs(withGoogleMap((props) =>
            <GoogleMap defaultZoom={3} defaultCenter={{lat: 30, lng: 10}}>
                <HeatmapLayer
                    data={this.props.points}
                    options={{
                        data: this.props.points,
                        radius: 50
                    }}
                />
            </GoogleMap>));

        return(
            <MapComponent
                googleMapURL={'https://maps.googleapis.com/maps/api/js?key=' + key + '&v=3.exp&libraries=geometry,drawing,places'}
                loadingElement={<div style={{ height: `100%` }} />}
                containerElement={<div style={{ height: `100%` }} />}
                mapElement={<div style={{ height: `100%` }} />} />
        );
    }
}