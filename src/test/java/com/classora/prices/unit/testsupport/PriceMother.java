package com.classora.prices.unit.testsupport;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.DateRange;
import com.classora.prices.domain.valueobject.Money;
import com.classora.prices.domain.valueobject.PriceId;
import com.classora.prices.domain.valueobject.PriceList;
import com.classora.prices.domain.valueobject.Priority;
import com.classora.prices.domain.valueobject.ProductId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class PriceMother {

    public static final LocalDateTime START = LocalDateTime.of(2020, 6, 14, 0, 0, 0);
    public static final LocalDateTime END = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
    public static final PriceId PRICE_ID = PriceId.of(UUID.fromString("6e43a823-48f2-4718-9c5d-5553641de911"));

    private PriceMother() {
    }

    public static Price defaultPrice() {
        return withPriority(0);
    }

    public static Price withPriority(int priority) {
        return build(PRICE_ID, priority);
    }

    public static Price withId(PriceId uuid) {
        return build(uuid, 0);
    }

    private static Price build(PriceId uuid, int priority) {
        return Price.of(
                uuid,
                new BrandId(1L),
                new ProductId(35455L),
                new PriceList(1L),
                new Priority(priority),
                new DateRange(START, END),
                Money.of(new BigDecimal("35.50"), "EUR"));
    }
}
