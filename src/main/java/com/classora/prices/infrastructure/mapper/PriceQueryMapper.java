package com.classora.prices.infrastructure.mapper;

import com.classora.prices.application.query.FindApplicablePriceQuery;
import com.classora.prices.infrastructure.userinterface.http.dto.FindApplicablePriceRequest;
import org.springframework.stereotype.Component;

@Component
public class PriceQueryMapper {

    public FindApplicablePriceQuery toQuery(FindApplicablePriceRequest request) {
        return new FindApplicablePriceQuery(
                request.applicationDate(),
                request.productId(),
                request.brandId());
    }
}
