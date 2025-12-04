package org.maximum0.pattern;

import java.math.BigDecimal;
import java.util.Arrays;
import org.maximum0.pattern.model.Order;
import org.maximum0.pattern.model.Order.OrderStatus;
import org.maximum0.pattern.model.OrderLine;
import org.maximum0.pattern.service.OrderProcessStep;

/**
 * 함수형 책임 연쇄 패턴 (Functional Chain of Responsibility Pattern) 예제
 * 각 처리 단계(Handler)를 람다식(Consumer)으로 인라인 정의하고, setNext()를 이용해 유연하게 처리 파이프라인(Chain)을 구축합니다.
 */
public class ChainOfResponsibilityPattern {

    public static void main(String[] args) {
        // 각 처리 단계(책임)를 람다식(Consumer)으로 구현합니다.
        OrderProcessStep initializeStep = new OrderProcessStep(order -> {
            if (order.getStatus().equals(OrderStatus.CREATED)) {
                System.out.println("Start processing order " + order.getId());
                order.setStatus(OrderStatus.IN_PROGRESS);
            }
        });

        OrderProcessStep setOrderAmountStep = new OrderProcessStep(order -> {
            if (order.getStatus().equals(OrderStatus.IN_PROGRESS)) {
                System.out.println("Setting amount of order " + order.getId());
                order.setAmount(order.getOrderLines().stream()
                        .map(OrderLine::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                );
            }
        });

        OrderProcessStep verifyOrderStep = new OrderProcessStep(order -> {
            if (order.getStatus().equals(OrderStatus.IN_PROGRESS)) {
                System.out.println("Verify order " + order.getId());
                if (order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                    order.setStatus(OrderStatus.ERROR);
                }
            }
        });

        OrderProcessStep processPaymentStep = new OrderProcessStep(order -> {
            if (order.getStatus().equals(OrderStatus.IN_PROGRESS)) {
                System.out.println("Processing payment of order " + order.getId());
                order.setStatus(OrderStatus.PROCESSED);
            }
        });

        OrderProcessStep handleErrorStep = new OrderProcessStep(order -> {
            if (order.getStatus().equals(OrderStatus.ERROR)) {
                System.out.println("Sending out 'Failed to process order' alert for order " + order.getId());
            }
        });

        OrderProcessStep completeProcessingOrderStep = new OrderProcessStep(order -> {
            if (order.getStatus().equals(OrderStatus.PROCESSED)) {
                System.out.println("Finished processing order " + order.getId());
            }
        });

        // setNext() 메서드를 이용해 처리 순서대로 책임 체인(Chain)을 구축합니다.
        OrderProcessStep chainedOrderProcessSteps = initializeStep
                .setNext(setOrderAmountStep)
                .setNext(verifyOrderStep)
                .setNext(processPaymentStep)
                .setNext(handleErrorStep)
                .setNext(completeProcessingOrderStep);

        // 정상 주문 처리
        Order order = new Order()
                .setId(1001L)
                .setStatus(OrderStatus.CREATED)
                .setOrderLines(Arrays.asList(
                        new OrderLine().setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine().setAmount(BigDecimal.valueOf(2000))
                ));
        chainedOrderProcessSteps.process(order);

        // 실패 주문 처리 (verifyOrderStep에서 ERROR로 변경되어 handleErrorStep으로 넘어감)
        Order failingOrder = new Order()
                .setId(1002L)
                .setStatus(OrderStatus.CREATED)
                .setOrderLines(Arrays.asList(
                        new OrderLine().setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine().setAmount(BigDecimal.valueOf(-2000))
                ));
        chainedOrderProcessSteps.process(failingOrder);
    }

}
