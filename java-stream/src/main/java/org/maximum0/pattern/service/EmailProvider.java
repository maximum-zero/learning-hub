package org.maximum0.pattern.service;

import org.maximum0.pattern.model.User;

/**
 * Strategy Pattern의 전략(Strategy) 인터페이스
 */
public interface EmailProvider {
    String getEmail(User user);
}
