package com.classora.prices.domain.valueobject;

import java.util.UUID;

public record PriceId(UUID value) {

    public PriceId {
        if (value == null) {
            throw new IllegalArgumentException("Price id must not be null");
        }
    }

    public static PriceId of(UUID value) {
        return new PriceId(value);
    }
}
