package org.maximum0.stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.maximum0.stream.model.Order;
import org.maximum0.stream.model.Order.OrderStatus;
import org.maximum0.stream.model.User;
import org.maximum0.stream.service.EmailService;

/**
 * 최종 연산: 수집 (Collectors.groupingBy, partitioningBy) 학습 예제.
 */
public class StreamCollectGroup {

    public static void main(String[] args) {
        // 1. Collectors.groupingBy
        System.out.println("--- 1. Collectors.groupingBy ---");

        List<Integer> numbers = Arrays.asList(13, 2, 101, 203, 304, 402, 305, 349, 2312, 203);
        Map<Integer, List<Integer>> unitDigitMap = numbers.stream()
                .collect(Collectors.groupingBy(number -> number % 10));
        System.out.println(unitDigitMap);


        // 2. Collectors.groupingBy (Set으로 변환)
        System.out.println("\n--- 2. Collectors.groupingBy (Set으로 변환) ---");

        Map<Integer, Set<Integer>> unitDigitSet = numbers.stream()
                .collect(Collectors.groupingBy(number -> number % 10, Collectors.toSet()));
        System.out.println(unitDigitSet);


        // 3. Collectors.groupingBy (값 변환 후 수집)
        System.out.println("\n--- 3. Collectors.groupingBy (값 변환 후 수집) ---");

        Map<Integer, List<String>> unitDigitStrMap = numbers.stream()
                .collect(Collectors.groupingBy(number -> number % 10,
                        Collectors.mapping(number -> "unit digit is " + number, Collectors.toList())));
        System.out.println(unitDigitStrMap);
        System.out.println(unitDigitStrMap.get(3));


        // 4. Collectors.groupingBy (총 금액 합계)
        System.out.println("\n--- 4. Collectors.groupingBy (총 금액 합계) ---");

        List<Order> orders = prepareOrders();
        Map<OrderStatus, BigDecimal> orderStatusToSumOfAmontMap = orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus,
                        Collectors.mapping(Order::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        System.out.println(orderStatusToSumOfAmontMap);


        // 5. Collectors.partitioningBy (짝수/홀수 분할)
        System.out.println("\n--- 5. Collectors.partitioningBy (짝수/홀수 분할) ---");

        Map<Boolean, List<Integer>> numberPartitions = numbers.stream()
                .collect(Collectors.partitioningBy(number -> number % 2 == 0));
        System.out.println(numberPartitions.get(true));
        System.out.println(numberPartitions.get(false));


        // 6. Collectors.partitioningBy (친구 수 기준 분류)
        System.out.println("\n--- 6. Collectors.partitioningBy (친구 수 기준 분류) ---");

        List<User> users = prepareUsers();
        EmailService emailService = new EmailService();
        Map<Boolean, List<User>> userPartitions = users.stream()
                .collect(Collectors.partitioningBy(user -> user.getFriendUserIds().size() > 5));
        userPartitions.get(true).forEach(emailService::sendPlayWithFriendsEmail);
        userPartitions.get(false).forEach(emailService::sendMakeWithFriendsEmail);
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
                .setEmailAddress("alice102@gmail.com")
                .setFriendUserIds(Arrays.asList(204, 205, 206));

        User user3 = new User()
                .setId(103)
                .setName("Bob")
                .setEmailAddress("bob103@gmail.com")
                .setFriendUserIds(Arrays.asList(204, 205, 207));

        return Arrays.asList(user1, user2, user3);
    }

    private static List<Order> prepareOrders() {
        Order order1 = new Order()
                .setId(1001L)
                .setStatus(OrderStatus.CREATED)
                .setAmount(BigDecimal.valueOf(2000));

        Order order2 = new Order()
                .setId(1002L)
                .setStatus(OrderStatus.ERROR)
                .setAmount(BigDecimal.valueOf(4000));

        Order order3 = new Order()
                .setId(1003L)
                .setStatus(OrderStatus.ERROR)
                .setAmount(BigDecimal.valueOf(3000));

        Order order4 = new Order()
                .setId(1004L)
                .setStatus(OrderStatus.PROCESSED)
                .setAmount(BigDecimal.valueOf(7000));

        return Arrays.asList(order1, order2, order3, order4);
    }

}
