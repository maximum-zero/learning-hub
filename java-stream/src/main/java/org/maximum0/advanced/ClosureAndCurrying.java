package org.maximum0.advanced;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 함수형 프로그래밍의 핵심 개념인 Scope(유효 범위), Closure(클로저), Currying(커링)
 */
public class ClosureAndCurrying {

    public static void main(String[] args) {
        // 1. 클로저 (단순 변수 캡처)
        System.out.println("--- 1. Simple Closure (Variable Capture) ---");

        Supplier<String> greetingSupplier = createGreetingClosure();
        // createGreetingClosure 함수는 이미 종료되었지만, greetingSupplier는 'Hello'를 기억함
        System.out.println("결과: " + greetingSupplier.get());


        // 2. 클로저 (상태 보존 - ID 생성기)
        System.out.println("\n--- 2. Stateful Closure (Counter) ---");

        Function<String, Integer> idGenerator = createIdGenerator();
        // 클로저는 외부 함수가 종료된 후에도 counter의 상태를 보존합니다 (0 -> 1 -> 2)
        // - 값(Primitive)이 아닌, 상태 변경이 가능한 주소(Reference)를 캡처했기 때문에 가능
        System.out.println("ID 1: " + idGenerator.apply("UserA"));
        System.out.println("ID 2: " + idGenerator.apply("UserB"));


        // 3. 커링 (함수 분할 및 부분 적용)
        System.out.println("\n--- 3. Currying (Partial Application) ---");

        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
        // 커링 함수: 인수를 하나씩 받는 중첩 함수로 변환
        Function<Integer, Function<Integer, Integer>> curriedAdd = x -> y -> add.apply(x, y);

        // 부분 적용: 첫 번째 인수(3)를 고정하여 새로운 함수(addThree)를 생성
        Function<Integer, Integer> addThree = curriedAdd.apply(3);
        int result = addThree.apply(10);
        System.out.println("Curried 결과 (3 + 10): " + result);
    }

    /**
     * 클로저의 기본 원리: 외부 스코프의 변수(hello)를 캡처합니다.
     */
    public static Supplier<String> createGreetingClosure() {
        String hello = "Hello"; // 외부 변수
        Supplier<String> supplier = () -> {
            String world = "World";
            return hello + world; // 내부 함수가 외부 변수 hello를 참조
        };
        return supplier; // 리턴된 람다가 hello를 기억하며 클로저를 형성
    }

    /**
     * 클로저의 활용: 외부 스코프에서 상태(counter)를 유지하고 변경합니다.
     */
    public static Function<String, Integer> createIdGenerator() {
        final int[] counter = {0};

        // 이 람다 함수(클로저)는 외부 변수 counter를 캡처합니다.
        Function<String, Integer> generator = (name) -> {
            counter[0]++; // 캡처된 변수의 상태를 변경 (Side Effect)
            return counter[0];
        };
        return generator;
    }

}
