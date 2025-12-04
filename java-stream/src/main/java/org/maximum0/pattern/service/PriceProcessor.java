package org.maximum0.pattern.service;

import org.maximum0.pattern.model.Price;

/**
 * 가격 처리 로직을 위한 함수형 인터페이스.
 */
@FunctionalInterface
public interface PriceProcessor {
    Price process(Price price);

    /**
     * 함수형 데코레이터 패턴 구현 핵심
     * 현재 프로세스(process) 이후에 다음 프로세스(next)를 순차적으로 연결합니다.
     *
     * @param next 다음으로 적용할 PriceProcessor (데코레이터)
     * @return 현재 프로세스 결과에 next 프로세스를 적용하는 새로운 PriceProcessor (함수 합성)
     */
    default PriceProcessor andThen(PriceProcessor next) {
        // 클로저를 활용하여 next 프로세스를 기억하고 있다가,
        // 최종적으로 price -> next.process(process(price))를 실행하여 파이프라인을 구축합니다.
        return price -> next.process(process(price));
    }

}