package org.maximum0.pattern;

import org.maximum0.pattern.model.User;
import org.maximum0.pattern.service.InternalUserService;
import org.maximum0.pattern.service.UserService;
import org.maximum0.pattern.service.UserServiceInFunctionalWay;

/**
 * 템플릿 메서드 패턴 (Template Method Pattern) 예제
 * - OOP 기반 방식 (AbstractUserService)
 * - 함수형 인터페이스 주입 방식 (UserServiceInFunctionalWay)
 */
public class TemplateMethodPattern {

    public static void main(String[] args) {
        User maximum0 = User.builder(1, "Maximum0")
                .with(builder -> {
                    builder.isVerified = false;
                })
                .build();

        // OOP Template 기본 구현 클래스 사용
        System.out.println("\n--- OOP Template Method Pattern ---");

        UserService userService = new UserService();
        userService.createUser(maximum0);

        InternalUserService internalUserService = new InternalUserService();
        internalUserService.createUser(maximum0);

        // Functional Template 람다식을 이용해 훅 메서드(행위)를 즉시 주입
        // - 별도의 클래스 파일 생성 없이 동작 정의
        System.out.println("\n--- Functional Template Method Pattern ---");

        UserServiceInFunctionalWay userServiceInFunctionalWay = new UserServiceInFunctionalWay(
                user -> {
                    System.out.println("Validating user " + user.getName());
                    return user.getName() != null && user.getEmailAddress() != null;
                },
                user -> {
                    System.out.println("Writing user " + user.getName() + " to DB");
                }
        );
        userServiceInFunctionalWay.createUser(maximum0);
    }

}
