/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dazito.komoot.controllers;

import com.dazito.komoot.model.sns.json.SnsNotification;
import com.dazito.komoot.service.SnsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daz
 */
@RestController
@RequestMapping(value = "sns")
public class SnsController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SnsController.class);
    
    @Autowired
    private SnsService snsService;
    
    @RequestMapping(value = "subscribe", method = RequestMethod.POST, consumes = "text/plain")
    public ResponseEntity subscribeSns(@RequestBody String payload) {
        if(payload == null || payload.isEmpty()) {
            LOGGER.warn("payload:{}|invalid payload - null or empty", payload);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        
        final ObjectMapper mapper = new ObjectMapper();
        try {
            SnsNotification snsNotification = mapper.readValue(payload, SnsNotification.class);

            snsService.onNewNotification(snsNotification);
            
            LOGGER.info("SNS Event received and processed");
        } 
        catch (IOException ex) {
            LOGGER.error("subscribeSns|could not process incoming notification - reason: {}", ex.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
