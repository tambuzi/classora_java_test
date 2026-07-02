package com.classora.prices.domain.valueobject;

public record BrandId(Long value) {

    public BrandId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Brand id must be a positive number");
        }
    }
}
