package org.maximum0.advanced;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Lazy Evaluation (지연 평가)
 */
public class LazyEvaluation {

    public static void main(String[] args) {
        System.out.println("--- 1. Short-Circuiting ---");

        // 'returnTrue()'가 이미 true이므로, || 뒤의 'returnFalse()'는 실행되지 않음 (Lazy Evaluation 원리)
        if (returnTrue() || returnFalse()) {
            System.out.println("결과: true\n");
        }

        System.out.println("--- 2. 일반 함수 호출 (Eager Evaluation) ---");

        // or() 함수를 호출하기 전에 두 인수를 모두 평가해야 하므로, 두 함수 모두 실행됨 (Eager Evaluation)
        if (or(returnTrue(), returnFalse())) {
            System.out.println("결과: true\n");
        }

        System.out.println("--- 3. Supplier를 이용한 함수의 Lazy Evaluation ---");

        // Supplier(람다)가 인수로 전달되어, 필요할 때 (x.get())만 실행됨 (Lazy Evaluation)
        if (lazyOr(() -> returnTrue(), () -> returnFalse())) {
            System.out.println("결과: true");
        }

        System.out.println("\n--- 4. Stream API Lazy Evaluation (파이프라인) ---");

        Stream<Integer> integerStream = Stream.of(3, -2, 5, 8, 3, 10)
                // Intermediate Operation (중간 연산)
                .filter(x -> x > 0)
                // Intermediate Operation (중간 연산)
                .peek(x -> System.out.println("peek: " + x))
                // Intermediate Operation (중간 연산)
                .filter(x -> x % 2 == 0);

        // 이 시점까지는 위 중간 연산들이 전혀 실행되지 않음 (Lazy)
        System.out.println("Before collect (중간 연산 실행 안 됨)");

        // Terminal Operation (최종 연산)이 호출될 때 비로소 전체 파이프라인이 실행됨 (실행 시점)
        List<Integer> integers = integerStream.collect(Collectors.toList());
        System.out.println("After collect : " + integers);
    }

    /**
     * 실행 시점에 "returnTrue"를 출력하고 true를 반환하는 함수.
     * 평가 시점을 추적하는 데 사용됩니다.
     */
    public static boolean returnTrue() {
        System.out.println("▶ returnTrue 실행");
        return true;
    }

    /**
     * 실행 시점에 "returnFalse"를 출력하고 false를 반환하는 함수.
     * 평가 시점을 추적하는 데 사용됩니다.
     */
    public static boolean returnFalse() {
        System.out.println("▶ returnFalse 실행");
        return false;
    }

    /**
     * 일반적인 논리 OR 함수.
     * 함수 호출 전 두 인수가 모두 평가되어야 하므로, Eager Evaluation 방식으로 작동합니다.
     */
    public static boolean or(boolean x, boolean y) {
        return x || y;
    }

    /**
     * 지연 OR 함수.
     * 인수를 Supplier(람다)로 받기 때문에, x.get()이 true일 경우 y.get()을 호출하지 않아
     * 불필요한 계산을 회피하는 Lazy Evaluation 방식으로 작동합니다.
     */
    public static boolean lazyOr(Supplier<Boolean> x, Supplier<Boolean> y) {
        return x.get() || y.get();
    }

}
