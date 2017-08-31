package com.epam.bets.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class SupportMail extends Entity {

    private int mailId;
    private String userEmail;
    private String mailText;
    private String mailSubject;
    private LocalDateTime mailDate;
    private MailType type;

    public enum MailType {
        IN, OUT
    }

    public int getMailId() {
        return mailId;
    }

    public void setMailId(int mailId) {
        this.mailId = mailId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public LocalDateTime getMailDate() {
        return mailDate;
    }

    public void setMailDate(LocalDateTime mailDate) {
        this.mailDate = mailDate;
    }

    public MailType getType() {
        return type;
    }

    public void setType(MailType type) {
        this.type = type;
    }
}
