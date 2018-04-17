var net = require("net");
var util = require("util");

var currentPool = 1;
var server = net.createServer();

//add a new socket to the assigned pool stored on the listening server instance
server.addToPool = function(s) {
  if (!server.connPools) {
    server.connPools = {};
  }

  if (!server.connPools[s.poolId]) {
    server.connPools[s.poolId] = [];
  }

  server.connPools[s.poolId].push(s);

  s.on("close", clientCloseHandler);
  s.on("data", clientDataHandler);
};

//broadcast data from client to other clients in their pool
function clientDataHandler(data) {
  let message = JSON.parse(data.toString("utf8"));
  console.log(message);
  if (message.type == "ACTION") {

    for (var a = 0; a < server.connPools[this.poolId].length; a++) {
      server.connPools[this.poolId][a].write(data);
    }

  } else if (message.type == "MESSAGE") {
    for (var a = 0; a < server.connPools[this.poolId].length; a++) {
      server.connPools[this.poolId][a].write(data);

    }
  }
}

//remove client form pool on disconnect
function clientCloseHandler() {
  var tmp = null;
  for (var a = 0; a < server.connPools[this.poolId].length; a++) {
    if (this.remotePort === a.remotePort) {
      tmp = a;
      break;
    }
  }
  server.connPools[this.poolId].splice(tmp, 1);
}

//add new client to pool
function newClient(s) {
  s.poolId = currentPool;
  server.addToPool(s);
}

server.on("connection", newClient);

server.listen(6969);
