import React from 'react'
import './LocationHeatmap.css'
import {Map, Marker, Popup, TileLayer} from 'react-leaflet'
import HeatmapLayer from 'react-leaflet-heatmap-layer'


// source: https://wiki.openstreetmap.org/wiki/Zoom_levels
const zoomLevelScaleAt0 = 156412


class LocationHeatmap extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            viewport: {
                center: [0, 0],
                zoom: 2,
            }
        }
    }

    onViewportChange = viewport => {
        this.setState({
            viewport: {
                center: viewport.center,
                zoom: Math.min(11, viewport.zoom)
            }
        })
    }

    render() {
        console.log('state', this.state)

        return (
            <div className='location-heatmap'>
                <Map
                    viewport={this.state.viewport}
                    onViewportChange={this.onViewportChange}>
                    <HeatmapLayer
                        fitBoundsOnLoad={false}
                        fitBoundsOnUpdate={false}
                        points={this.props.points}
                        radius={25}
                        blur={30}
                        maxZoom={2}
                        max={8}
                        longitudeExtractor={data => data.lng}
                        latitudeExtractor={data => data.lat}
                        intensityExtractor={data => data.weight}/>
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
                    />
                </Map>
            </div>
        )
    }
}

export default LocationHeatmap
