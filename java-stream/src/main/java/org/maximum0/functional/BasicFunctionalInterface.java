package org.maximum0.functional;

import java.util.*;
import java.util.function.*;
import org.maximum0.functional.model.User;

/**
 * Java 표준 함수형 인터페이스 (Supplier, Consumer, Predicate, Comparator) 사용 예제.
 * 함수형 인터페이스는 단 하나의 추상 메서드(Single Abstract Method)를 가지며 람다식 구현의 대상이 됩니다.
 */
public class BasicFunctionalInterface {

    public static void main(String[] args) {
        // 1. Supplier<T> 예제: 값 제공 (T get())
        System.out.println("--- 1. Supplier 예시 ---");

        Supplier<Double> randomSupplier = () -> Math.random();
        printValuesForCount(randomSupplier, 3);


        // 2. Consumer<T> 예제: 값 소비 (void accept(T t))
        System.out.println("\n--- 2. Consumer 예시 ---");

        Consumer<Integer> processor = (x) -> System.out.println("processor: " + x);
        process(Arrays.asList(4, 2, 3), processor);


        // 3. Predicate<T> 예제: 조건 테스트 (boolean test(T t))
        System.out.println("\n--- 3. Predicate 예시 ---");

        List<Integer> inputs = Arrays.asList(10, -5, 4, 100, -2, 0, 3);
        System.out.println("전체: " + inputs);

        Predicate<Integer> isPositive = x -> x > 0;
        System.out.println("양수 필터링: " + filter(inputs, isPositive));
        System.out.println("음수 or 0 필터링: " + filter(inputs, isPositive.negate()));

        Predicate<Integer> isEven = (x) -> x % 2 == 0;
        System.out.println("양수 and 짝수 필터링: " + filter(inputs, isPositive.and(isEven)));


        // 4. Comparator<T> 예제: 비교 (int compare(T t1, T t2))
        System.out.println("\n--- 4. Comparator 예시 ---");

        List<User> users = new ArrayList<>();
        users.add(new User(3, "Bob"));
        users.add(new User(1, "Maximum0"));
        users.add(new User(5, "Alice"));
        System.out.println("초기 User 목록: " + users);

        // 4-1. ID를 기준으로 오름차순 비교하는 람다식
        Comparator<User> idComparator = (u1, u2) -> u1.getId() - u2.getId();
        Collections.sort(users, idComparator);
        System.out.println("ID 기준 정렬: " + users);

        // 4-2. 이름을 기준으로 비교하는 람다식을 직접 전달
        Collections.sort(users, (u1, u2) -> u1.getName().compareTo(u2.getName()));
        System.out.println("이름 기준 정렬: " + users);
    }

    /**
     * Supplier를 count 횟수만큼 호출하여 그 결과를 출력하는 메서드.
     * Supplier는 값을 제공하는 역할만 수행하며, 입력 인자가 없습니다.
     * @param supplier 결과를 제공할 Supplier<Double>
     * @param count 호출(반복) 횟수
     */
    public static void printValuesForCount(Supplier<Double> supplier, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println("printValuesForCount : " + supplier.get());
        }
    }

    /**
     * 리스트의 각 요소에 대해 Consumer를 적용하는 제네릭 메서드.
     * Consumer는 값을 받아 처리(소비)하는 역할만 수행하며, 반환 값이 없습니다.
     * @param inputs 처리할 데이터 리스트
     * @param processor 각 요소를 처리할 Consumer<T>
     * @param <T> 리스트 요소의 타입
     */
    public static <T> void process(List<T> inputs, Consumer<T> processor) {
        for (T input : inputs) {
            processor.accept(input);
        }
    }

    /**
     * Predicate의 조건에 맞는 요소만 걸러내어(filter) 새 리스트로 반환하는 제네릭 메서드.
     * Predicate는 값을 받아 조건 테스트(boolean 반환)를 수행합니다.
     * @param inputs 필터링할 데이터 리스트
     * @param condition 필터링 조건 (Predicate<T>)
     * @param <T> 리스트 요소의 타입
     * @return 조건에 맞는 요소들로 구성된 새 리스트
     */
    public static <T> List<T> filter(List<T> inputs, Predicate<T> condition) {
        List<T> output = new ArrayList<>();
        for (T input : inputs) {
            if (condition.test(input)) {
                output.add(input);
            }
        }
        return output;
    }
}
