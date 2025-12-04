package org.maximum0.pattern.service;

import org.maximum0.pattern.model.User;

/**
 * OOP Template Method Pattern
 * 알고리즘의 뼈대(createUser)를 정의하고, 가변적인 부분(validateUser, writeToDB)을 추상 메서드로 서브클래스에 위임합니다.
 */
public abstract class AbstractUserService {
    protected abstract boolean validateUser(User user);
    protected abstract void writeToDB(User user);

    /**
     * 템플릿 메서드: 사용자 생성 로직의 전체 흐름(알고리즘의 뼈대)을 정의하며 순서를 고정합니다.
     */
    public void createUser(User user) {
        if (validateUser(user)) {
            writeToDB(user);
        } else {
            System.out.println("Cannot Create User");
        }
    }

}
