package com.classora.prices.infrastructure.userinterface.http.validation;

import com.classora.prices.application.validation.Validator;
import com.classora.prices.infrastructure.userinterface.http.dto.FindApplicablePriceRequest;
import com.classora.prices.infrastructure.userinterface.http.exception.InvalidRequestException;
import org.springframework.stereotype.Component;

@Component
public class FindApplicablePriceRequestValidator implements Validator<FindApplicablePriceRequest> {

    @Override
    public void validate(FindApplicablePriceRequest request) {
        if (request.applicationDate() == null) {
            throw new InvalidRequestException("applicationDate is required");
        }
        if (request.productId() == null) {
            throw new InvalidRequestException("productId is required");
        }
        if (request.productId() <= 0) {
            throw new InvalidRequestException("productId must be a positive number");
        }
        if (request.brandId() == null) {
            throw new InvalidRequestException("brandId is required");
        }
        if (request.brandId() <= 0) {
            throw new InvalidRequestException("brandId must be a positive number");
        }
    }
}
