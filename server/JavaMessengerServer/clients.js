class Client {
    constructor (ip, port, name, socket, chat) {
        this.ip = ip ;
        this.port = port ;
        this.name = name;
        this.socket = socket;
        this.currentChat = chat;
    }

    toString() {
        return "Name:" + this.name + " - " + 
            "IP: "+ this.ip + " - " + 
            "PORT: " + this.port + " - " + 
            "SOCKET ID: " + this.socket.id + " - " + 
            "CHAT: " + this.chat 

    }

}

  // export the class
  module.exports = Client;