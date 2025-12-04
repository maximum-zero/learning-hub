package org.maximum0.stream;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 스트림 생성 예제.
 * 컬렉션, 배열, Stream.of() 등 다양한 소스로부터 스트림을 생성합니다.
 */
public class BasicStream {
    public static void main(String[] args) {
        // 1. Stream.of()를 이용한 스트림 생성
        System.out.println("--- 1. Stream.of()로 생성 ---");

        Stream<String> nameStream = Stream.of("Maximum0", "Alice", "Bob");
        List<String> nameList = nameStream.collect(Collectors.toList());
        System.out.println("결과 목록: " + nameList);


        // 2. 배열을 이용한 스트림 생성
        System.out.println("\n--- 2. 배열로 생성 ---");

        String[] cityArray = new String[] { "San Jose", "Seoul", "Tokyo" };
        Stream<String> cityStream = Arrays.stream(cityArray);
        List<String> cityList = cityStream.collect(Collectors.toList());
        System.out.println("결과 목록: " + cityList);


        // 3. 컬렉션(Set)을 이용한 스트림 생성
        System.out.println("\n--- 3. 컬렉션(Set)으로 생성 ---");

        Set<Integer> numberSet = new HashSet<>(Arrays.asList(3, -5, 10, 7, -3));
        Stream<Integer> numberStream = numberSet.stream();
        List<Integer> numberList = numberStream.collect(Collectors.toList());
        System.out.println("결과 목록: " + numberList);
    }

}
