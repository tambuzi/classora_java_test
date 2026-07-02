package com.classora.prices.domain.valueobject;

public record ProductId(Long value) {

    public ProductId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Product id must be a positive number");
        }
    }
}
