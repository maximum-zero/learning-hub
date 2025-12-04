package org.maximum0.pattern.service;

import org.maximum0.pattern.model.Price;

public class TaxPriceProcessor implements PriceProcessor {

    @Override
    public Price process(Price price) {
        return new Price(price.getPrice() + ", then applied tax");
    }

}
