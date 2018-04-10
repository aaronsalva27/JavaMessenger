class Client {
    constructor (ip, port) {
        this.ip = ip ;
        this.port = port ;
    }

    toString() {
        return "IP: "+ ip + " - " + "PORT: " + this.port
    }

}

  // export the class
  module.exports = Client;