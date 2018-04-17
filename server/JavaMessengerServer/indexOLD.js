const Client = require("./clients");
const colors = require('colors/safe'); 

var net = require('net');
var HOST = '127.0.0.1'; // parameterize the IP of the Listen var PORT = 6969; // TCP LISTEN port
var PORT = 6969; // TCP LISTEN port

var clients = []
var sockets = [];


net.createServer ((sock) =>{

       sock.on('data', (data)=> {
        let message = JSON.parse(data.toString('utf8'))
        let typeMsg = message.type;
        //console.log("msg", message)
        switch (typeMsg){
            case "ACTION":
            if(message.data && message.data.length){
                manageClient(message, sock)
            }
            //manageMessage(message,printMessage(message ,sock));
            sock.write('Echo server\r\n');

            break;
            case "MESSAGE":
            manageMessage(message,printMessage(message ,sock));
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
//console.log(data.toString('utf8'))
   //let msg = data.toString('utf8')
  // console.log(data)

    if(data.room){
        switch(data.room){
            case "room 1":

                clients.forEach(cli => {
                    if( cli.room == data.room){
                        console.log(cli.socket)
                        cli.socket.write(JSON.stringify({ room: data.room,
                        from: data.from,
                        data : data.data,
                        owner: data.owner }))
                    }    
                });
                
                
            break;
        }
    }

    if(callback) {
        callback();
    }
}

/*Functions Utils*/
function printMessage(data, sock){
    console.log(colors.green(data.owner)+
    " at: "+colors.red(sock.remoteAddress + ":" + sock.remotePort)+
    " type: "+colors.bgGreen(data.type)+
    " room: "+colors.rainbow(data.room)+
    " data => "+colors.yellow(data.data))
}

/* Function Utils */
function manageClient(data, sock){
    switch(data.data){

        case "connect":
        var newclient = new Client(sock.remoteAddress, sock.remotePort, sock, null)
        newclient.name = data.owner;
        clients.push(newclient)
        console.log(colors.blue("[INFO]")+"client: "+data.owner+" connected", sock)
        break;

        case "joined room 1":
        clients.forEach(cli => {
            //console.log("PORDIOS ",  cli.name == data.owner)
            if( cli.name == data.owner){
                cli.room = data.room;
                console.log(colors.blue("[INFO]")+"client: "+data.owner+" joined: "+data.room)
            }    
        });

        
       
        //console.log(clients)

        break;
    }
}



console.log('Server listening on ' + HOST +':'+ PORT);
