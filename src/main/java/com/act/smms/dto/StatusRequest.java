package com.act.smms.dto;

import com.act.smms.enums.SMSStatus;

public class StatusRequest {

        private SMSStatus status;
        private String message;
        private String sender;
        private String recipient;
        public StatusRequest(SMSStatus status, String message, String sender, String recipient) {
            this.status = status;
            this.message = message;
            this.sender = sender;
            this.recipient = recipient;
        }
        public SMSStatus getStatus() {
            return status;
        }
        public void setStatus(SMSStatus status) {
            this.status = status;
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
        public String getRecipient() {
            return recipient;
        }
        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }
        
        
}
