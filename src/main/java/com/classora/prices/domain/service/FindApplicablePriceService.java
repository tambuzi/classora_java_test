package com.classora.prices.domain.service;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.exception.PriceNotFoundException;
import com.classora.prices.domain.model.PriceFinder;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;

import java.time.LocalDateTime;
import java.util.Objects;

public class FindApplicablePriceService {

    private final PriceFinder priceFinder;

    public FindApplicablePriceService(PriceFinder priceFinder) {
        this.priceFinder = Objects.requireNonNull(priceFinder, "priceFinder must not be null");
    }

    public Price execute(BrandId brandId, ProductId productId, LocalDateTime applicationDate) {
        return priceFinder.findApplicableCandidates(brandId, productId, applicationDate).stream()
                .filter(price -> price.appliesAt(applicationDate))
                .reduce((current, candidate) -> candidate.hasHigherPriorityThan(current) ? candidate : current)
                .orElseThrow(() -> new PriceNotFoundException(brandId, productId, applicationDate));
    }
}
