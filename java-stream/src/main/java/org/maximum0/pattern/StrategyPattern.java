package org.maximum0.pattern;

import java.util.Arrays;
import java.util.List;
import org.maximum0.pattern.model.User;
import org.maximum0.pattern.service.EmailSender;
import org.maximum0.pattern.service.MakeMoreFriendsEmailProvider;
import org.maximum0.pattern.service.VerifyYourEmailAddressEmailProvider;

/**
 * 함수형 전략 패턴 (Functional Strategy Pattern) 예제
 *
 * EmailProvider라는 함수형 인터페이스를 통해 전략을 람다식으로 대체하여 클래스 생성을 줄입니다.
 */
public class StrategyPattern {

    public static void main(String[] args) {
        List<User> users = prepareUsers();
        EmailSender emailSender = new EmailSender();

        // 전통적인 OOP Strategy 방식: 클래스 인스턴스를 주입
        emailSender.setEmailProvider(new VerifyYourEmailAddressEmailProvider());
        users.stream()
                .filter(user -> !user.isVerified())
                .forEach(emailSender::sendEmail);

        emailSender.setEmailProvider(new MakeMoreFriendsEmailProvider());
        users.stream()
                .filter(User::isVerified)
                .filter(user -> user.getFriendUserIds().size() <= 5)
                .forEach(emailSender::sendEmail);

        // 함수형 Strategy 방식: 람다식(Lambda Expression)으로 전략을 인라인 구현
        // - 별도의 클래스 파일 생성 없이 동작(Behavior)만 즉시 전달
        emailSender.setEmailProvider(user -> "'Play With Friends' email for " + user.getName());
        users.stream()
                .filter(User::isVerified)
                .filter(user -> user.getFriendUserIds().size() > 5)
                .forEach(emailSender::sendEmail);
    }

    public static List<User> prepareUsers() {
        User user1 = User.builder(1, "Maximum0")
                .with(builder -> {
                    builder.emailAddress = "maximum.zero95@gmail.com";
                    builder.isVerified = false;
                    builder.friendUserIds = Arrays.asList(201, 202, 203, 204, 211, 212, 213, 214);
                })
                .build();

        User user2 = User.builder(2, "Alice")
                .with(builder -> {
                    builder.emailAddress = "alice@gmail.com";
                    builder.isVerified = true;
                    builder.friendUserIds = Arrays.asList(201, 202, 203);
                })
                .build();

        User user3 = User.builder(3, "Bob")
                .with(builder -> {
                    builder.emailAddress = "bob@gmail.com";
                    builder.isVerified = true;
                    builder.friendUserIds = Arrays.asList(201, 202, 203, 204, 211, 212);
                })
                .build();

        return Arrays.asList(user1, user2, user3);
    }

}
