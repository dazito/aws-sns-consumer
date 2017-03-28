/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dazito.komoot.batch;

import com.dazito.komoot.model.db.NotificationEntity;
import com.dazito.komoot.repository.NotificationRepository;
import com.google.common.collect.Lists;
import it.ozimov.springboot.templating.mail.model.Email;
import it.ozimov.springboot.templating.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.templating.mail.service.EmailService;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author daz
 */
@Component
public class NotificationBatchBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationBatchBean.class);
    
    private static final String YOUR_FRIENDS_ARE_ACTIVE = ", your friends are active!\n\n";
    private static final String HI = "Hi ";
    private static final String EMAIL_SUBJECT = "You have new notifications";
    private static final String FROM_EMAIL_ADDRESS = "my@email.com";
    
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    public EmailService emailService;

    
    @Scheduled(cron = "0 0 */1 * * *")
    public void cronJob() {
        LOGGER.info("The cronJob is running");
        
        final List<NotificationEntity> notificationList = notificationRepository.findByTimestampHigherThan();        
        final Map<String, List<NotificationEntity>> groupedNotifications = groupByEmailAddress(notificationList);
        
        groupedNotifications.keySet().forEach((String userEmail) -> {
            final String emailBody = buildEmailBody(groupedNotifications.get(userEmail));
            
            sendEmail(userEmail, emailBody);
        });
        
        for(NotificationEntity notificationEntity : notificationList) {
            notificationEntity.setProcessed(true);
            
            notificationRepository.save(notificationEntity);
        }
        
        LOGGER.info("The cronJob finished");
    }
    
    private String parseTimestampToDateText(long timestamp) {
        ZonedDateTime dateTime = Instant
                .ofEpochMilli(timestamp)
                .atZone(ZoneId.of("UTC"));
        
        final String day = dateTime
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.US);
        
        final String hour = getFormattedHour(dateTime);
        final String minute = getFormattedMinute(dateTime);
        
        return new StringBuilder()
                .append(day)
                .append(", ")
                .append(hour)
                .append(":")
                .append(minute)
                .append("\t")
                .toString();
    }

    private String getFormattedHour(ZonedDateTime dateTime) {
        if(dateTime.getHour() < 10) {
            return "0" + dateTime.getHour();
        }
        
        return String.valueOf(dateTime.getHour());
    }
    
    private String getFormattedMinute(ZonedDateTime dateTime) {
        if(dateTime.getMinute() < 10) {
            return "0" + dateTime.getMinute();
        }
        
        return String.valueOf(dateTime.getMinute());
    }
    
    private Map<String, List<NotificationEntity>> groupByEmailAddress(List<NotificationEntity> notificationEntityList) {
        if(notificationEntityList == null) {
            return new HashMap<>();
        }
        
        return notificationEntityList
                .stream()
                .collect(Collectors.groupingBy(NotificationEntity::getEmail));
    }
    
    private String buildEmailBody(List<NotificationEntity> notificationList) {
        if(notificationList.isEmpty()) {
            return "";
        }
        
        final StringBuilder emailBody = new StringBuilder();
        emailBody.append(HI)
                    .append(notificationList.get(0).getName())
                    .append(YOUR_FRIENDS_ARE_ACTIVE);
        
        for(NotificationEntity notificationEntity : notificationList) {
            final String date = parseTimestampToDateText(notificationEntity.getTimestamp());
            emailBody
                    .append(date)
                    .append(" ")
                    .append(notificationEntity.getMessage())
                    .append("\n");
        }
        
        return emailBody.toString();
    }
    
    
    private void sendEmail(String to, String body) {
        if(to == null || to.isEmpty()) {
            LOGGER.error("to:{}|email address on To field is null or empty", to);
            return;
        }
        
        if(body == null || to.isEmpty()) {
            LOGGER.error("body:{}|email body is null or empty", body);
            return;
        }
        
        try {
            final Email email = DefaultEmail.builder()
                    .from(new InternetAddress(FROM_EMAIL_ADDRESS))
                    .to(Lists.newArrayList(new InternetAddress(to)))
                    .subject(EMAIL_SUBJECT)
                    .body(body)
                    .encoding("UTF-8")
                    .build();
            
            emailService.send(email);
            
            LOGGER.info("email sent - to:{} | body: {}", to, body);
        } 
        catch (AddressException ex) {
            LOGGER.error("to:{}|error while sending the email - reason: {}", to, ex.getMessage());
        }
    }
    
}
