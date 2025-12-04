package org.maximum0.pattern.service;

import java.util.Optional;
import java.util.function.Consumer;
import org.maximum0.pattern.model.Order;

/**
 * 책임 연쇄 패턴의 Link/Handler
 * 각 처리 단계(책임)를 람다(Consumer)로 정의하고, 다음 단계를 연결(Chain)하는 역할을 수행합니다.
 */
public class OrderProcessStep {
    private final Consumer<Order> processOrder;
    private OrderProcessStep next;

    public OrderProcessStep(Consumer<Order> processOrder) {
        this.processOrder = processOrder;
    }

    /**
     * 책임 연쇄(Chain)를 구축하는 메서드.
     * 체인의 끝까지 이동하여 다음 단계를 연결하며, Fluent Interface를 제공합니다.
     */
    public OrderProcessStep setNext(OrderProcessStep next) {
        if (this.next == null) {
            this.next = next;
        } else {
            this.next.setNext(next);
        }
        return this;
    }

    public void process(Order order) {
        processOrder.accept(order);
        Optional.ofNullable(next)
                .ifPresent(nextStep -> nextStep.process(order));
    }
}
