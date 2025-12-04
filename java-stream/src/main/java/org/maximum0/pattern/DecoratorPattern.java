package org.maximum0.pattern;

import org.maximum0.pattern.model.Price;
import org.maximum0.pattern.service.BasicPriceProcessor;
import org.maximum0.pattern.service.DiscountPriceProcessor;
import org.maximum0.pattern.service.PriceProcessor;
import org.maximum0.pattern.service.TaxPriceProcessor;

/**
 * 함수형 데코레이터 패턴 (Functional Decorator Pattern) 예제
 *
 * OOP의 데코레이터 패턴을 함수 합성(andThen)을 사용하여 간결하게 재구현합니다.
 */
public class DecoratorPattern {

    public static void main(String[] args) {
        Price unprocessedPrice = new Price("Original Price");

        PriceProcessor basicPriceProcessor = new BasicPriceProcessor();
        PriceProcessor discountPriceProcessor = new DiscountPriceProcessor();
        PriceProcessor taxPriceProcessor = new TaxPriceProcessor();

        // andThen() 메서드를 이용해 처리 로직을 파이프라인 형태로 순차적으로 합성(Chain)합니다.
        PriceProcessor decoratedPriceProcessor = basicPriceProcessor
                .andThen(discountPriceProcessor)
                .andThen(taxPriceProcessor)
                .andThen(price -> new Price(price.getPrice() + ", then apply another procedure"));

        // 최종 합성된 프로세서에 가격을 주입하면, 파이프라인을 따라 순차적으로 처리됩니다.
        Price processedPrice = decoratedPriceProcessor.process(unprocessedPrice);
        System.out.println(processedPrice.getPrice());
    }

}
