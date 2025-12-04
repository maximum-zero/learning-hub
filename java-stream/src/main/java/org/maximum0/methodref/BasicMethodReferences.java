package org.maximum0.methodref;

import java.util.*;
import java.util.function.*;
import org.maximum0.functional.model.User;

/**
 * 메서드 참조 (Method Reference) 기본 유형 예제.
 * 람다식이 기존 메서드를 단 하나만 호출할 때, 코드를 클래스명::메서드명 형태로 간결하게 만듭니다.
 * 이 클래스는 생성자 참조를 제외한 3가지 기본 유형을 다룹니다.
 */
public class BasicMethodReferences {

    public static void main(String[] args) {
        // 1. 정적 메서드 참조 (Static Method Reference)
        System.out.println("--- 1. 정적 메서드 참조 예시 ---");

        Function<String, Integer> strToInt = Integer::parseInt;
        System.out.println("15를 정수로 변환: " + strToInt.apply("15"));

        // 1-1. 정적 메서드 참조를 BiFunction 인수로 전달하는 예시
        System.out.println("8 * 2 = " + calculate(8, 2, BasicMethodReferences::multiply));


        // 2. 특정 객체의 인스턴스 메서드 참조 (Reference to an Instance Method)
        System.out.println("\n--- 2. 특정 객체의 인스턴스 메서드 참조 예시 ---");

        String str = "hello";
        Predicate<String> equalsToHello = str::equals;
        System.out.println("hello 객체와 일치하는지 비교: " + equalsToHello.test("hello"));

        // 2-1. 특정 객체(인스턴스)의 메서드 참조를 BiFunction 인수로 전달하는 예시
        BasicMethodReferences instance = new BasicMethodReferences();
        System.out.println("8 - 2 = " + calculate(8, 2, instance::subtract));


        // 3. 임의 객체의 인스턴스 메서드 참조 (Reference to an Instance Method of an Arbitrary Object)
        System.out.println("\n--- 3. 임의 객체의 인스턴스 메서드 참조 예시 ---");

        Function<String, Integer> strLength = String::length;
        System.out.println("hello world의 길이: " + strLength.apply("hello world"));

        // User::getId : 임의의 User 객체의 getId() 메서드 참조
        List<User> users = Arrays.asList(new User(3, "Bob"), new User(1, "Max"));
        System.out.print("User ID 목록: ");
        printUserField(users, User::getId);
    }

    /**
     * BiFunction을 이용하여 두 정수(x, y)에 대한 연산을 수행하는 범용 계산기 메서드.
     * 메서드 참조 또는 람다식을 통해 연산(operator)을 외부에서 주입받아 처리합니다.
     * @param x 첫 번째 정수 입력 값
     * @param y 두 번째 정수 입력 값
     * @param operator 연산을 정의하는 BiFunction<Integer, Integer, Integer> 함수형 인터페이스
     * @return 연산자(operator)를 적용한 최종 결과 값
     */
    public static int calculate(int x, int y, BiFunction<Integer, Integer, Integer> operator) {
        return operator.apply(x, y);
    }

    /**
     * 두 정수를 곱하는 정적 메서드.
     * @param x 첫 번째 정수
     * @param y 두 번째 정수
     * @return 두 정수의 곱
     */
    public static int multiply(int x, int y) {
        return x * y;
    }

    /**
     * 두 정수를 빼는 인스턴스 메서드.
     * @param x 첫 번째 정수
     * @param y 두 번째 정수
     * @return x - y의 결과
     */
    public int subtract(int x, int y) {
        return x - y;
    }

    /**
     * User 리스트의 각 User 객체에서 특정 필드 값(getter로 지정)을 가져와 출력하는 범용 메서드.
     * Function<User, Object>의 첫 번째 인자(User)가 메서드를 호출하는 객체가 됩니다.
     * @param users 필드 값을 가져올 User 객체 리스트
     * @param getter User 객체에서 원하는 필드 값(Object)을 추출하는 Function<User, Object>
     */
    public static void printUserField(List<User> users, Function<User, Object> getter) {
        for (User user : users) {
            System.out.print(getter.apply(user) + " ");
        }
        System.out.println();
    }
}
