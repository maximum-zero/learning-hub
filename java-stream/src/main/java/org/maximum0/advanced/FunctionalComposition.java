package org.maximum0.advanced;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.maximum0.advanced.model.Order;
import org.maximum0.advanced.model.OrderLine;
import org.maximum0.advanced.processor.OrderLineAggregationPriceProcessor;
import org.maximum0.advanced.processor.TaxPriceProcessor;

/**
 * Functional Composition (함수 합성) 개념을 시연합니다.
 * 1. andThen() 메서드를 사용하여 여러 함수를 순차적으로 연결하는 방법
 * 2. reduce() 메서드를 사용하여 함수 목록(List<Function>)을 하나의 함수로 병합하는 방법 (Pipe Pattern)
 */
public class FunctionalComposition {

    public static void main(String[] args) {
        // 1. andThen()을 사용한 함수 합성
        System.out.println("--- 1. Composition with andThen() ---");

        Function<Integer, Integer> multiplyByTwo = x -> 2 * x;
        Function<Integer, Integer> addTen = x -> x + 10;

        // f.andThen(g)는 f를 먼저 실행하고 그 결과를 g의 입력으로 전달합니다. (순서: multiplyByTwo -> addTen)
        Function<Integer, Integer> composedFunction = multiplyByTwo.andThen(addTen);
        System.out.println("결과 : " + composedFunction.apply(3));


        // 2. reduce()를 사용한 함수 목록 병합
        System.out.println("\n--- 2. Composition with reduce() (Pipe Pattern) ---");

        Order unprocessedOrder = new Order()
                .setId(1001L)
                .setOrderLines(Arrays.asList(
                        new OrderLine().setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine().setAmount(BigDecimal.valueOf(2000))
                ));
        System.out.println("처리 전 금액: " + unprocessedOrder.getAmount());

        // 빈 함수(Identity)를 시작점으로, 목록의 함수들을 순서대로 연결해 데이터가 흐르는 하나의 파이프라인 함수를 만듭니다.
        List<Function<Order, Order>> priceProcessors = getPriceProcessors();
        Function<Order, Order> mergedPriceProcessors = priceProcessors.stream()
                .reduce(Function.identity(), Function::andThen);

        // [파이프라인 실행 순서]
        // 1. OrderLineAggregationPriceProcessor: 1000 + 2000 = 3000으로 금액 설정
        // 2. TaxPriceProcessor: 3000 * 1.09375 = 3281.25로 세금 적용
        Order processedOrder = mergedPriceProcessors.apply(unprocessedOrder);
        System.out.println("처리 후 금액: " + processedOrder.getAmount());
    }

    public static List<Function<Order, Order>> getPriceProcessors() {
        // 이 목록의 순서대로 함수가 합성되어 적용됩니다.
        return Arrays.asList(
                new OrderLineAggregationPriceProcessor(), // 1. OrderLine 금액 합산
                new TaxPriceProcessor(new BigDecimal("9.375")) // 2. 세금 적용 (9.375%)
        );
    }

}
