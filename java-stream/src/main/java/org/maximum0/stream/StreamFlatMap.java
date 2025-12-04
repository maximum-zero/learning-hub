package org.maximum0.stream;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.maximum0.stream.model.Order;
import org.maximum0.stream.model.Order.OrderStatus;
import org.maximum0.stream.model.OrderLine;
import org.maximum0.stream.model.OrderLine.OrderLineType;

/**
 * 스트림 평탄화 (Intermediate Operation: flatMap) 예제.
 * 스트림 안에 있는 스트림을 하나의 스트림으로 합쳐(평탄화) 처리합니다.
 */
public class StreamFlatMap {
    public static void main(String[] args) {
        // 1. 배열 평탄화: 2차원 문자열 배열을 1차원 리스트로 변환
        System.out.println("--- 1. 배열 평탄화 (도시 목록) ---");

        String[][] cities = new String[][] {
                { "Seoul", "Busan" },
                { "San Francisco", "New York" },
                { "Madrid", "Barcelona" },
        };
        Stream<String[]> cityArrayStream = Arrays.stream(cities);
        List<String> flattenedCityList = cityArrayStream
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        System.out.println("결과: " + flattenedCityList);


        // 2. 객체 리스트 평탄화: 주문(Order) 목록에서 모든 주문 라인(OrderLine)을 하나의 리스트로 합침
        System.out.println("\n--- 2. 객체 리스트 평탄화 (모든 주문 라인 병합) ---");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        List<Order> orders = prepareOrdersWithLines(now);

        List<OrderLine> mergedOrderLines = orders.stream()
                .map(Order::getOrderLines)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("결과: " + mergedOrderLines);
    }

    private static List<Order> prepareOrdersWithLines(LocalDateTime now) {
        Order order1 = new Order()
                .setId(1001)
                .setStatus(OrderStatus.CREATED)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setId(10001)
                                .setType(OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(5000)),
                        new OrderLine()
                                .setId(10002)
                                .setType(OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(4000))
                ))
                .setCreatedByUserId(101L)
                .setCreatedAt(now.minusHours(4));

        Order order2 = new Order()
                .setId(1002)
                .setStatus(OrderStatus.ERROR)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setId(10003)
                                .setType(OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(2000)),
                        new OrderLine()
                                .setId(10004)
                                .setType(OrderLineType.DISCOUNT)
                                .setAmount(BigDecimal.valueOf(-1000))
                ))
                .setCreatedByUserId(102L)
                .setCreatedAt(now.minusHours(1));

        Order order3 = new Order()
                .setId(1003)
                .setStatus(OrderStatus.PROCESSED)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setId(10005)
                                .setType(OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(2000))
                ))
                .setCreatedByUserId(103L)
                .setCreatedAt(now.minusHours(36));

        return Arrays.asList(order1, order2, order3);
    }

}
