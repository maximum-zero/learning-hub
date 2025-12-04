package org.maximum0.advanced.processor;

import java.math.BigDecimal;
import java.util.function.Function;
import org.maximum0.advanced.model.Order;

/**
 * 주문(Order)의 현재 금액(Order.amount)에 설정된 세율(rate)을 적용하는 함수입니다.
 * 이 함수는 Function<Order, Order> 인터페이스를 구현하며,
 * 주문 금액 처리 파이프라인의 후반 단계에서 세금을 부과하는 역할을 합니다.
 */
public class TaxPriceProcessor implements Function<Order, Order> {
    private final BigDecimal rate;

    public TaxPriceProcessor(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public Order apply(Order order) {
        return order.setAmount(order.getAmount().multiply(rate.divide(new BigDecimal(100)).add(BigDecimal.ONE)));
    }

}
