package com.classora.prices.application.query.dto;

import com.classora.prices.domain.entity.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FindApplicablePriceResult(
        Long productId,
        Long brandId,
        Long priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency) {

    public static FindApplicablePriceResult from(Price price) {
        return new FindApplicablePriceResult(
                price.productId().value(),
                price.brandId().value(),
                price.priceList().value(),
                price.validity().start(),
                price.validity().end(),
                price.amount().amount(),
                price.amount().currency().getCurrencyCode());
    }
}
