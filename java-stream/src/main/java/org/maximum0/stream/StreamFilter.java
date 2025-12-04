package org.maximum0.stream;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.maximum0.stream.model.Order;
import org.maximum0.stream.model.Order.OrderStatus;
import org.maximum0.stream.model.User;

/**
 * 스트림 필터링 (Intermediate Operation: filter) 예제.
 * 조건에 맞는 요소만 스트림으로 전달합니다.
 */
public class StreamFilter {
    public static void main(String[] args) {
        // 1. 기본 필터링: 양수 필터링
        System.out.println("--- 1. 기본 필터링 (양수) ---");

        List<Integer> positiveNumbers = Stream.of(3, -5, 10, 7, -3)
                .filter(number -> number > 0)
                .collect(Collectors.toList());
        System.out.println("결과: " + positiveNumbers);


        // 2. 객체 필터링: 검증된(Verified) User 필터링
        System.out.println("\n--- 2. 객체 필터링 (Verified User) ---");

        List<User> users = prepareUsers();
        List<User> verifiedUsers = users.stream()
                .filter(User::isVerified)
                .collect(Collectors.toList());
        System.out.println("결과: " + verifiedUsers);


        // 3. 복합 필터링: ERROR 상태인 주문 필터링
        System.out.println("\n--- 3. 복합 필터링 (ERROR 주문) ---");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        List<Order> orders = prepareOrders(now);
        List<Order> errorOrders = orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.ERROR))
                .collect(Collectors.toList());
        System.out.println("결과: " + errorOrders);


        // 4. 다중 조건 필터링: ERROR 상태이고 12시간 이내 생성된 주문 필터링
        System.out.println("\n--- 4. 다중 조건 필터링 (ERROR + 12시간 이내) ---");

        List<Long> recentErrorOrdersUserIds = orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.ERROR))
                .filter(order -> order.getCreatedAt().isAfter(now.minusHours(12)))
                .map(Order::getCreatedByUserId)
                .collect(Collectors.toList());
        System.out.println("결과 (User ID 목록): " + recentErrorOrdersUserIds);
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
