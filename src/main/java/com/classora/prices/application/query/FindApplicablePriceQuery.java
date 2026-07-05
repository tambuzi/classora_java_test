package com.classora.prices.application.query;

import com.classora.prices.application.bus.Query;
import com.classora.prices.application.query.dto.FindApplicablePriceResult;

import java.time.LocalDateTime;

public record FindApplicablePriceQuery(LocalDateTime applicationDate, Long productId, Long brandId)
        implements Query<FindApplicablePriceResult> {
}
