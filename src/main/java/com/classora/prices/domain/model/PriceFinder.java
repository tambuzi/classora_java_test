package com.classora.prices.domain.model;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceFinder {

    List<Price> findApplicableCandidates(BrandId brandId, ProductId productId, LocalDateTime applicationDate);
}
