import React from 'react';
import './index.css';
import Graph from 'react-graph-vis';
import './UserSocialGraph.css';
import './SocialGraph.css';
import axios from "axios/index";
import './UserData.css';
import {Card} from "antd";

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

// var events = {
//     select: function(event) {
//         var { nodes, edges } = event;
//     }
// };

export default class UserSocialGraph extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            network: {},
            users: [],
            graph: {
                nodes: [],
                edges: []
            }
        }
    }

    componentWillMount() {
        let graphnodes = this.state.graph.nodes.slice();
        axios.get('http://gr-esports.de:8081/ts3/users').then(res => {
            axios.get('http://gr-esports.de:8081/ts3/currentusers').then(res2 => {
                axios.get('http://gr-esports.de:8081/ts3/bans').then(res3 => {
                    //console.log(res3.data);
                    for (let i = 0; i < res.data.length; i++) {
                        let node = {id: res.data[i].uniqueID, label: res.data[i].nickname};
                        for (let j = 0; j < res2.data.length; j++) {
                            if (res.data[i].uniqueID === res2.data[j].id) {
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
                                //console.log(channelToColor);
                                //node.color = '#009933';
                            }
                        }
                        //console.log(res3.data);
                        for (let j = 0; j < res3.data.length; j++) {
                            //console.log(res3.data[j]);
                            if (node.id === res3.data[j]) {
                                if (i === 45) {
                                    console.log(res3.data[j]);
                                }
                                node.color = bannedColor;
                                console.log(res.data[i].uniqueID);
                            }
                        }
                        graphnodes.push(node);
                    }
                    //console.log(this.state.graph);

                    axios.get('http://gr-esports.de:8081/ts3/relations').then(res4 => {
                        let edges = this.state.graph.edges.slice();
                        for (let i = 0; i < res4.data.length; i++) {
                            if (res4.data[i].channelRelation >= 0.03 || res4.data[i].totalRelation >= 0.3) {
                                let color1 = '#7da9ff';
                                //let nodes = this.state.graph.nodes;
                                let edge = {
                                    from: res4.data[i].user,
                                    to: res4.data[i].otherUser,
                                    value: res4.data[i].totalRelation,
                                    color: {
                                        color: color1,
                                        inherit: false,
                                        highlight: '#b200cc'
                                    }
                                };
                                for (let j = 0; j < graphnodes.length; j++) {
                                    if (res4.data[i].user === graphnodes[j].id) {
                                        if (graphnodes[j].color != null) {
                                            color1 = graphnodes[j].color;
                                        }
                                    }
                                }
                                for (let j = 0; j < graphnodes.length; j++) {
                                    if (res4.data[i].otherUser === graphnodes[j].id) {
                                        if (graphnodes[j].color != null) {
                                            if (graphnodes[j].color === color1 && color1 !== bannedColor) {
                                                //console.log(i);
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
                        }, () => this.select(this.props.user.uniqueID));
                        console.log(this.state.graph);
                    });
                });
            });
        });
    }

    componentDidMount() {
        this.props.onRef(this);
    }

    componentWillUnmount() {
        this.props.onRef(undefined);
    }

    setNetworkInstance = network => {
        this.network = network;
        if (this.network != null) {
            this.network.on('selectNode', (click) => this.props.onSelect(click.nodes[0]))
        }
    };

    select(id) {
        if (id != null) {
            this.network.setSelection({
                nodes: [id],
                edges: []
            });
            this.network.focus(id, {
                scale: 0.6,
                locked: true,
                animation: false
            });
        }
    }

    render() {
        // if (this.props.loading) {
        //     return <Card>
        //         <div className='loader-wrapper'>
        //             <div className='loader'/>
        //         </div>
        //     </Card>
        // }
        return (
            <Card>
                <Graph style={{height: '380px'}} getNetwork={this.setNetworkInstance} graph={this.state.graph}
                       options={options}/>
            </Card>
        );
    }
}