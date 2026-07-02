package com.classora.prices.domain.valueobject;

public record PriceList(Long value) {

    public PriceList {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Price list id must be a positive number");
        }
    }
}
