package org.maximum0.pattern.service;

import java.util.function.Consumer;
import java.util.function.Predicate;
import org.maximum0.pattern.model.User;

/**
 * 함수형 템플릿 메소드 패턴 (Functional Template Method Pattern)
 * 추상 클래스 상속 대신 함수형 인터페이스(Predicate, Consumer)를 인수로 주입받아 템플릿 메서드의 가변적인 부분(훅)을 처리합니다.
 */
public class UserServiceInFunctionalWay {
    private final Predicate<User> validateUser;
    private final Consumer<User> writeToDB;

    public UserServiceInFunctionalWay(Predicate<User> validateUser, Consumer<User> writeToDB) {
        this.validateUser = validateUser;
        this.writeToDB = writeToDB;
    }

    /**
     * 템플릿 메서드: 사용자 생성 로직의 전체 흐름을 정의하며 순서를 고정합니다.
     */
    public void createUser(User user) {
        if (validateUser.test(user)) {
            writeToDB.accept(user);
        } else {
            System.out.println("Cannot Create User");
        }
    }

}
