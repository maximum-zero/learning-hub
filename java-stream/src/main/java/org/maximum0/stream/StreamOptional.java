package org.maximum0.stream;

import java.util.Optional;
import org.maximum0.stream.model.User;

/**
 * 최종 연산 및 Optional: Optional<T>의 핵심 사용법 학습 예제.
 * Optional은 값이 존재할 수도 있고 존재하지 않을 수도 있는 컨테이너 객체로, null 체크를 대체합니다.
 */
public class StreamOptional {
    public static void main(String[] args) {
        // 1. Optional 생성 및 기본 메서드
        System.out.println("--- 1. Optional 생성 및 기본 메서드 ---");

        Optional<String> maybeEmail = Optional.of("some@email.com");
        Optional<String> maybeEmailNull = Optional.ofNullable(null);
        Optional<String> maybeEmailEmpty = Optional.empty();

        // get(): 값이 있어야만 호출 가능 (없으면 NoSuchElementException 발생)
        System.out.println("get(): " + maybeEmail.get());

        // isPresent(): 값의 존재 여부 확인
        if (maybeEmailNull.isPresent()) {
            System.out.println("maybeEmailNull 값 존재");
        } else {
            System.out.println("maybeEmailNull 값 없음");
        }


        // 2. Optional 값 추출 및 기본값 제공
        System.out.println("\n--- 2. Optional 값 추출 및 기본값 ---");

        String defaultEmail = "default@email.com";

        // orElse(): 값이 없으면 기본값 반환
        String email3 = maybeEmailEmpty.orElse(defaultEmail);
        System.out.println("orElse 결과 (기본값): " + email3);

        // orElseGet(): 값이 없으면 Supplier 람다 실행 (기본값 생성 로직이 복잡할 때 효율적)
        String email4 = maybeEmailEmpty.orElseGet(() -> {
            System.out.print("orElseGet 실행: ");
            return defaultEmail;
        });
        System.out.println(email4);


        // 3. ifPresent: 값이 있을 때만 Consumer 람다 실행
        System.out.println("\n--- 3. ifPresent (있을 때만 실행) ---");

        User userWithEmail = new User()
                .setId(1001)
                .setName("Maximum0")
                .setEmailAddress("maximum.zero95@gmail.com")
                .setVerified(true);

        Optional.ofNullable(userWithEmail)
                .ifPresent(user -> System.out.println("User 이름 출력: " + user.getName()));


        // 4. map: Optional 내부의 값을 변환 (Optional<T> -> Optional<R>)
        System.out.println("\n--- 4. map (User -> ID 변환) ---");

        Optional<Integer> maybeId = Optional.ofNullable(userWithEmail)
                .map(User::getId);
        maybeId.ifPresent(id -> System.out.println("User ID: " + id));

        String welcomeName = Optional.ofNullable(userWithEmail)
                .map(User::getName)
                .map(name -> "Hello, " + name + "!")
                .orElse("Guest");
        System.out.println("Welcome 메시지: " + welcomeName);


        // 5. flatMap: Optional 안에 Optional이 중첩될 때 평탄화 (Optional<Optional<T>> -> Optional<T>)
        System.out.println("\n--- 5. flatMap (Optional 평탄화) ---");

        Optional<String> maybeEmailAddress = Optional.ofNullable(userWithEmail)
                .flatMap(User::getEmailAddressOptional);
        maybeEmailAddress.ifPresent(email -> System.out.println("Email 주소: " + email));


        // 6. filter: Optional 내부의 값에 조건 적용
        System.out.println("\n--- 6. filter (조건 적용) ---");

        Optional<User> verifiedUser = Optional.ofNullable(userWithEmail)
                .filter(User::isVerified);
        verifiedUser.ifPresentOrElse(
                user -> System.out.println("검증된 User: " + user.getName()),
                () -> System.out.println("검증되지 않았거나 User 없음")
        );
    }

}
