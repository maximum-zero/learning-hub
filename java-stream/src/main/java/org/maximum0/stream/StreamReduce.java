package org.maximum0.stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.maximum0.stream.model.Order;
import org.maximum0.stream.model.OrderLine;
import org.maximum0.stream.model.User;

/**
 * 최종 연산: 축소 (reduce) 학습 예제.
 */
public class StreamReduce {

    public static void main(String[] args) {
        // 1. reduce(BinaryOperator) - 인자 1개
        System.out.println("--- 1. reduce(BinaryOperator) - 인자 1개 ---");

        List<Integer> numbers = Arrays.asList(1, 4, -2, -5, 3);
        Optional<Integer> sumOptional = numbers.stream()
                .reduce(Integer::sum);
        sumOptional.ifPresent(sum -> System.out.println("합계: " + sum));

        Optional<Integer> maxOptional = numbers.stream()
                .reduce(Integer::max);
        maxOptional.ifPresent(max -> System.out.println("최댓값: " + max));


        // 2. reduce(Identity, BinaryOperator) - 인자 2개
        System.out.println("\n--- 2. reduce(Identity, BinaryOperator) - 인자 2개 ---");

        int multiply = numbers.stream()
                .reduce(1, (acc, x) -> acc * x);
        System.out.println("곱셈: " + multiply);


        // 3. Map-Reduce 패턴: User 리스트의 친구 총원 계산
        System.out.println("\n--- 3. Map-Reduce (친구 총원 계산) ---");

        List<User> users = prepareUsers();
        int sumOfNumberOfFriends = users.stream()
                .map(User::getFriendUserIds)
                .map(List::size)
                .reduce(0, Integer::sum);
        System.out.println("친구 총원: " + sumOfNumberOfFriends);


        // 4. reduce(Identity, Accumulator, Combiner) - 인자 3개 (병렬 스트림용)
        System.out.println("\n--- 4. reduce(Identity, Accumulator, Combiner) - 인자 3개 ---");

        List<String> numberStrList = Arrays.asList("3", "2", "5", "-4");
        int sumOfNumberStrList = numberStrList.stream()
                .reduce(0,
                        (number, str) -> number + Integer.parseInt(str),
                        Integer::sum);
        System.out.println("문자열 합계: " + sumOfNumberStrList);


        // 5. FlatMap-Reduce 패턴: 주문 내역의 전체 금액 합산
        System.out.println("\n--- 5. FlatMap-Reduce (전체 주문 금액) ---");

        List<Order> orders = prepareOrdersWithLines();
        BigDecimal sumOfAmounts = orders.stream()
                .map(Order::getOrderLines)
                .flatMap(List::stream)
                .map(OrderLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("전체 주문 금액 합계: " + sumOfAmounts);
    }

    private static List<User> prepareUsers() {
        User user1 = new User()
                .setId(101)
                .setName("Maximum0")
                .setFriendUserIds(Arrays.asList(201, 202, 203, 204));

        User user2 = new User()
                .setId(102)
                .setName("Alice")
                .setFriendUserIds(Arrays.asList(204, 205, 206));

        User user3 = new User()
                .setId(103)
                .setName("Bob")
                .setFriendUserIds(Arrays.asList(204, 205, 207));

        return Arrays.asList(user1, user2, user3);
    }

    private static List<Order> prepareOrdersWithLines() {
        Order order1 = new Order()
                .setId(1001L)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000))
                ));

        Order order2 = new Order()
                .setId(1002L)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(3000))
                ));

        Order order3 = new Order()
                .setId(1003L)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000))
                ));

        return Arrays.asList(order1, order2, order3);
    }

}
