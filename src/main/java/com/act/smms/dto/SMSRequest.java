package com.act.smms.dto;

import java.util.List;

public class SMSRequest {
    private String message;
    private String sender;
    private List<String> recipient;

    

    public SMSRequest(String message, String sender, List<String> recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public List<String> getRecipient() {
        return recipient;
    }
    public void setRecipient(List<String> recipient) {
        this.recipient = recipient;
    }
    
}
