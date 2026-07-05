package com.classora.prices.infrastructure.userinterface.http.controllers;

import com.classora.prices.application.bus.QueryBus;
import com.classora.prices.application.query.FindApplicablePriceQuery;
import com.classora.prices.application.query.dto.FindApplicablePriceResult;
import com.classora.prices.application.validation.Validator;
import com.classora.prices.infrastructure.mapper.PriceQueryMapper;
import com.classora.prices.infrastructure.mapper.PriceRestMapper;
import com.classora.prices.infrastructure.userinterface.http.dto.FindApplicablePriceRequest;
import com.classora.prices.infrastructure.userinterface.http.dto.FindApplicablePriceResponse;
import com.classora.prices.infrastructure.userinterface.http.routes.Routes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.PRICES)
@Tag(name = "Prices", description = "Query the price applicable to a product of a brand at a given date")
public class FindApplicablePriceController {

    private final Validator<FindApplicablePriceRequest> requestValidator;
    private final PriceQueryMapper priceQueryMapper;
    private final QueryBus queryBus;
    private final PriceRestMapper priceRestMapper;

    public FindApplicablePriceController(Validator<FindApplicablePriceRequest> requestValidator,
                                         PriceQueryMapper priceQueryMapper,
                                         QueryBus queryBus,
                                         PriceRestMapper priceRestMapper) {
        this.requestValidator = requestValidator;
        this.priceQueryMapper = priceQueryMapper;
        this.queryBus = queryBus;
        this.priceRestMapper = priceRestMapper;
    }

    @Operation(summary = "Find the applicable price",
            description = "Returns the tariff that applies to the product of the brand at the given date, "
                    + "resolving overlapping tariffs by highest priority.")
    @GetMapping
    public FindApplicablePriceResponse findApplicablePrice(FindApplicablePriceRequest request) {
        requestValidator.validate(request);
        FindApplicablePriceQuery query = priceQueryMapper.toQuery(request);
        FindApplicablePriceResult result = queryBus.handle(query);
        return priceRestMapper.toResponse(result);
    }
}
