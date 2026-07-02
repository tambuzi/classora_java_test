package com.classora.prices.application.query;

import com.classora.prices.application.query.dto.FindApplicablePriceResult;
import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.service.FindApplicablePriceService;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;

import java.util.Objects;

public class FindApplicablePriceQueryHandler {

    private final FindApplicablePriceService findApplicablePriceService;

    public FindApplicablePriceQueryHandler(FindApplicablePriceService findApplicablePriceService) {
        this.findApplicablePriceService = Objects.requireNonNull(findApplicablePriceService, "findApplicablePriceService must not be null");
    }

    public FindApplicablePriceResult handle(FindApplicablePriceQuery query) {
        Price price = findApplicablePriceService.execute(
                new BrandId(query.brandId()),
                new ProductId(query.productId()),
                query.applicationDate());
        return FindApplicablePriceResult.from(price);
    }
}
