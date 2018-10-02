import React from 'react';
import './index.css';
import Graph from 'react-graph-vis';
import axios from "axios/index";
import './SocialGraph.css';


const bannedColor = '#ff4444';
const colors = ['#00cc00', '#66ffff', '#009933', '#cc00ff', '#ffff00', '#ff3399', '#ffffcc', '#ffffff'];
var channelToColor = {};
var channelIndex = 0;


var options = {
    autoResize: true,
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
            gravitationalConstant: -300000,
            centralGravity: 8
        }
    }
};


export default class SocialGraph extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            graph: {
                nodes: [],
                edges: []
            }
        };
    }

    componentWillMount() {
        let graphnodes = this.state.graph.nodes.slice();
        axios.get('http://gr-esports.de:8092/api/ts3/users').then(res => {
            axios.get('http://gr-esports.de:8090/api/ts3/currentusers').then(res2 => {
                axios.get('http://gr-esports.de:8090/api/ts3/bans').then(res3 => {
                    for (let i = 0; i < res.data.length; i++) {
                        let node = {id: res.data[i].uniqueId, label: res.data[i].nickName};
                        for (let j = 0; j < res2.data.length; j++) {
                            if (res.data[i].uniqueId === res2.data[j].id) {
                                if (channelToColor[res2.data[j].channel] != null) {
                                    node.color = channelToColor[res2.data[j].channel];
                                } else {
                                    node.color = colors[channelIndex];
                                    channelToColor[res2.data[j].channel] = colors[channelIndex];
                                    channelIndex++;
                                    if (channelIndex > colors.length) {
                                        channelIndex = 0;
                                    }
                                }
                            }
                        }
                        for (let j = 0; j < res3.data.length; j++) {
                            if (node.id === res3.data[j]) {
                                node.color = bannedColor;
                                console.log(res.data[i].uniqueId);
                            }
                        }
                        graphnodes.push(node);
                    }

                    axios.get('http://gr-esports.de:8092/api/ts3/relations').then(res4 => {
                        let edges = this.state.graph.edges.slice();
                        for (let i = 0; i < res4.data.length; i++) {
                            if (res4.data[i].channelRelation >= 0.03 || res4.data[i].totalRelation >= 0.3) {
                                let color1 = '#7da9ff';
                                let edge = {
                                    from: res4.data[i].client1,
                                    to: res4.data[i].client2,
                                    value: res4.data[i].totalRelation,
                                    color: {
                                        color: color1,
                                        inherit: false,
                                        highlight: '#b200cc'
                                    }
                                };
                                for (let j = 0; j < graphnodes.length; j++) {
                                    if (res4.data[i].client1 === graphnodes[j].id) {
                                        if (graphnodes[j].color != null) {
                                            color1 = graphnodes[j].color;
                                        }
                                    }
                                }
                                for (let j = 0; j < graphnodes.length; j++) {
                                    if (res4.data[i].client2 === graphnodes[j].id) {
                                        if (graphnodes[j].color != null) {
                                            if (graphnodes[j].color === color1 && color1 !== bannedColor) {
                                                edge.color.color = color1;
                                            }
                                        }
                                    }
                                }
                                edges.push(edge);
                            }
                        }
                        this.setState({
                            graph: {
                                nodes: graphnodes,
                                edges: edges
                            }
                        });
                    });
                });
            });
        });
    }

    componentDidMount() {

    }

    setNetworkInstance = network => {
        this.network = network;
        console.log(this.network.getScale());
    };

    render() {

        return <Graph getNetwork={this.setNetworkInstance} graph={this.state.graph} options={options}/>
    }
}