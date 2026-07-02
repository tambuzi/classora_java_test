package com.classora.prices.infrastructure.mapper;

import com.classora.prices.application.query.dto.FindApplicablePriceResult;
import com.classora.prices.infrastructure.userinterface.http.dto.FindApplicablePriceResponse;
import org.springframework.stereotype.Component;

@Component
public class PriceRestMapper {

    public FindApplicablePriceResponse toResponse(FindApplicablePriceResult result) {
        return new FindApplicablePriceResponse(
                result.productId(),
                result.brandId(),
                result.priceList(),
                result.startDate(),
                result.endDate(),
                result.price(),
                result.currency());
    }
}
