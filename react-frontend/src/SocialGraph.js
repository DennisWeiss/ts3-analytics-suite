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
    nodes: {
        shape: 'box',
        font: {
            size: 20,
            face: 'helvetica'
        }
    },
    edges: {
        arrows: {
            to: {
                enabled: false
            },
            middle: {
                enabled: false
            },
            from: {
                enabled: false
            }
        },
        color: "#000000",
        scaling: {
            min: 0.01,
            max: 20,
        }
    },
    physics: {
        barnesHut: {
            gravitationalConstant: -200000,
            centralGravity: 8
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
        axios.get('http://localhost:8080/ts3/users').then(res => {
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

        axios.get('http://localhost:8080/ts3/relations').then(res => {
            let edges = this.state.graph.edges.slice();
            for (let i = 0; i < res.data.length; i++) {
                if (res.data[i].channelRelation >= 0.03) {
                    edges.push({from: res.data[i].user, to: res.data[i].otherUser, value: res.data[i].totalRelation});
                }
            }
            this.setState({
                graph: {
                    nodes: this.state.graph.nodes,
                    edges: edges
                }
            });
            console.log(this.state.graph);
        });

    }

    render() {
        return(
            <Graph graph={this.state.graph} options={options} events={events} />
        );
    }
}