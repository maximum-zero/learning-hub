package org.maximum0.pattern.service;

import org.maximum0.pattern.model.User;

/**
 * Strategy Pattern의 Context
 * EmailProvider 전략(Strategy)을 주입받아 사용합니다.
 */
public class EmailSender {
    private EmailProvider emailProvider;

    public EmailSender setEmailProvider(EmailProvider emailProvider) {
        this.emailProvider = emailProvider;
        return this;
    }

    public void sendEmail(User user) {
        String email = emailProvider.getEmail(user);
        System.out.println("Sending " + email);
    }
}
