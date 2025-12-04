package org.maximum0.pattern.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * User 클래스: 불변 객체(Immutable Object)로 설계되었습니다.
 * 객체 생성은 내부 Builder 클래스를 통해 수행됩니다.
 */
public class User {
    private int id;
    private String name;
    private String emailAddress;
    private boolean isVerified;
    private List<Integer> friendUserIds;

    public User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.emailAddress = builder.emailAddress;
        this.isVerified = builder.isVerified;
        this.friendUserIds = builder.friendUserIds;
    }

    public static Builder builder(int id, String name) {
        return new Builder(id, name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public List<Integer> getFriendUserIds() {
        return friendUserIds;
    }

    /**
     * User 객체를 생성하는 내부 Builder 클래스.
     * 람다식(Consumer)을 활용하여 선택적 필드 설정을 유연하게 제공합니다.
     */
    public static class Builder {
        private int id;
        private String name;
        public String emailAddress;
        public boolean isVerified;
        public List<Integer> friendUserIds = new ArrayList<>();

        private Builder(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public User build() {
            return new User(this);
        }

        /**
         * 함수형 빌더의 핵심 메서드.
         * 빌더 객체 자신을 인수로 받는 Consumer 람다식을 통해 선택적 필드를 일괄 설정합니다.
         * 이를 통해 전통적인 setter 방식 없이도 필드 설정의 유연성을 확보합니다.
         *
         * @param consumer 빌더 객체에 대한 설정을 담은 Consumer<Builder> 람다식
         * @return 메서드 체이닝(Fluent Interface)을 위한 현재 Builder 인스턴스
         */
        public Builder with(Consumer<Builder> consumer) {
            consumer.accept(this); // 람다식을 실행하여 빌더 상태를 변경
            return this;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", isVerified=" + isVerified +
                ", friendUserIds=" + friendUserIds +
                '}';
    }
}
