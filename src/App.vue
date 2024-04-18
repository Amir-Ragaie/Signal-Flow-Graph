<template>
  <div id="app" class="app">
    <div class="output">
      <header @click="closeOutput()">X</header>
      <div class="container">
        <ul>
          <p>Transfer Function is {{TF}}</p>
        </ul>
        <ul>
          <p>Forward Paths</p>
          <li v-for="path in fPaths" :key="path">
            • {{ path }}
          </li>
        </ul>
        <ul>
          <p>Individual Loops</p>
          <li v-for="path in loops" :key="path">
            • {{ path }}
          </li>
        </ul>
        <ul>
          <p>Non Touching Loops</p>
          <li v-for="path in nonTLoops" :key="path">
            <div v-for="index in path" :key="index">
              • {{ loops[index] }}
            </div>
            <hr>
          </li>
        </ul>
        <ul>
          <p>Deltas</p>
          {{ deltas }}
        </ul>
        
      </div>
    </div>
    <div class="navBar">
      <ul>
        <li class="options">
          <img src="./assets/product.png" class="productimage">
        <input type="number" placeholder="Products Num" class="productno" v-model="nodes">
        </li>
        <li class="options" @click="clear()">
            <img src="./assets/clear.png" class="image">
            <p>Clear</p>
        </li>
        <li class="options" @click="start()">
            <img src="./assets/start.png" class="image">
            <p>Start</p>
        </li>
      </ul>  
    </div>
    <div class="screen">
      <div class="board" ref="parent" @click="create">
        <v-stage :config="configKonva">
          <v-layer>
            <v-circle v-for="shapeConfig in queues" :key="shapeConfig" :config="shapeConfig" ref="queue" @click="setShapes(shapeConfig)" @dragMove="updateQueue" />
            <v-text v-for="text in qNames" :key="text" :config="text" ref="text"/>
            <v-text v-for="text in gains" :key="text" :config="text" ref="text"/>
            <v-arrow v-for="shapeConfig in arrows" :key="shapeConfig" :config="shapeConfig" ref="arrow" />
          </v-layer>
        </v-stage>
        <img src="./assets/left-arrow.png" class="close" @click="close">
      </div>
      <div class="sidebar">
        <ul>
          <li class="item queue" @click="set()">
            <img src="./assets/q.png">
            <p>Node</p>
          </li>
          <li class="item connection" @click="enableConnection(!this.connection)">
            <img src="./assets/connection.png">
            <p>Connect</p>
          </li>
          <li class="item gain">
            <p>Gain: </p>
            <input type="text" v-model="curGain">
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { Stage, Layer, Rect, Line, Text, Arrow, Group } from 'vue-konva';
export default {
  name: 'App',
  data() {
    return {
      sideBar: true,
      execution: false,
      configKonva: {
        width: 2000,
        height: 1000
      },
      queues: [],
      current: '',
      shape1: null,
      shape2: null,
      connection: false,
      connections: [],
      arrows: [],
      qNames: [],
      nodes: 0,
      gains: [],
      curGain: 0,
      busy : true,
      fPaths: [],
      loops: [],
      nonTLoops: [],
      deltas: [],
      TF: 0,
    }
  },
  components: {
    VStage: Stage,
    VLayer: Layer,
    VRect: Rect,
    VLine: Line,
    VText: Text,
    VArrow: Arrow,
    VGroup: Group,
  },
  mounted() {
    const parentRect = this.$refs.parent;
    if(parentRect){
      this.configKonva.height = parentRect.offsetHeight;
      this.configKonva.width = parentRect.offsetWidth;
    }
    // this.fPaths = [[1,2,3,4,5],[1,5,4,7,9,1,3,4],[4,4,5,6,2,3,4,5],[1,2,3,4,5],[1,5,4,7,9,1,3,4],[4,4,5,6,2,3,4,5],[1,2,3,4,5],[1,5,4,7,9,1,3,4],[4,4,5,6,2,3,4,5],[1,2,3,4,5],[1,5,4,7,9,1,3,4],[4,4,5,6,2,3,4,5],[1,2,3,4,5],[1,5,4,7,9,1,3,4],[4,4,5,6,2,3,4,5],[1,2,3,4,5],[1,5,4,7,9,1,3,4],[4,4,5,6,2,3,4,5],[1,2,3,4,5],[1,5,4,7,9,1,3,4],[4,4,5,6,2,3,4,5],[1,2,3,4,5],[1,5,4,7,9,1,3,4],[4,4,5,6,2,3,4,5]];
    // this.loops = [[1,2,3,4,5],[1,5,4,7,9,1,3,4],[4,4,5,6,2,3,4,5]];
  },
  methods: {
    start(){
      const rows = this.queues.length;
      const cols = rows;
      let graph = new Array(rows);
      for (let i = 0; i < rows; i++) {
        graph[i] = new Array(cols);
        for(let j = 0; j < cols; j++){
          graph[i][j] = 0;
        }
      }
      for(let i = 0; i < this.arrows.length; i++){
        let x = this.arrows[i].idFrom;
        let y = this.arrows[i].idTo;
        graph[x][y] = this.arrows[i].gain;
      }
      console.log(graph);
      // console.log(JSON.stringify(graph));
      fetch('http://localhost:8080/solver',{
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        // body: JSON.stringify({graph: graph})
        body: JSON.stringify(graph)
      }).then(response =>{ 
        console.log(response)
        return response.json();
      }).then(data => {
        this.fPaths = data.forwardPaths;
        this.loops = data.loopsInGraph;
        this.nonTLoops = data.nonTouchingLoops;
        this.deltas = data.deltas;
        this.TF = data.transferFunction;
        console.log(data);
      });
      document.querySelector(".output").style.visibility = 'visible';
    },
    updateQueue(e){
      // console.log(e.target.id())
      let id = e.target.id();
      let x = e.target.x();
      let y = e.target.y();
      for(let i = 0; i < this.queues.length; i++){
        if(this.queues[i].id == e.target.id()){
          this.queues[i].x = x
          this.queues[i].y = y
        }
      }
      for(let i = 0; i < this.qNames.length; i++){
        if(this.qNames[i].id == id){
          this.qNames[i].x = x - 10;
          this.qNames[i].y = y -10;
          // console.log(e.target.x() + "  " + e.target.y())
        }
      }
      for(let i = 0; i < this.arrows.length; i++){
        let id1 = this.arrows[i].idFrom ;
        let id2 = this.arrows[i].idTo;
        this.arrows[i].points = this.getPoints(this.queues[id1], this.queues[id2]);
        for(let j = 0; j < this.gains.length; j++){
          let gain = this.gains[j];
          // console.log(id1 + ' ' + id2);
          // console.log(gain);
          if(gain.id == id1 + ' ' + id2){
            let points = this.arrows[i].points;
            let gpx = points[0];
            let gpy = points[1];
            if(points.length == 4){
              gpx = (points[0] + points[2])/2;
              gpy = (points[1] + points[3])/2;
            }
            else{
              gpx = points[2];
              gpy = points[3];
            }
            gain.x = gpx;
            gain.y = gpy;
          }
        }
      }
    },
    close(){
      let sideBar = document.querySelector(".sidebar")
      let arrow = document.querySelector(".close")
      if(this.sideBar){
        arrow.style.transform= "rotateY(0deg)"
        sideBar.style.width = "0px";
        sideBar.style.padding = "0px";
        arrow.style.right = "0px";
      }
      else{
        arrow.style.transform= "rotateY(180deg)"
        sideBar.style.width = "200px";
        sideBar.style.padding = "0px";
        arrow.style.right = "200px"
      }
      this.sideBar = !this.sideBar
    },
    set(){
      if(this.current == 'queue'){
        this.current = '';
        document.querySelector(".connection").classList.remove("active");
        document.querySelector(".queue").classList.remove("active");
      }
      else{
        this.current = 'queue';
        document.querySelector(".connection").classList.remove("active");
        document.querySelector(".queue").classList.add("active");
      }
    },
    clear(){
      this.queues = [];
      this.arrows = [];
      this.qNames = [];
      this.connections = []
      this.execution = false;
      document.querySelector(".connection").classList.remove("active");
      document.querySelector(".queue").classList.remove("active");
    },
    create(e){
      const parentRect = this.$refs.parent.getBoundingClientRect();
      let id = -1
      if(this.current == 'queue'){
        if(this.nodes <= 0){
          alert("You have reached Maximum available nodes");
          return;
        }
        this.nodes--;
        const queue = {
          id: this.queues.length,
          x: (e.clientX - parentRect.left ),
          y: (e.clientY - parentRect.top ),
          radius:20,
          fill:"black",
          stroke:"black",
          draggable: "true",
          name: ""+(this.queues.length),
          type: "queue",
          queueId: this.queues.length+1
        };
        id = queue.id
        this.queues.push(queue);
        const qname = {
          id: queue.id,
          x: queue.x-10,
          y: queue.y-10,
          text: queue.name,
          fontSize: 20,
          fill: 'white',
          fontFamily: 'calibri'
          // draggable: true,
        }
        this.qNames.push(qname);
      }
      // this.current = '';
      // this.set();
    },
    getPoints(shape1, shape2) {
      let center1X = shape1.x ;
      let center1Y = shape1.y ;
      let center2X = shape2.x;
      let center2Y = shape2.y;
      let points = [];
      let idDiff = shape2.id - shape1.id;
      if(idDiff != 1){
        if(idDiff < 0){
          idDiff--;
          points.push(center1X);
          points.push(center1Y + 20);
        }
        else{
          idDiff++;
          points.push(center1X);
          points.push(center1Y - 20);
        }
        points.push((center1X + center2X)/2);
        let offset = 0;
        if(idDiff < 0)offset = Math.max(center1Y, center2Y);
        else offset = Math.min(center1Y, center2Y);
        points.push(offset - 30*(idDiff));
        if(idDiff < 0){
          points.push(center2X);
          points.push(center2Y + 20);
        }
        else{
          points.push(center2X);
          points.push(center2Y - 20);
        }
      }
      else{
        points.push(center1X +20);
        points.push(center1Y);
        points.push(center2X-20);
        points.push(center2Y);
      }
      return points;
    },
    connect(){
      this.busy = true;
      document.querySelector(".queue").classList.remove("active");
      this.current = '';
      if(this.shape1!=null && this.shape2!=null && this.connection){
        let points = this.getPoints(this.shape1, this.shape2);
        const arrow = {
          idFrom: this.shape1.id,
          idTo: this.shape2.id,
          points: points,
          stroke: 'red',
          gain: this.curGain,
          tension: 1
        };
        let exist = false;
        for(let i = 0; i < this.arrows.length; i++){
          console.log(this.arrows[i].idFrom + ' ' +this.arrows[i].idTo)
          if(this.arrows[i].idFrom == arrow.idFrom && this.arrows[i].idTo == arrow.idTo){
            if(arrow.gain == 0){
              this.arrows = this.arrows.filter(arrow => !(this.arrows[i].idFrom == arrow.idFrom && this.arrows[i].idTo == arrow.idTo))
              this.gains = this.gains.filter(gain => !(gain.id == arrow.idFrom + ' ' + arrow.idTo));
            }
            else{
              this.arrows[i].gain = arrow.gain;
              this.gains[i].text = arrow.gain;
            }
            exist= true;
            console.log("yee")
            break;
          }
        }
        if(this.curGain != 0 && !exist){
          let gpx = points[0];
          let gpy = points[1];
          if(points.length == 4){
            gpx = (points[0] + points[2])/2;
            gpy = (points[1] + points[3])/2;
          }
          else{
            gpx = points[2];
            gpy = points[3];
          }
          const gain = {
            id: arrow.idFrom + ' ' + arrow.idTo,
            x: gpx,
            y: gpy,
            text: arrow.gain,
            fontSize: 20,
            fill: 'black',
            fontFamily: 'calibri'
          }
          this.gains.push(gain);
          this.connections.push({shape1: this.shape1, shape2: this.shape2});
          this.arrows.push(arrow);
        }
        else if(this.curGain == 0 && !exist){
          alert("Gain Cannot be zero!");
        }
        this.shape1.fill = 'black'
        this.shape2.fill = 'black'
        this.shape1 = null;
        this.shape2 = null;
        document.querySelector(".queue").classList.remove("active");
      }
    },
    enableConnection(connection){
      this.current = '';
      document.querySelector(".queue").classList.remove("active");
      if(connection == false){
        this.connection = false
        document.querySelector(".connection").classList.remove("active");
        if(this.shape1 != null)this.shape1.fill = 'black'
        if(this.shape2 != null)this.shape2.fill = 'black'
        this.shape1 = null
        this.shape2 = null
      }
      else{
        this.connection = true;
        document.querySelector(".queue").classList.remove("active");
        document.querySelector(".connection").classList.add("active");
      }
      // console.log(this.connection);
    },
    setShapes(shape){
      if(this.connection){
        if(this.shape1 == null){
          this.shape1 = shape;
          this.shape1.fill = 'red'
        }
        else if(this.shape2 == null){
          this.shape2 = shape;
          this.shape2.fill = 'green'
          this.connect();
        }
      }
    },
    closeOutput(){
      document.querySelector(".output").style.visibility = 'hidden'
    }
  }
}
</script>

