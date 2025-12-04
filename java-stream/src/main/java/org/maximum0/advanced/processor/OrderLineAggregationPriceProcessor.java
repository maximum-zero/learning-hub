package org.maximum0.advanced.processor;

import java.math.BigDecimal;
import java.util.function.Function;
import org.maximum0.advanced.model.Order;
import org.maximum0.advanced.model.OrderLine;

/**
 * 주문(Order)의 각 품목(OrderLine)의 금액을 합산하여 최종 주문 금액(Order.amount)을 계산하는 함수입니다.
 * 이 함수는 Function<Order, Order> 인터페이스를 구현하며,
 * 함수 합성(Composition)을 위한 파이프라인의 한 단계로 사용됩니다.
 */
public class OrderLineAggregationPriceProcessor implements Function<Order, Order> {

    @Override
    public Order apply(Order order) {
        return order.setAmount(order.getOrderLines().stream()
                .map(OrderLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }
}
