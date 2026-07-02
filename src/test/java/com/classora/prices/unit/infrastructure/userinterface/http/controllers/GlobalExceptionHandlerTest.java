package com.classora.prices.unit.infrastructure.userinterface.http.controllers;

import com.classora.prices.application.exception.InvalidQueryException;
import com.classora.prices.domain.exception.PriceNotFoundException;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;
import com.classora.prices.infrastructure.userinterface.http.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldMapPriceNotFoundTo404() {
        PriceNotFoundException exception = new PriceNotFoundException(
                new BrandId(1L), new ProductId(35455L), LocalDateTime.of(2020, 6, 14, 10, 0, 0));

        ProblemDetail problem = handler.handlePriceNotFound(exception);

        assertThat(problem.getStatus()).isEqualTo(404);
        assertThat(problem.getDetail()).contains("35455");
    }

    @Test
    void shouldMapMalformedRequestTo400() {
        MissingServletRequestParameterException exception =
                new MissingServletRequestParameterException("brandId", "Long");

        ProblemDetail problem = handler.handleMalformedRequest(exception);

        assertThat(problem.getStatus()).isEqualTo(400);
        assertThat(problem.getDetail()).contains("brandId");
    }

    @Test
    void shouldMapInvalidQueryTo400() {
        ProblemDetail problem = handler.handleInvalidQuery(new InvalidQueryException("Brand id must be a positive number"));

        assertThat(problem.getStatus()).isEqualTo(400);
        assertThat(problem.getDetail()).isEqualTo("Brand id must be a positive number");
    }
}
