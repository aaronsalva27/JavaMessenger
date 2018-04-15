class Client {
    constructor (ip, port, name) {
        this.ip = ip ;
        this.port = port ;
        this.name = name;
    }

    toString() {
        return "Name:" + this.name + " - " + 
                " IP: "+ this.ip + " - " + 
                "PORT: " + this.port
    }

}

  // export the class
  module.exports = Client;