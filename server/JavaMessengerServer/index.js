const Client = require("./clients");
const colors = require('colors/safe'); 

var net = require('net');
var HOST = '127.0.0.1'; // parameterize the IP of the Listen var PORT = 6969; // TCP LISTEN port
var PORT = 6969; // TCP LISTEN port

var clients = []

net.createServer ((sock) =>{

    console.log('CONNECTED: ' + sock.remoteAddress +':'+ sock.remotePort);

    var newclient = new Client(sock.remoteAddress, sock.remotePort)
    clients.push(newclient)

    sock.on('data', (data)=> {
        let message = JSON.parse(data.toString('utf8'))
        printMessage(message ,sock)
        sock.write('Echo server\r\n');
    })

    sock.on('close', function() {
        console.log('Connection closed');
    });

    sock.on("error", (err) => {
        console.log("Caught flash policy server socket error: ")
        console.log(err.stack)
    }
    
  )
    
}).listen(PORT, HOST);


/*Functions Utils*/
function printMessage(data, sock){
    console.log(colors.green(data.name)+
    " at: "+colors.red(sock.remoteAddress + ":" + sock.remotePort)+
    " send => "+colors.yellow(data.message))
}


console.log('Server listening on ' + HOST +':'+ PORT);
