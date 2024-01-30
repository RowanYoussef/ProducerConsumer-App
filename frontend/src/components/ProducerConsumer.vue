<template>
  <div class="body" id="body">
  <div id="toolbar">
    <div>
      <button id="machine" @click="addMachine()"><i class="fa-solid fa-plus"></i>   Add Machine</button>
    </div>
    <div>
      <button id="queue" @click="addQueue()" ><i class="fa-solid fa-plus"></i>   Add Queue</button>
    </div>
    <div>
      <button id="line" @click="connectQtoM();connectMtoQ();" ><i class="fa-solid fa-arrow-up"></i>  Connect
        Elements</button>
      </div>
      <label for="select">From</label>
        <select id="select" v-model="From" >
        <option v-for=" (machine) in machines" :key="machine"  >{{ machine.id }}</option>
        <option v-for=" (queue) in queues" :key="queue"  >{{ queue.id }}</option>
      </select>
      <label for="select">To</label>
      <select id="select" v-model="To">
        <option v-for=" (machine) in machines" :key="machine"  >{{ machine.id }}</option>
        <option v-for=" (queue) in queues" :key="queue"  >{{ queue.id }}</option>
      </select>
    <div id="run">
      <button @click="Simulate()"><i class="fa-solid fa-circle-play"></i>  Play</button>
      <input type="text" placeholder="enter number of products" v-model="productsNum">
    </div>
    <div id="replay">
      <button  @click="Replay()"><i class="fa-solid fa-retweet"></i>  Replay</button>
    </div>
    <div id="clear">
      <button @click="Clear()" > Clear</button>
    </div>
  </div>
  <canvas id="canvas" width="1355" height="690"
    style=" background-color: white; margin-left: 50px;border-radius: 25px; border:3px solid black"
    @click="draw">
  </canvas>
</div>
</template>

