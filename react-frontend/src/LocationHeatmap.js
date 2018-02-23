import React from 'react';
import './LocationHeatmap.css';
import { Map, Marker, Popup, TileLayer } from 'react-leaflet';
import HeatmapLayer from '../src/HeatmapLayer';
import { addressPoints } from './realworld.10000.js';

/* global google */


const key = 'AIzaSyDOC-TY0tIePGLZ2IlTgzaIIo3UBjV3dCM';

export default class LocationHeatmap extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        return(
            <div>
                <Map center={[0,0]} zoom={13}>
                    <HeatmapLayer
                        fitBoundsOnLoad
                        fitBoundsOnUpdate
                        points={addressPoints}
                        longitudeExtractor={m => m[1]}
                        latitudeExtractor={m => m[0]}
                        intensityExtractor={m => parseFloat(m[2])} />
                    <TileLayer
                        url='http://{s}.tile.osm.org/{z}/{x}/{y}.png'
                        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                    />
                </Map>
            </div>
        );
    }
}
