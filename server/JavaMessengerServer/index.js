const Client = require("./clients");
const colors = require('colors/safe'); 

var net = require('net');
var HOST = '127.0.0.1'; // parameterize the IP of the Listen var PORT = 6969; // TCP LISTEN port
var PORT = 6969; // TCP LISTEN port

var clients = []
var sockets = [];


net.createServer ((sock) =>{

    console.log('CONNECTED: ' + sock.remoteAddress +':'+ sock.remotePort);

    var newclient = new Client(sock.remoteAddress, sock.remotePort, sock, null)
    clients[sock.id] = newclient;
    
    sock.on('data', (data)=> {
        let message = JSON.parse(data.toString('utf8'))
        let typeMsg = message.to;
        switch (typeMsg){
            case "SERVER":
            if(message.data && message.data.length){
                manageClient(message, sock)
            }
            sock.write('Echo server\r\n');
            manageMessage(data,printMessage(message ,sock));

            break;
            case "chat1":
            manageMessage(data,printMessage(message ,sock));
            sock.write('Echo server\r\n');
            break;

        }
    })

    sock.on('close', function(data) {
        console.log(data)
        console.log('Connection closed');
    });

    sock.on("error", (err) => {
        console.log("Caught flash policy server socket error: ")
        console.log(err.stack)
    });
    
}).listen(PORT, HOST);

/////// Message Model ///////
/*
type [MESSAGE, ERROR , ACTION]
owner
data
reciever?
message
ip
port
room?

*/


function manageMessage(data ,callback) {

   // console.log(data.toString('utf8'))

    if(callback) {
        callback();
    }
}

/*Functions Utils*/
function printMessage(data, sock){
    console.log(colors.green(data.owner)+
    " at: "+colors.red(sock.remoteAddress + ":" + sock.remotePort)+
    " to: "+colors.rainbow(data.to)+
    " data => "+colors.yellow(data.data))
}

/* Function Utils */
function manageClient(data, sock){
    switch(data.data){
        case "chat1":
        var client = clients[sock.id];
        client.currentChat = data.data; // set the chat1 current room
        console.log(colors.blue("[INFO]")+"client: "+data.owner+" changed to room: "+client.currentChat)
        break;
    }
}



console.log('Server listening on ' + HOST +':'+ PORT);
