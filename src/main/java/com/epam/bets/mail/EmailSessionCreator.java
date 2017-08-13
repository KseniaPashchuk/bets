package com.epam.bets.mail;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class EmailSessionCreator {

    private final String USERNAME = "ina.pashchuk@gmail.com";
    private final String USER_PASSWORD = "tomoe127";
    private Properties sessionProperties;

    public EmailSessionCreator() {
        sessionProperties = new Properties();
        sessionProperties.setProperty("mail.transport.protocol", "smtp");
        sessionProperties.setProperty("mail.host", "smtp.gmail.com");
        sessionProperties.put("mail.smtp.auth", "true");
        sessionProperties.put("mail.smtp.port", "465");
        sessionProperties.put("mail.smtp.socketFactory.port", "465");
        sessionProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        sessionProperties.put("mail.smtp.socketFactory.fallback", "false");
    }

    public Session createSession() {
        return Session.getDefaultInstance(sessionProperties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, USER_PASSWORD);
                    }
                });
    }
}
