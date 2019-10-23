import React from 'react'
import './index.css'
import Graph from 'react-graph-vis'
import axios from 'axios/index'
import './SpectralClustering.css'
import ReactHighcharts from 'react-highcharts'
import {Card} from 'antd'


const round = digits => x => Math.round(Math.pow(10, digits) * x) / Math.pow(10, digits)

const highchartOptions = eigenValues => ({
  title: {
    text: 'Eigen values of graph\'s laplacian matrix of connectivity of users'
  },
  subtitle: {
    text: 'Sorted from small to large'
  },
  yAxis: {
    title: {
      text: 'Eigen value'
    }
  },
  legend: {
    layout: 'vertical',
    align: 'right',
    verticalAlign: 'middle'
  },
  plotOptions: {
    series: {
      label: {
        connectorAllowed: false
      },
    }
  },
  series: [{
    name: 'Eigen values',
    data: eigenValues.map(round(5))
  }],
})

const colors = ['#EA352E',
  '#FF8D00', '#FFED00', '#40B14B', '#1061c3', '#9210AD', '#974C00', '#F87E7D', '#FFCD7A', '#FEFF00',
  '#7ED085', '#5FB3F9', '#D190DA', '#D0AF8F', '#BDBDBD', '#FFCCD2', '#FFE1AF', '#FEFF85', '#C6E7C8', '#B9DDFC',
  '#EBC7EF', '#EEDCCA', '#E5E5E5', '#FFEBEE', '#FFF3DF', '#FFFEE6', '#E7F5E9', '#E2F2FE', '#F4E4F5', '#F2E8DE',
  '#FFFFFF']

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
    color: '#000000',
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
}

var shuffle = function (array) {

  var currentIndex = array.length
  var temporaryValue, randomIndex

  // While there remain elements to shuffle...
  while (0 !== currentIndex) {
    // Pick a remaining element...
    randomIndex = Math.floor(Math.random() * currentIndex)
    currentIndex -= 1

    // And swap it with the current element.
    temporaryValue = array[currentIndex]
    array[currentIndex] = array[randomIndex]
    array[randomIndex] = temporaryValue
  }

  return array

}

const distanceSquared = (a, b) => {
  let dSquared = 0
  for (let i = 0; i < Math.min(a.length, b.length); i++) {
    const delta = a[i] - b[i]
    dSquared += delta * delta
  }
  return dSquared
}

const k_means = k => points => {
  const centroids = shuffle([...points]).slice(0, k)
  const clusters = new Array(points.length)
  clusters.fill(0, 0, clusters.length)
  for (let m = 0; m < 50; m++) {
    for (let i = 0; i < points.length; i++) {
      let minDistanceSquared = null
      let closestCentroid = null
      for (let j = 0; j < centroids.length; j++) {
        const d = distanceSquared(points[i], centroids[j])
        if (minDistanceSquared == null || d < minDistanceSquared) {
          minDistanceSquared = d
          closestCentroid = j
        }
      }
      clusters[i] = closestCentroid
    }
    for (let i = 0; i < centroids.length; i++) {
      const summedPoints = new Array(points[0].length)
      summedPoints.fill(0, 0, summedPoints.length)
      let n = 0
      for (let j = 0; j < clusters.length; j++) {
        if (i === clusters[j]) {
          n++
          for (let k = 0; k < summedPoints.length; k++) {
            summedPoints[k] += points[j][k]
          }
        }
      }
      if (n > 0) {
        centroids[i] = summedPoints.map(point => point / n)
      }
    }
  }
  return clusters
}

const spectralClustering = (eigenPairs, users, k) => {
  const userEigenVectorCoordinates = []
  let numberOfZeroEigenValues = 0
  for (let i = 0; i < eigenPairs.length; i++) {
    if (eigenPairs[i].value > Math.pow(10, -8)) {
      numberOfZeroEigenValues = i
      break
    }
  }
  console.log('numberOfZeroEigenValues', numberOfZeroEigenValues)
  for (let i = 0; i < users.length; i++) {
    const coordinates = []
    for (let j = 0; j < k; j++) {
      coordinates.push(eigenPairs[j].vector.data[i])
    }
    userEigenVectorCoordinates.push(coordinates)
  }
  console.log(userEigenVectorCoordinates)
  console.log(eigenPairs[numberOfZeroEigenValues].value)
  const clusters = k_means(k)(userEigenVectorCoordinates)
  const userToCluster = {}
  for (let i = 0; i < users.length; i++) {
    userToCluster[users[i]] = clusters[i]
  }
  return userToCluster
}

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

export default class SpectralClustering extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      users: [],
      graph: {
        nodes: [],
        edges: []
      }
    }
  }

  componentDidMount() {
    axios.get('https://gr-esports.de:8092/api/ts3/users').then(res => {
      axios.get('https://gr-esports.de:8092/api/ts3/relations').then(res2 => {
        axios.get('https://gr-esports.de:8092/api/ts3/spectral-clustering/eigendecomposition').then(res3 => {
          console.log('eigenvalues', res3.data.eigenPairs.map(eigenPair => eigenPair.value))
          const userToCluster = spectralClustering(res3.data.eigenPairs, res3.data.users, 42)
          console.log(userToCluster)
          console.log(Object.keys(userToCluster).length)
          let edges = this.state.graph.edges.slice()
          for (let i = 0; i < res2.data.length; i++) {
            if (res2.data[i].channelRelation >= 0.03 || res2.data[i].totalRelation >= 0.3) {
              let color1 = '#7da9ff'
              let edge = {
                from: res2.data[i].client1,
                to: res2.data[i].client2,
                value: res2.data[i].totalRelation,
                color: {
                  color: color1,
                  inherit: false,
                  highlight: '#b200cc'
                }
              }
              edges.push(edge)
            }
          }

          let graphnodes = []

          const users = Object.keys(userToCluster)

          for (let i = 0; i < users.length; i++) {
            let nickname = ''
            for (let j = 0; j < res.data.length; j++) {
              if (res.data[j].uniqueId === users[i]) {
                nickname = res.data[j].nickName
              }
            }
            let node = {
              id: users[i],
              label: nickname,
              color: colors[userToCluster[users[i]] % colors.length]
            }
            graphnodes.push(node)

          }

          // graphnodes = filterNodesWithEdges(graphnodes, edges)

          console.log(graphnodes)

          this.setState({
            eigenValues: res3.data.eigenPairs.map(eigenPair => eigenPair.value),
            graph: {
              nodes: graphnodes,
              edges: edges
            }
          })
        })
      })
    })
  }

  setNetworkInstance = network => {
    this.network = network
    console.log(this.network.getScale())
  }

  render() {

    return <>
      <Graph getNetwork={this.setNetworkInstance} graph={this.state.graph} options={options}/>
      <Card>
        <ReactHighcharts config={highchartOptions(this.state.eigenValues || [])}/>
      </Card>
    </>
  }
}