<script>
export default {
  data(){
    return {
      ctx: null,
      can: null,
      width: null,
      height: null,
      machines: [],
      queues: [],
      machineClicked: false,
      queueClicked: false,
      From: '',
      To: '',
      message: '',
      x1: '',
      x2: '',
      y1: '',
      y2: '',
      connections: [],
      productsNum: 0,
      productsNum1: 0,
      UpdatedQueues: [
           {
            products: [],
           }
      ],
      UpdatedMachines: [],

    }

  },
  methods: {
    draw (e) {
      var rect = this.can.getBoundingClientRect();
      var x1 = e.clientX - rect.left;
     var y1 = e.clientY - rect.top;
     if(this.machineClicked){
      this.ctx.fillStyle = "palevioletred";
      this.ctx.beginPath();
     var machine = {
       x: x1,
        y: y1,
        radius: 30,
        id: "M"+this.machines.length.toString(),
      }
      this.SendMachine(machine.id);
      this.machines.push(machine);
      this.ctx.arc(x1,y1,30, 0, 2 * Math.PI);
      this.ctx.fill();
      this.ctx.fillStyle = 'black';
      this.ctx.fillText(machine.id, x1, y1);
     }else if(this.queueClicked){
      this.ctx.fillStyle = "rgb(50, 225, 255)";
      var queue = {
        x: x1,
        y: y1,
        width: 50,
        height: 40,
        id: "Q"+this.queues.length.toString(),
      }
      this.SendQueue(queue.id);
      this.queues.push(queue);
      this.ctx.fillRect(x1, y1, 50, 40);
      this.ctx.fillStyle = 'black';
      this.ctx.fillText(queue.id, x1+20, y1+20);
     }
     this.machineClicked=false;
     this.queueClicked=false;
    },
    addMachine(){
      this.machineClicked=true;
    },
    addQueue(){
      this.queueClicked=true;
    },
    SendMachine(id){
      fetch("http://localhost:8085/addMachine/"+id,{method:'get'})
    },
    SendQueue(id){
      fetch("http://localhost:8085/addQueue/"+id,{method:'get'})
    },
    connectQtoM(){
      if(!this.From==''&&!this.To==''){
      fetch("http://localhost:8085/connectQueueToMachine/"+this.From+"/"+this.To,{method:'get'})
      .then(response=> {
        return response.text();
      })
      .then(data => {this.message=data
        if(this.message==="Success"){
        for(var i =0;i<this.queues.length;i++){
          if(this.queues[i].id===this.From){
               this.x1=this.queues[i].x;
               this.y1=this.queues[i].y;
               break;
          }
        }
        for(var j =0;j<this.machines.length;j++){
          if(this.machines[j].id===this.To){
               this.x2=this.machines[j].x;
               this.y2=this.machines[j].y;
               break;
          }
        }
        var connection = {
          startx: this.x1,
          starty: this.y1,
          endx: this.x2,
          endy: this.y2,
          from: this.From,
          to: this.To,
        }
        this.connections.push(connection);
        this.ctx.fillStyle = "black";
        this.ctx.beginPath();
            this.ctx.moveTo(this.x1,this.y1 );
            this.ctx.lineTo(this.x2, this.y2);
            this.ctx.closePath();
            this.ctx.stroke();
            this.From='';
            this.To='';
            this.message='';
      }});
      console.log(this.message);
    }

    },
    connectMtoQ(){
      if(!this.From==''&&!this.To==''){
      fetch("http://localhost:8085/connectMachineToQueue/"+this.To+"/"+this.From,{method:'get'})
      .then(response=> {
        return response.text();
      })
      .then(data => {this.message=data
        if(this.message==="Success"){
        for(var i =0;i<this.queues.length;i++){
          if(this.queues[i].id===this.To){
               this.x2=this.queues[i].x;
               this.y2=this.queues[i].y;
               break;
          }
        }
        for(var j =0;j<this.machines.length;j++){
          if(this.machines[j].id===this.From){
               this.x1=this.machines[j].x;
               this.y1=this.machines[j].y;
               break;
          }
        }
        var connection = {
          startx: this.x1,
          starty: this.y1,
          endx: this.x2,
          endy: this.y2,
          from: this.From,
          to: this.To,
        }
        this.connections.push(connection);
        this.ctx.fillStyle = "black";
        this.ctx.beginPath();
            this.ctx.moveTo(this.x1,this.y1 );
            this.ctx.lineTo(this.x2, this.y2);
            this.ctx.closePath();
            this.ctx.stroke();
            this.From='';
            this.To='';
            this.message='';
      }});
      console.log(this.message);
    }

    },
    Clear(){
      fetch("http://localhost:8085/Clear",{method:'get'})
      this.ctx.clearRect(0, 0, this.width, this.height);
      this.machines=[]
      this.queues=[]
      this.machineClicked=false
      this.queueClicked=false
      this.From=''
      this.To=''
      this.message=''
      this.x1=''
      this.x2=''
      this.y1=''
      this.y2=''
      this.connections=[]
    },
    async Simulate(){
      if(this.productsNum!=0){
      fetch("http://localhost:8085/simulate/"+this.productsNum,{method:'get'})
      this.ctx.clearRect(0, 0, this.width, this.height); 
        
      do{
      const response= await fetch("http://localhost:8085/getQueues",{method:'get'})
            let data = await response.json();
            console.log(data);
            this.UpdatedQueues=data;  
          console.log(this.UpdatedQueues);
          //setTimeout(500);
      const response1= await fetch("http://localhost:8085/getMachines",{method:'get'})
            let data1 = await response1.json();
            this.UpdatedMachines=data1; 
            console.log(this.UpdatedMachines); 
            console.log(this.UpdatedQueues[(this.queues.length)-1].products.length);
            console.log(this.productsNum);
            console.log(!(this.UpdatedQueues[(this.queues.length)-1].products.length==this.productsNum));
            for(var i=0;i<this.connections.length;i++){
            this.ctx.fillStyle = "black";
             this.ctx.beginPath();
            this.ctx.moveTo(this.connections[i].startx,this.connections[i].starty );
            this.ctx.lineTo(this.connections[i].endx, this.connections[i].endy);
            this.ctx.closePath();
            this.ctx.stroke();
          }
          for(var j=0;j<this.UpdatedQueues.length;j++){
            this.ctx.fillStyle = "rgb(50, 225, 255)";
            this.ctx.fillRect(this.queues[j].x, this.queues[j].y, 50, 40);
           this.ctx.fillStyle = 'black';
          this.ctx.fillText(this.queues[j].id, this.queues[j].x+20, this.queues[j].y+20);
          this.ctx.fillText(this.UpdatedQueues[j].products.length, this.queues[j].x+20, this.queues[j].y+30);
          }
          for(var k=0;k<this.machines.length;k++){
            this.ctx.beginPath();
            this.ctx.fillStyle = this.UpdatedMachines[k].color;
            this.ctx.arc(this.machines[k].x,this.machines[k].y,30, 0, 2 * Math.PI);
           this.ctx.fill();
           this.ctx.fillStyle = 'black';
           this.ctx.fillText(this.machines[k].id, this.machines[k].x, this.machines[k].y);
           this.ctx.closePath();
          }
          setTimeout(2000);
      }while(!(this.UpdatedQueues[(this.queues.length)-1].products.length==this.productsNum))
      this.UpdatedMachines=[];
      this.UpdatedQueues=[];
      this.productsNum1=this.productsNum;
      this.productsNum=0;
    }
      
    },
    async Replay(){
      if(this.productsNum==0){
      fetch("http://localhost:8085/replay",{method:'get'})
      this.ctx.clearRect(0, 0, this.width, this.height); 
        
      do{
      const response= await fetch("http://localhost:8085/getQueues",{method:'get'})
            let data = await response.json();
            console.log(data);
            this.UpdatedQueues=data;  
          console.log(this.UpdatedQueues);
          //setTimeout(500);
      const response1= await fetch("http://localhost:8085/getMachines",{method:'get'})
            let data1 = await response1.json();
            this.UpdatedMachines=data1; 
            console.log(this.UpdatedMachines); 
            console.log(this.UpdatedQueues[(this.queues.length)-1].products.length);
            console.log(this.productsNum);
            console.log(!(this.UpdatedQueues[(this.queues.length)-1].products.length==this.productsNum));
            for(var i=0;i<this.connections.length;i++){
            this.ctx.fillStyle = "black";
             this.ctx.beginPath();
            this.ctx.moveTo(this.connections[i].startx,this.connections[i].starty );
            this.ctx.lineTo(this.connections[i].endx, this.connections[i].endy);
            this.ctx.closePath();
            this.ctx.stroke();
          }
          for(var j=0;j<this.UpdatedQueues.length;j++){
            this.ctx.fillStyle = "rgb(50, 225, 255)";
            this.ctx.fillRect(this.queues[j].x, this.queues[j].y, 50, 40);
           this.ctx.fillStyle = 'black';
          this.ctx.fillText(this.queues[j].id, this.queues[j].x+20, this.queues[j].y+20);
          this.ctx.fillText(this.UpdatedQueues[j].products.length, this.queues[j].x+20, this.queues[j].y+30);
          }
          for(var k=0;k<this.machines.length;k++){
            this.ctx.beginPath();
            this.ctx.fillStyle = this.UpdatedMachines[k].color;
            this.ctx.arc(this.machines[k].x,this.machines[k].y,30, 0, 2 * Math.PI);
           this.ctx.fill();
           this.ctx.fillStyle = 'black';
           this.ctx.fillText(this.machines[k].id, this.machines[k].x, this.machines[k].y);
           this.ctx.closePath();
          }
          setTimeout(2000);
      }while(!(this.UpdatedQueues[(this.queues.length)-1].products.length==this.productsNum1))
      this.UpdatedMachines=[];
      this.UpdatedQueues=[];
    }
      
    },

    async getQueues() {
        const response= await fetch("http://localhost:8085/getQueues",{method:'get'})
            let data = await response.json();
            console.log(data);
            this.UpdatedQueues=data;  
            console.log(this.UpdatedQueues); 
            
    },
    async getMachines() {
        const response= await fetch("http://localhost:8085/getMachines",{method:'get'})
            let data = await response.json();
            this.UpdatedMachines=data; 
            console.log(this.UpdatedMachines);   
            
    },

  },
  mounted() {
        var canvas = document.getElementById("canvas");
        this.can = canvas;
        var c = canvas.getContext("2d");
        this.ctx = c;
        canvas.width = 1180;
        canvas.height = 650;
        this.width = canvas.width;
        this.height = canvas.height;
        this.ctx.fillStyle = "#fff";
        this.ctx.fillRect(0, 0, this.width, this.height);
    },
}
</script>

<style>
.body{
  display: flex;
  flex-direction: row;
  align-items: center;
  overflow: hidden;
  background-color: gray;
  height: 100vh;
}

::ng-deep * {

  margin: 0;
  padding: 0;
}

#toolbar{
  display: flex;
  flex-direction: column;
  padding: 20px;
  background-color: palevioletred;
  width: fit-content;
  border-radius: 30px;
  height: 80vh;
  justify-content: space-between;
  border: 2px solid white;
}

button{
  font-size:1.15em;
  background: none;
  border: none;
  cursor: pointer;
  color: white;
}
#select {
  width: 50px;
}
</style>