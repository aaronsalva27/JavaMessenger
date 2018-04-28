class Client {
    constructor (ip, port, name, socket, chat) {
        this.ip = ip ;
        this.port = port ;
        this.name = name;
        this.socket = socket;
        this.room = chat;
    }

    toString() {
        return "Name:" + this.name + " - " + 
            "IP: "+ this.ip + " - " + 
            "PORT: " + this.port + " - " + 
            "SOCKET: " + this.socket + " - " + 
            "ROOM: " + this.room 

    }

}

  // export the class
  module.exports = Client;