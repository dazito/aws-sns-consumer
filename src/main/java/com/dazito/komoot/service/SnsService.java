/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dazito.komoot.service;

import com.dazito.komoot.model.Notification;
import com.dazito.komoot.model.db.NotificationEntity;
import com.dazito.komoot.model.sns.json.SnsNotification;
import com.dazito.komoot.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author daz
 */
@Service
public class SnsService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SnsService.class);
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    public void onNewNotification(SnsNotification snsNotification) {
        final ObjectMapper mapper = new ObjectMapper();
        final String message = snsNotification.getMessage();

        try {
            final Notification notification = mapper.readValue(message, Notification.class);
            persistNotification(notification);
        } 
        catch (IOException ex) {
            LOGGER.error("Error while proessing the record received from SNS - reason: {}",
                    ex.getMessage());
        }
    }
    
    private void persistNotification(Notification notification) {
        if(notification == null) {
            LOGGER.error("received null notification");
            return;
        }
        
        final NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setProcessed(false);
        notificationEntity.setEmail(notification.getEmail());
        notificationEntity.setMessage(notification.getMessage());
        notificationEntity.setName(notification.getName());
        
        final long timestamp = getTimestampInMilisFromNotification(notification);
        notificationEntity.setTimestamp(timestamp);
        
        notificationRepository.save(notificationEntity);
    }

    private long getTimestampInMilisFromNotification(Notification notification) {
        // Assuming the timestamp is in UTC
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime date = LocalDateTime.parse(notification.getTimestamp(), formatter);
        ZoneId zoneId = ZoneId.of(ZoneOffset.UTC.getId());
        ZonedDateTime zdt = ZonedDateTime.of(date, zoneId);
        
        return zdt.toInstant().toEpochMilli();
    }
    
}
