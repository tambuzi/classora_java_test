package com.classora.prices.domain.model;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;

import java.util.List;

public interface PriceFinder {

    List<Price> findPricesFor(BrandId brandId, ProductId productId);
}
