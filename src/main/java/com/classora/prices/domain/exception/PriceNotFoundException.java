package com.classora.prices.domain.exception;

import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(BrandId brandId, ProductId productId, LocalDateTime applicationDate) {
        super("No applicable price found for brand %d, product %d at %s"
                .formatted(brandId.value(), productId.value(), applicationDate));
    }
}
