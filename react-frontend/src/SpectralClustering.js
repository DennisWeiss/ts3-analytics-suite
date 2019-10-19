import React from 'react';
import './index.css';
import Graph from 'react-graph-vis';
import axios from "axios/index";
import './SpectralClustering.css'
import * as tf from '@tensorflow/tfjs'


const options = {
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


const filterNodesWithEdges = (nodes, edges) => {
  const idToNode = {}
  nodes.forEach(node => idToNode[node.id] = node)
  const filteredNodes = new Set()
  edges.forEach(edge => {
    filteredNodes.add(idToNode[edge.from])
    filteredNodes.add(idToNode[edge.to])
  })
  return [...filteredNodes]
}

const adjacencyMatrix = (nodes, edges) => {
  const nodeIdToIndex = {}
  nodes.forEach((node, index) => nodeIdToIndex[node.id] = index)
  let A = tf.zeros([nodes.length, nodes.length])
  edges.forEach(edge => {
    A = A.add(tf.scatterND(
      tf.tensor([[nodeIdToIndex[edge.from], nodeIdToIndex[edge.to]]], undefined, 'int32'),
      tf.tensor([1]),
      [nodes.length, nodes.length]
    ))
  })
  return A
}

const degreeMatrix = (nodes, edges) => {
  const nodeIdToIndex = {}
  nodes.forEach((node, index) => nodeIdToIndex[node.id] = index)
  let D = tf.zeros([nodes.length, nodes.length])
  edges.forEach(edge => {
    D = D.add(tf.scatterND(
      tf.tensor([[nodeIdToIndex[edge.from], nodeIdToIndex[edge.from]]], undefined, 'int32'),
      tf.tensor([1]),
      [nodes.length, nodes.length]
    ))
  })
  return D
}

export default class SpectralClustering extends React.Component {
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

  componentDidMount() {
    let graphnodes = this.state.graph.nodes.slice();
    axios.get('https://gr-esports.de:8092/api/ts3/users').then(res => {
      for (let i = 0; i < res.data.length; i++) {
        let node = {id: res.data[i].uniqueId, label: res.data[i].nickName};
        graphnodes.push(node);
      }

      axios.get('https://gr-esports.de:8092/api/ts3/relations').then(res4 => {
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
            edges.push(edge);
          }
        }

        graphnodes = filterNodesWithEdges(graphnodes, edges)

        const graphLaplacian = degreeMatrix(graphnodes, edges).sub(adjacencyMatrix(graphnodes, edges))
        console.log(graphLaplacian.arraySync())

        // console.log(subtract(degreeMatrix(graphnodes, edges), adjacencyMatrix(graphnodes, edges)))

        this.setState({
          graph: {
            nodes: graphnodes,
            edges: edges
          }
        });
      });
    });
  }

  setNetworkInstance = network => {
    this.network = network;
    console.log(this.network.getScale());
  };

  render() {

    return <Graph getNetwork={this.setNetworkInstance} graph={this.state.graph} options={options}/>
  }
}