import React from 'react';
import './LocationHeatmap.css';
import 'react-heatmap/dist/react-heatmap';
import Heatmap from 'heatmapjs/build/heatmap.js';


export default class LocationHeatmap extends React.Component {
    render() {
        return(
            <ReactHeatmap max={5} data={data} />
        );
    }
}