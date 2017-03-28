/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dazito.komoot.model;

/**
 *
 * @author daz
 */
public class Notification {
    
    private String email;
    private String name;
    private String message;
    private String timestamp;

    public Notification(String email, String name, String message, String timestamp) {
        this.email = email;
        this.name = name;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Notification() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
