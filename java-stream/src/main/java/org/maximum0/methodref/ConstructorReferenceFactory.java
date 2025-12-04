package org.maximum0.methodref;

import java.util.*;
import java.util.function.*;
import org.maximum0.methodref.model.Car;
import org.maximum0.methodref.model.Sedan;
import org.maximum0.methodref.model.Suv;
import org.maximum0.functional.model.User;
import org.maximum0.methodref.model.Van;

/**
 * 생성자 참조 (Constructor Reference) 및 이를 활용한 팩토리 패턴 간소화 예제.
 * 생성자 참조는 클래스::new 형태로, 생성자를 호출하여 객체를 생성하는 역할을 수행합니다.
 */
public class ConstructorReferenceFactory {

    public static void main(String[] args) {
        // 1. 기본 생성자 참조 (Basic Constructor Reference)
        System.out.println("--- 1. 기본 생성자 참조 예제 ---");

        BiFunction<Integer, String, User> userCreator = User::new;
        User alice = userCreator.apply(5, "Alice");
        System.out.println("생성된 User 객체: " + alice);


        // 2. 생성자 참조를 활용한 동적 객체 생성 (Dynamic Object Creation)
        System.out.println("\n--- 2. 생성자 참조를 활용한 팩토리 패턴 예제 ---");

        Map<String, BiFunction<String, String, Car>> carTypeToConstractorMap = new HashMap<>();
        carTypeToConstractorMap.put("sedan", Sedan::new);
        carTypeToConstractorMap.put("suv", Suv::new);
        carTypeToConstractorMap.put("van", Van::new);

        String[][] inputToCars = {
                {"sedan", "Sonata", "Hyundai"},
                {"suv", "Sorento", "KIA"},
                {"van", "Sienna", "Toyota"}
        };

        List<Car> cars = new ArrayList<>();
        for (String[] input : inputToCars) {
            String type = input[0];
            String name = input[1];
            String brand = input[2];

            Car car = carTypeToConstractorMap.get(type).apply(name, brand);
            cars.add(car);
        }

        for (Car car : cars) {
            car.drive();
        }
    }
}
