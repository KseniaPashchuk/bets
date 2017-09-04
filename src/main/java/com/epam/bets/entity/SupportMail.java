package com.epam.bets.entity;

import java.time.LocalDateTime;


public class SupportMail extends Entity {

    private int mailId;
    private String userEmail;
    private String mailText;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupportMail mail = (SupportMail) o;

        if (mailId != mail.mailId) return false;
        if (userEmail != null ? !userEmail.equals(mail.userEmail) : mail.userEmail != null) return false;
        if (mailText != null ? !mailText.equals(mail.mailText) : mail.mailText != null) return false;
        if (mailDate != null ? !mailDate.equals(mail.mailDate) : mail.mailDate != null) return false;
        return type == mail.type;
    }

    @Override
    public int hashCode() {
        int result = mailId;
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (mailText != null ? mailText.hashCode() : 0);
        result = 31 * result + (mailDate != null ? mailDate.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SupportMail{" +
                "mailId=" + mailId +
                ", userEmail='" + userEmail + '\'' +
                ", mailText='" + mailText + '\'' +
                ", mailDate=" + mailDate +
                ", type=" + type +
                '}';
    }
}