<style>
.app{
  width: 100vw;
  height: 100vh;
  background-color:#EEE;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
.navBar{
  height: 70px;
  width: 100%;
  padding: 10px;
  display: flex;
  /* justify-content: flex-end; */
  justify-content: space-around;
  align-items: center;
  padding-right: 150px;
  padding-left: 100px;
  background-color: rgb(133, 194, 214);
  color: white;
  font-size: 20px;
  font-family: calibri;
  transform: rotateX('angle');
}
.navBar ul{
  list-style: none;
  display: flex;
  /* justify-content: flex-end; */
  justify-content: space-around;
  width: 100%;
  margin: 0;
  padding: 0;
}
.navBar li{
  color:black;
  font-weight: bold;
  transition: 0.3s;
} 
.navBar li:hover{
  color: #EEE;
}
.navBar .options{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  cursor: pointer;
  transition: 0.3s;
  margin: 0 20px;
}
.image{
  margin-right: 5px;
}
.screen{
  width: 100%;
  height: calc(100% - 50px);
  display: flex;
  position: relative;
}
.board{
  width: 100%;
  /*background-image:url('./assets/background.png') ;
  background-size: cover;*/
  position: relative;
}
.close{
  position: absolute;
  right: 197px;
  width: 20px;
  height: 40px;
  top: 50%;
  background: #eeeeeeb8;
  padding: 5px 0;
  cursor: pointer;
  transform: rotateY(180deg);
  transition:0.5s;
  z-index: 10;
}
.close:hover{
  background:#aeaeae;
}
.sidebar{
  height: 100%;
  right: 0;
  position: absolute;
  padding: 10px;
  width: 200px;
  overflow: hidden;
  /* background: lightblue; */
  background-color: lightgray;
  font-weight: bold;
  font-size: 18px;
  font-family: calibri;
  transition: width 0.5s ease;
}
.sidebar ul{
  list-style: none;
  display: flex;
  flex-direction: column;
}
.sidebar li{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  cursor: pointer;
  transition: 0.3s;
}
.sidebar li:hover{
  background: lightblue;
}
li img{
  width: 30px;

}
.sidebar .gain input{
  width: 100px;
  padding: 5px;
  border: none;
  border-radius: 10px;
  color: grey;
}
.active{
  background: lightblue;
}
.navBar .productno{
  width: 140px;
  height: 30px;
  border-radius: 8px;
  border: none;
  outline: none;
  padding: 5px;
  margin-left: 10px;
  font-size: 20px;
  font-family: calibri;
  text-align: center;
}
.output{
  display: flex;
  flex-direction: column;
  background: lightblue;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 60%;
  height: 70%;
  overflow: hidden;
  z-index: 1000;
  padding-top: 20px;
  visibility: hidden;
}
.output header{
  position: fixed;
  top: 0;
  right: 0;
  background: rgb(208, 237, 247);
  cursor: pointer;
  color: blue;
  font-weight: bold;
  z-index: 10001;
}
.output header:hover{
  color: red;
}

.output .container{
  display: flex;
  flex-direction: column;
  padding: 20px;
  overflow: scroll;
  height: 100% ;
}
.output .container ul{
  display: flex;
  list-style: none;
  flex-direction: column;
  padding-bottom:20px ;
  border-bottom: 2px solid black;
  
}
.output .container ul p{
  font-size: 25px;
  font-weight: bold;
  margin: 10px;
}
::-webkit-scrollbar {
  width: 10px; /* width of the scrollbar */
}

/* Track */
::-webkit-scrollbar-track {
  background: lightblue; /* color of the track */
}

/* Handle */
::-webkit-scrollbar-thumb {
  background: rgb(102, 181, 255); /* color of the scrollbar handle */
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: grey; /* color of the scrollbar handle on hover */
}
</style>