import React from 'react';
// import {Map, MapLayer, Marker, Popup, TileLayer} from 'react-leaflet';
import './Location.css';
import './UserData.css';
import './index.css'
import {GoogleMap, Marker, withGoogleMap, withScriptjs} from "react-google-maps";


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
                    containerElement={<div style={{ height: `380px` }} />}
                    mapElement={<div style={{ height: `100%` }} />} />
            </div>
        );

        // const position = [this.props.lat, this.props.lng]
        //
        // console.log('props', this.props)
        //
        // return (
        //     <div className='location-map'>
        //         <Map center={position} zoom={7}>
        //             <TileLayer
        //                 attribution="&amp;copy <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
        //                 url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        //             />
        //             <Marker position={position}>
        //                 <Popup>
        //                     <span>Some text</span>
        //                 </Popup>
        //             </Marker>
        //         </Map>
        //     </div>
        // )
    }
}