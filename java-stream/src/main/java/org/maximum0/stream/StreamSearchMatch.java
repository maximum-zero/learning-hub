package org.maximum0.stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.maximum0.stream.model.Order;
import org.maximum0.stream.model.Order.OrderStatus;
import org.maximum0.stream.model.User;

/**
 * 최종 연산: 탐색 및 매칭 (allMatch, anyMatch, findFirst, findAny) 학습 예제.
 */
public class StreamSearchMatch {

    public static void main(String[] args) {
        System.out.println("--- 1. Match 연산 (조건 일치 여부 확인) ---");

        // 1-1. allMatch: 모든 요소가 조건을 만족하는지 (모두 양수인가?)
        List<Integer> numbers = Arrays.asList(3, -4, 2, 7, 9);
        boolean allPositive = numbers.stream()
                .allMatch(number -> number > 0);
        System.out.println("모든 숫자가 양수인가? " + allPositive);

        // 1-2. anyMatch: 하나라도 조건을 만족하는지 (음수가 존재하는가?)
        boolean anyNegative = numbers.stream()
                .anyMatch(number -> number < 0);
        System.out.println("음수가 존재하는가? " + anyNegative);

        // 1-3. allMatch (객체): 모든 User가 검증되었는가?
        List<User> users = prepareUsers();
        boolean areAllUserVerified = users.stream()
                .allMatch(User::isVerified);
        System.out.println("모든 유저가 검증되었는가? " + areAllUserVerified);

        // 1-4. anyMatch (객체): 에러 상태인 주문이 하나라도 존재하는가?
        List<Order> orders = prepareOrders();
        boolean isAnyOrderInErrorStatus = orders.stream()
                .anyMatch(order -> order.getStatus().equals(OrderStatus.ERROR));
        System.out.println("에러 주문이 존재하는가? " + isAnyOrderInErrorStatus);


        System.out.println("\n--- 2. Find 연산 (요소 탐색) ---");

        // 2-1. findAny: 스트림에서 조건에 맞는 임의의 요소 하나를 찾음 (병렬 환경에 유리)
        Optional<Integer> anyNegativeInteger = Stream.of(3, 2, -5, 6, -10, 8)
                .filter(x -> x < 0)
                .findAny();
        System.out.print("임의의 음수: ");
        anyNegativeInteger.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("없음")
        );

        // 2-2. findFirst: 스트림에서 조건에 맞는 첫 번째 요소를 찾음 (순서가 중요할 때 사용)
        Optional<Integer> firstPositiveInteger = Stream.of(-1, 3, 2, -5, 6)
                .filter(x -> x > 0)
                .findFirst();
        System.out.print("첫 번째 양수: ");
        firstPositiveInteger.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("없음")
        );
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
                .setId(1001)
                .setStatus(OrderStatus.CREATED)
                .setAmount(BigDecimal.valueOf(2000));

        Order order2 = new Order()
                .setId(1002)
                .setStatus(OrderStatus.ERROR)
                .setAmount(BigDecimal.valueOf(4000));

        Order order3 = new Order()
                .setId(1003)
                .setStatus(OrderStatus.ERROR)
                .setAmount(BigDecimal.valueOf(3000));

        Order order4 = new Order()
                .setId(1004)
                .setStatus(OrderStatus.PROCESSED)
                .setAmount(BigDecimal.valueOf(7000));

        return Arrays.asList(order1, order2, order3, order4);
    }

}
