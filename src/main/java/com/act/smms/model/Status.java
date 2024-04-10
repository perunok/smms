package com.act.smms.model;

import java.time.LocalDateTime;

import com.act.smms.enums.SMSStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sms_status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private SMSStatus status;

    @Column(name = "message")
    private String message;

    @Column(name = "sender")
    private String sender;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "remark")
    private String remark;

    public Status(SMSStatus smsStatus, String message, String sender, String recipient, LocalDateTime date,
            String remark) {
        this.status = smsStatus;
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
        this.date = date;
        this.remark = remark;
    }

    public Status() {
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
