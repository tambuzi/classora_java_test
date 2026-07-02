package com.classora.prices.domain.entity;

import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.DateRange;
import com.classora.prices.domain.valueobject.Money;
import com.classora.prices.domain.valueobject.PriceId;
import com.classora.prices.domain.valueobject.PriceList;
import com.classora.prices.domain.valueobject.Priority;
import com.classora.prices.domain.valueobject.ProductId;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Price {

    private final PriceId uuid;
    private final BrandId brandId;
    private final ProductId productId;
    private final PriceList priceList;
    private final Priority priority;
    private final DateRange validity;
    private final Money amount;

    private Price(PriceId uuid, BrandId brandId, ProductId productId, PriceList priceList,
                  Priority priority, DateRange validity, Money amount) {
        this.uuid = requireNonNull(uuid, "uuid");
        this.brandId = requireNonNull(brandId, "brandId");
        this.productId = requireNonNull(productId, "productId");
        this.priceList = requireNonNull(priceList, "priceList");
        this.priority = requireNonNull(priority, "priority");
        this.validity = requireNonNull(validity, "validity");
        this.amount = requireNonNull(amount, "amount");
    }

    public static Price of(PriceId uuid, BrandId brandId, ProductId productId, PriceList priceList,
                           Priority priority, DateRange validity, Money amount) {
        return new Price(uuid, brandId, productId, priceList, priority, validity, amount);
    }

    public boolean appliesAt(LocalDateTime date) {
        return validity.contains(date);
    }

    public boolean hasHigherPriorityThan(Price other) {
        return priority.isHigherThan(other.priority);
    }

    public PriceId uuid() {
        return uuid;
    }

    public BrandId brandId() {
        return brandId;
    }

    public ProductId productId() {
        return productId;
    }

    public PriceList priceList() {
        return priceList;
    }

    public Priority priority() {
        return priority;
    }

    public DateRange validity() {
        return validity;
    }

    public Money amount() {
        return amount;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Price price)) {
            return false;
        }
        return uuid.equals(price.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    private static <T> T requireNonNull(T value, String field) {
        return Objects.requireNonNull(value, field + " must not be null");
    }
}
