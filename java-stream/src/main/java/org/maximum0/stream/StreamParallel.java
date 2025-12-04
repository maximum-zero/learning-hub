package org.maximum0.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.maximum0.stream.model.User;
import org.maximum0.stream.service.EmailService;

/**
 * 최종 연산: 병렬 스트림 (Parallel Stream) 학습 예제.
 */
public class StreamParallel {

    public static void main(String[] args) {
        List<User> users = prepareUsers();
        EmailService emailService = new EmailService();

        // 1. 순차 스트림 (Sequential Stream) 처리 시간 측정
        System.out.println("--- 1. Sequential Stream (순차 처리) ---");
        long startTime = System.currentTimeMillis();
        users.stream()
                .filter(user -> !user.isVerified())
                .forEach(emailService::sendVerifyYourEmailEmail);
        long endTime = System.currentTimeMillis();
        System.out.println("처리 시간: " + (endTime - startTime) + "ms");

        // 2. 병렬 스트림 (Parallel Stream) 처리 시간 측정
        System.out.println("\n--- 2. Parallel Stream (병렬 처리) ---");
        startTime = System.currentTimeMillis();
        users.stream().parallel()
                .filter(user -> !user.isVerified())
                .forEach(emailService::sendVerifyYourEmailEmail);
        endTime = System.currentTimeMillis();
        System.out.println("처리 시간: " + (endTime - startTime) + "ms");

        // 3. 병렬 스트림과 상태 변경
        System.out.println("\n--- 3. Parallel Stream (상태 변경 주의) ---");
        List<User> processedUsers = users.parallelStream()
                .map(user -> {
                    System.out.println("Capitalize user name for user " + user.getId());
                    // NOTE: 병렬 스트림에서 외부 객체(user)의 상태를 변경하는 것은 동시성 문제 위험
                    user.setName(user.getName().toUpperCase());
                    return user;
                })
                .map(user -> {
                    System.out.println("Set 'isVerified' to true for user " + user.getId());
                    user.setVerified(true);
                    return user;
                })
                .collect(Collectors.toList());
        System.out.println(processedUsers);
    }

    private static List<User> prepareUsers() {
        User user1 = new User()
                .setId(101)
                .setName("Maximum0")
                .setEmailAddress("maximum.zero95@gmail.com")
                .setFriendUserIds(Arrays.asList(201, 202, 203, 204, 211, 212, 213, 214));

        User user2 = new User()
                .setId(102)
                .setName("Alice")
                .setVerified(true)
                .setEmailAddress("alice@gmail.com");
        User user3 = new User()
                .setId(103)
                .setName("Bob")
                .setVerified(false)
                .setEmailAddress("bob@gmail.com");
        User user4 = new User()
                .setId(104)
                .setName("Charlie")
                .setVerified(false)
                .setEmailAddress("charlie@gmail.com");
        User user5 = new User()
                .setId(105)
                .setName("David")
                .setEmailAddress("david@gmail.com")
                .setVerified(true);
        User user6 = new User()
                .setId(106)
                .setName("Eve")
                .setEmailAddress("eve@gmail.com")
                .setVerified(false);
        User user7 = new User()
                .setId(106)
                .setName("Frank")
                .setEmailAddress("frank@gmail.com")
                .setVerified(false);


        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7);
    }

}
