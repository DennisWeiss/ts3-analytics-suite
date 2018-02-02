import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Graph from 'react-graph-vis';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import axios from "axios/index";






var options = {
    layout: {
        hierarchical: false
    },
    edges: {
        color: "#000000"
    },
    physics: {
        barnesHut: {
            gravitationalConstant: -100000,
            centralGravity: 1
        }
    }
};

var events = {
    select: function(event) {
        var { nodes, edges } = event;
    }
};

/*function getGraph() {
    axios.get('/users.json').then(res => {

        for (let i = 0; i < res.data.length; i++) {
            graph.nodes.push({id: res.data[i].uniqueId, label: res.data[i].nickname});
        }
        console.log(graph);
    });
}*/

export default class SocialGraph extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            graph: {
                nodes: [

                ],
                edges: [

                ]
            }
        };


    }

    componentWillMount() {
        axios.get('/users.json').then(res => {
            let graphnodes = this.state.graph.nodes.slice();
            for (let i = 0; i < res.data.length; i++) {
                graphnodes.push({id: res.data[i].uniqueID, label: res.data[i].nickname});
            }
            this.setState({
                graph: {
                    nodes: graphnodes,
                    edges: this.state.graph.edges
                }
            });
            console.log(this.state.graph);
        });

        axios.get('/relations.json').then(res => {
            let edges = this.state.graph.edges.slice();
            for (let i = 0; i < res.data.length; i++) {
                if (res.data[i].channelRelation >= 0.1) {
                    edges.push({from: res.data[i].user, to: res.data[i].otherUser});
                }
            }
            this.setState({
                graph: {
                    nodes: this.state.graph.nodes,
                    edges: edges
                }
            });
        });

    }

    render() {
        return(
            <Graph graph={this.state.graph} options={options} events={events} />
        );
    }
}