/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalDateTime;
import java.util.Date;
import org.json.simple.JSONObject;

/**
 *
 * @author aaron
 */



public class Message {
    public enum Type {
        MESSAGE, ERROR,ACTION
    }
        
    private String owner;
    private String host;
    private String data;
    private Type type;
    private String receiver;
    private LocalDateTime created;
    private String room;

    public Message(String owner, String host, String data, Type type,String receiver ,LocalDateTime created, String room) {
        this.owner = owner;
        this.host = host;
        this.data = data;
        this.type = type;
        this.receiver = receiver;
        this.created = created;
        this.room = room;
    }
    
    public JSONObject generateMessage() {
        JSONObject obj = new JSONObject();
        obj.put("owner",owner);
        obj.put("host", host);
        obj.put("type", type.toString());
        obj.put("to", receiver);
        obj.put("data", data);
        obj.put("created", LocalDateTime.now().toString());
        obj.put("room", room);
        
        return obj;
    }
    
}
