package org.maximum0.pattern.service;

import org.maximum0.pattern.model.Price;

public class BasicPriceProcessor implements PriceProcessor {

    @Override
    public Price process(Price price) {
        return price;
    }

}
