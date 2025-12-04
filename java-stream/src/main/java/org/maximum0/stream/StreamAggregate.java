package org.maximum0.stream;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.maximum0.stream.model.Order;
import org.maximum0.stream.model.Order.OrderStatus;
import org.maximum0.stream.model.User;

/**
 * 최종 연산: 집계 (count, max, min) 학습 예제.
 */
public class StreamAggregate {

    public static void main(String[] args) {
        // 1. max(): 기본 타입에서 최댓값 찾기 (Optional<Integer> 반환)
        System.out.println("--- 1. max() (최댓값) ---");

        Optional<Integer> max = Stream.of(5, 3, 6, 2, 1)
                .max(Integer::compareTo);
        max.ifPresent(System.out::println);


        // 2. min(): 커스텀 객체에서 최솟값 찾기 (Optional<User> 반환)
        System.out.println("\n--- 2. min() (이름 기준 최소 User) ---");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        List<User> users = prepareUsers(now);
        User firstUserByName = users.stream()
                .min(Comparator.comparing(User::getName))
                .orElse(null);
        System.out.println(firstUserByName);


        // 3. count(): 필터링 후 개수 세기 (long 반환)
        System.out.println("\n--- 3. count() (양수 개수) ---");

        long positiveIntegerCount = Stream.of(1, -4, 5, -3, 6)
                .filter(x -> x > 0)
                .count();
        System.out.println("양수 개수: " + positiveIntegerCount);

        // 4. 복합 필터링 및 count(): 24시간 이내 미검증 User 수
        System.out.println("\n--- 4. 복합 count() (24시간 내 미검증 User) ---");

        long unverifiedUsersIn24Hrs = users.stream()
                .filter(user -> user.getCreatedAt().isAfter(now.minusDays(1)))
                .filter(user -> !user.isVerified())
                .count();
        System.out.println("24시간 내 미검증 User 수: " + unverifiedUsersIn24Hrs);

        // 5. max()와 Optional.orElse(): 에러 주문 중 최대 금액 찾기
        System.out.println("\n--- 5. max()와 orElse() (최대 에러 금액) ---");

        List<Order> orders = prepareOrders();
        BigDecimal maxErroredAmount = orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.ERROR))
                .map(Order::getAmount)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        System.out.println("최대 에러 주문 금액: " + maxErroredAmount);
    }


    private static List<User> prepareUsers(LocalDateTime now) {
        User user1 = new User()
                .setId(101)
                .setName("Maximum0")
                .setVerified(true)
                .setEmailAddress("maximum.zero95@gmail.com")
                .setCreatedAt(now.minusDays(2));

        User user2 = new User()
                .setId(102)
                .setName("Alice")
                .setVerified(false)
                .setEmailAddress("alice102@gmail.com")
                .setCreatedAt(now.minusHours(10));

        User user3 = new User()
                .setId(103)
                .setName("Bob")
                .setVerified(false)
                .setEmailAddress("bob103@gmail.com")
                .setCreatedAt(now.minusHours(1));

        User user4 = new User()
                .setId(104)
                .setName("David")
                .setVerified(false)
                .setEmailAddress("david104@gmail.com")
                .setCreatedAt(now.minusHours(27));

        return Arrays.asList(user1, user2, user3, user4);
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
