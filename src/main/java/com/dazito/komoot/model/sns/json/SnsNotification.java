/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dazito.komoot.model.sns.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author daz
 */
public class SnsNotification {
    @JsonProperty("SignatureVersion")
    private String signatureVersion;
    @JsonProperty("Timestamp")
    private String timestamp;
    @JsonProperty("Signature")
    private String signature;
    @JsonProperty("SigningCertURL")
    private String signingCertUrl;
    @JsonProperty("MessageId")
    private String messageId;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("MessageAttributes")
    private MessageAttributes messageAttributes;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("UnsubscribeURL")
    private String unsubscribeUrl;
    @JsonProperty("TopicArn")
    private String topicArn;
    @JsonProperty("Subject")
    private String subject;

    public SnsNotification(
            String signatureVersion, 
            String timestamp, 
            String signature, 
            String signingCertUrl, 
            String messageId, 
            String message, 
            MessageAttributes messageAttributes, 
            String type, 
            String unsubscribeUrl, 
            String topicArn, 
            String subject
    ) {

        this.signatureVersion = signatureVersion;
        this.timestamp = timestamp;
        this.signature = signature;
        this.signingCertUrl = signingCertUrl;
        this.messageId = messageId;
        this.message = message;
        this.messageAttributes = messageAttributes;
        this.type = type;
        this.unsubscribeUrl = unsubscribeUrl;
        this.topicArn = topicArn;
        this.subject = subject;
    }

    public SnsNotification() {
    }

    public void setSignatureVersion(String signatureVersion) {
        this.signatureVersion = signatureVersion;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setSigningCertUrl(String signingCertUrl) {
        this.signingCertUrl = signingCertUrl;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageAttributes(MessageAttributes messageAttributes) {
        this.messageAttributes = messageAttributes;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUnsubscribeUrl(String unsubscribeUrl) {
        this.unsubscribeUrl = unsubscribeUrl;
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSignatureVersion() {
        return signatureVersion;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public String getSigningCertUrl() {
        return signingCertUrl;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public MessageAttributes getMessageAttributes() {
        return messageAttributes;
    }

    public String getType() {
        return type;
    }

    public String getUnsubscribeUrl() {
        return unsubscribeUrl;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public String getSubject() {
        return subject;
    }
    
    public static class MessageAttributes {

        public MessageAttributes() {
        }
        
    }
}
