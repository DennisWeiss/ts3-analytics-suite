import React from 'react';
import './LocationHeatmap.css';
import {Map, Marker, Popup, TileLayer} from 'react-leaflet';
import HeatmapLayer from 'react-leaflet-heatmap-layer';
import {addressPoints} from './realworld.10000.js';

/* global google */


const key = 'AIzaSyDOC-TY0tIePGLZ2IlTgzaIIo3UBjV3dCM'

const position = [0, 0]

export default class LocationHeatmap extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className='location-heatmap'>
                <Map center={position} zoom={13}>
                    <HeatmapLayer
                        fitBoundsOnLoad
                        fitBoundsOnUpdate
                        points={this.props.points}
                        radius={40}
                        blur={30}
                        maxZoom={5}
                        longitudeExtractor={data => data.lng}
                        latitudeExtractor={data => data.lat}
                        intensityExtractor={data => data.weight} />
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
                    />
                </Map>
            </div>
        )
    }
}
