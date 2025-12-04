package org.maximum0.stream;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.maximum0.stream.model.Order;
import org.maximum0.stream.model.Order.OrderStatus;
import org.maximum0.stream.model.User;

/**
 * 스트림 정렬 (sorted) 및 중복 제거 (distinct) 예제.
 */
public class StreamSortedDistinct {
    public static void main(String[] args) {
        // 1. 기본 정렬: 숫자 오름차순 정렬
        System.out.println("--- 1. 기본 정렬 (숫자 오름차순) ---");

        List<Integer> numbers = Arrays.asList(3, -5, 7, 4);
        List<Integer> sortedNumbers = numbers.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("결과: " + sortedNumbers);


        // 2. 커스텀 정렬: User 이름을 기준으로 오름차순 정렬
        System.out.println("\n--- 2. 커스텀 정렬 (User 이름 기준) ---");

        List<User> users = prepareUsers();
        List<User> sortedUsersByName = users.stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
        System.out.println("결과: " + sortedUsersByName);

        // 3. 시간 순 정렬: 주문 생성 시간 순으로 정렬
        System.out.println("\n--- 3. 시간 순 정렬 (주문 생성 시간 기준) ---");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        List<Order> orders = prepareOrders(now);
        List<Order> sortedOrdersByDate = orders.stream()
                .sorted((o1, o2) -> o1.getCreatedAt().compareTo(o2.getCreatedAt()))
                .collect(Collectors.toList());
        System.out.println("결과: " + sortedOrdersByDate);

        // 4. 중복 제거 (distinct)
        System.out.println("\n--- 4. 중복 제거 (distinct) ---");

        List<Integer> redundantNumbers = Arrays.asList(3, -5, 4, -5, 2, 3);
        List<Integer> distinctNumbers = redundantNumbers.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("결과: " + distinctNumbers);

        // 5. 복합 연산: 주문 목록에서 고유한 User ID 목록 추출 후 정렬
        System.out.println("\n--- 5. 복합 연산 (고유 User ID 추출 후 정렬) ---");

        List<Long> uniqueUserIds = orders.stream()
                .map(Order::getCreatedByUserId)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("결과: " + uniqueUserIds);
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