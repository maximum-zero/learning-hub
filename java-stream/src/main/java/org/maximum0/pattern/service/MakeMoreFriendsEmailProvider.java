package org.maximum0.pattern.service;

import org.maximum0.pattern.model.User;

public class MakeMoreFriendsEmailProvider implements EmailProvider {

    @Override
    public String getEmail(User user) {
        return "'Make More Friends' email for " + user.getName();
    }
}
