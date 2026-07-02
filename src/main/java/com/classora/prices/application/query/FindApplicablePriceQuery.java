package com.classora.prices.application.query;

import com.classora.prices.application.exception.InvalidQueryException;

import java.time.LocalDateTime;

public record FindApplicablePriceQuery(LocalDateTime applicationDate, Long productId, Long brandId) {

    public FindApplicablePriceQuery {
        if (applicationDate == null) {
            throw new InvalidQueryException("Application date must not be null");
        }
        if (productId == null) {
            throw new InvalidQueryException("Product id must not be null");
        }
        if (productId <= 0) {
            throw new InvalidQueryException("Product id must be a positive number");
        }
        if (brandId == null) {
            throw new InvalidQueryException("Brand id must not be null");
        }
        if (brandId <= 0) {
            throw new InvalidQueryException("Brand id must be a positive number");
        }
    }
}
