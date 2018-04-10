const client = require("./clients");


var net = require('net');
var HOST = '127.0.0.1'; // parameterize the IP of the Listen var PORT = 6969; // TCP LISTEN port
var PORT = 6969; // TCP LISTEN port

var clients = []

net.createServer ((sock) =>{

    console.log('CONNECTED: ' + sock.remoteAddress +':'+ sock.remotePort);

    var newclient = Client(sock.remoteAddress, sock.remotePort)
    clients.push(newclient)

    sock.on('data', (data)=> {
        console.log(sock.remoteAddress + ":" + sock.remotePort )
        console.log(data.toString())

        sock.write('Echo server\r\n');
        

    })
    
}).listen(PORT, HOST);


console.log('Server listening on ' + HOST +':'+ PORT);
