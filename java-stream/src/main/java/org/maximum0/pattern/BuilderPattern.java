package org.maximum0.pattern;

import org.maximum0.pattern.model.User;

/**
 * 함수형 빌더 패턴 (Functional Builder Pattern) 예제
 *
 * 전통적인 빌더 패턴의 단점(설정 메서드 수 증가)을 보완하기 위해
 * java.util.function.Consumer를 활용하여 빌더를 구현합니다.
 */
public class BuilderPattern {

    public static void main(String[] args) {
        User user = User.builder(1, "maximum0") // 필수 필드 (id, name)
                .with(builder -> {
                    // Consumer 람다 내부에서 Builder 필드에 직접 접근하여 값 설정
                    builder.emailAddress = "maximum.zero95@gmail.com";
                    builder.isVerified = true;
                })
                .build();

        System.out.println(user);
    }
}
