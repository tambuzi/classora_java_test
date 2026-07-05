package com.classora.prices.application.query.validation;

import com.classora.prices.application.exception.InvalidQueryException;
import com.classora.prices.application.query.FindApplicablePriceQuery;
import com.classora.prices.application.validation.QueryValidator;

public class FindApplicablePriceQueryValidator implements QueryValidator<FindApplicablePriceQuery> {

    @Override
    public void validate(FindApplicablePriceQuery query) {
        if (query.applicationDate() == null) {
            throw new InvalidQueryException("Application date must not be null");
        }
        if (query.productId() == null) {
            throw new InvalidQueryException("Product id must not be null");
        }
        if (query.productId() <= 0) {
            throw new InvalidQueryException("Product id must be a positive number");
        }
        if (query.brandId() == null) {
            throw new InvalidQueryException("Brand id must not be null");
        }
        if (query.brandId() <= 0) {
            throw new InvalidQueryException("Brand id must be a positive number");
        }
    }

    @Override
    public Class<FindApplicablePriceQuery> queryType() {
        return FindApplicablePriceQuery.class;
    }
}
