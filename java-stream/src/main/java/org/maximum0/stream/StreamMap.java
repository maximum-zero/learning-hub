package org.maximum0.stream;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.maximum0.stream.model.Order;
import org.maximum0.stream.model.Order.OrderStatus;
import org.maximum0.stream.model.User;

/**
 * 스트림 매핑 (Intermediate Operation: map) 예제.
 * 스트림 요소를 다른 값이나 타입으로 변환합니다.
 */
public class StreamMap {
    public static void main(String[] args) {
        // 1. 기본 매핑: 숫자를 2배로 변환
        System.out.println("--- 1. 기본 매핑 (숫자 2배) ---");

        List<Integer> numbers = Arrays.asList(3, 6, -4);
        List<Integer> doubledNumbers = numbers.stream()
                .map(x -> x * 2)
                .collect(Collectors.toList());
        System.out.println("결과: " + doubledNumbers);


        // 2. 타입 매핑: 숫자를 문자열로 변환
        System.out.println("\n--- 2. 타입 매핑 (숫자 -> 문자열) ---");

        List<String> numberStrings = numbers.stream()
                .map(x -> "숫자: " + x)
                .collect(Collectors.toList());
        System.out.println("결과: " + numberStrings);


        // 3. 객체 필드 매핑: User 객체에서 이메일 주소만 추출
        System.out.println("\n--- 3. 객체 필드 매핑 (User -> Email) ---");

        List<User> users = prepareUsers();
        List<String> userEmails = users.stream()
                .map(User::getEmailAddress)
                .collect(Collectors.toList());
        System.out.println("결과: " + userEmails);


        // 4. 복합 매핑 (Filter + Map): 미검증 User의 이메일만 추출
        System.out.println("\n--- 4. 복합 매핑 (미검증 User Email) ---");

        List<String> unverifiedEmails = users.stream()
                .filter(user -> !user.isVerified())
                .map(User::getEmailAddress)
                .collect(Collectors.toList());
        System.out.println("결과: " + unverifiedEmails);


        // 5. 주문 객체 필드 매핑: Order 객체에서 생성자 ID만 추출
        System.out.println("\n--- 5. 주문 객체 필드 매핑 (Order -> User ID) ---");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        List<Order> orders = prepareOrders(now);

        List<Long> createdByUserIds = orders.stream()
                .map(Order::getCreatedByUserId)
                .collect(Collectors.toList());
        System.out.println("결과: " + createdByUserIds);
    }

    private static List<User> prepareUsers() {
        User user1 = new User()
                .setId(101)
                .setName("Maximum0")
                .setVerified(true)
                .setEmailAddress("maximum.zero95@gmail.com");

        User user2 = new User()
                .setId(102)
                .setName("Alice")
                .setVerified(false)
                .setEmailAddress("alice102@gmail.com");

        User user3 = new User()
                .setId(103)
                .setName("Bob")
                .setVerified(false)
                .setEmailAddress("bob103@gmail.com");

        return Arrays.asList(user1, user2, user3);
    }

    private static List<Order> prepareOrders(LocalDateTime now) {
        Order order1 = new Order()
                .setId(1001)
                .setStatus(OrderStatus.CREATED)
                .setCreatedByUserId(101L)
                .setCreatedAt(now.minusHours(4));

        Order order2 = new Order()
                .setId(1002)
                .setStatus(OrderStatus.ERROR)
                .setCreatedByUserId(102L)
                .setCreatedAt(now.minusHours(1));

        Order order3 = new Order()
                .setId(1003)
                .setStatus(OrderStatus.PROCESSED)
                .setCreatedByUserId(103L)
                .setCreatedAt(now.minusHours(36));

        Order order4 = new Order()
                .setId(1004)
                .setStatus(OrderStatus.ERROR)
                .setCreatedByUserId(104L)
                .setCreatedAt(now.minusHours(15));

        Order order5 = new Order()
                .setId(1005)
                .setStatus(OrderStatus.IN_PROGRESS)
                .setCreatedByUserId(101L)
                .setCreatedAt(now.minusHours(10));

        return Arrays.asList(order1, order2, order3, order4, order5);
    }

}
