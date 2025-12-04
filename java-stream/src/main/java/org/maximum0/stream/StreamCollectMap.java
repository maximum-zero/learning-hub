package org.maximum0.stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.maximum0.stream.model.Order;
import org.maximum0.stream.model.Order.OrderStatus;
import org.maximum0.stream.model.User;

/**
 * 최종 연산: 수집 (Collectors.toMap) 학습 예제.
 */
public class StreamCollectMap {

    public static void main(String[] args) {
        // 1. Collectors.toMap (기본 타입)
        System.out.println("--- 1. Collectors.toMap (기본 타입) ---");

        Map<Integer, String> numberMap = Stream.of(3, 5, -4, 2, 6)
                .collect(Collectors.toMap(Function.identity(), x -> "Number is " + x));
        System.out.println("결과 맵: " + numberMap);
        System.out.println("키 3의 값: " + numberMap.get(3));


        // 2. Collectors.toMap (객체 Key, 객체 Value)
        System.out.println("\n--- 2. Collectors.toMap (객체 Key, 객체 Value) ---");

        List<User> users = prepareUsers();
        Map<Integer, User> userIdToUserMap = users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        System.out.println("결과 맵: " + userIdToUserMap);
        System.out.println("키 101의 값: " + userIdToUserMap.get(101));


        // 3. Collectors.toMap (객체 Key, 객체 필드 Value)
        System.out.println("\n--- 3. Collectors.toMap (객체 Key, 객체 필드 Value) ---");

        List<Order> orders = prepareOrders();
        Map<Long, OrderStatus> orderIdToOrderStatusMap = orders.stream()
                .collect(Collectors.toMap(Order::getId, Order::getStatus));
        System.out.println("결과 맵: " + orderIdToOrderStatusMap);
        System.out.println("키 1003L의 값: " + orderIdToOrderStatusMap.get(1003L));
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
