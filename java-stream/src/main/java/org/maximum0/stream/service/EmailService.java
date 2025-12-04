package org.maximum0.stream.service;


import org.maximum0.stream.model.User;

public class EmailService {
    public void sendPlayWithFriendsEmail(User user) {
        user.getEmailAddressOptional().ifPresent(email -> System.out.println("Sending 'Play With Friends' email to " + email));
    }

    public void sendMakeWithFriendsEmail(User user) {
        user.getEmailAddressOptional().ifPresent(email -> System.out.println("Sending 'Make More Friends' email to " + email));
    }

    public void sendVerifyYourEmailEmail(User user) {
        user.getEmailAddressOptional().ifPresent(email -> System.out.println("Sending 'Verify Your Email' email to " + email));
    }


}
