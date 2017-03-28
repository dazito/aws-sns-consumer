/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dazito.komoot.model.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author daz
 */
@Entity
@Table(name = "Notification")
public class NotificationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "timestamp", nullable = false)
    private long timestamp;

    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "message", nullable = false)
    private String message;
    
    @Size(max = 254)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 254)
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "processed", nullable = false)
    private boolean processed;

    public NotificationEntity(Long id, long timestamp, String message, String name, String email, boolean processed) {
        this.id = id;
        this.timestamp = timestamp;
        this.message = message;
        this.name = name;
        this.email = email;
        this.processed = processed;
    }

    public NotificationEntity() {
    }

    public Long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
    
    
    
}
