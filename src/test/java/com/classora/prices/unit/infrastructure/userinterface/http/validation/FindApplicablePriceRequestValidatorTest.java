package com.classora.prices.unit.infrastructure.userinterface.http.validation;

import com.classora.prices.infrastructure.userinterface.http.dto.FindApplicablePriceRequest;
import com.classora.prices.infrastructure.userinterface.http.exception.InvalidRequestException;
import com.classora.prices.infrastructure.userinterface.http.validation.FindApplicablePriceRequestValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FindApplicablePriceRequestValidatorTest {

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

    private final FindApplicablePriceRequestValidator validator = new FindApplicablePriceRequestValidator();

    @Test
    void shouldAcceptAValidRequest() {
        assertThatNoException().isThrownBy(() ->
                validator.validate(new FindApplicablePriceRequest(APPLICATION_DATE, 35455L, 1L)));
    }

    @Test
    void shouldRejectNullApplicationDate() {
        assertThatThrownBy(() -> validator.validate(new FindApplicablePriceRequest(null, 35455L, 1L)))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void shouldRejectNullProductId() {
        assertThatThrownBy(() -> validator.validate(new FindApplicablePriceRequest(APPLICATION_DATE, null, 1L)))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void shouldRejectNonPositiveProductId() {
        assertThatThrownBy(() -> validator.validate(new FindApplicablePriceRequest(APPLICATION_DATE, 0L, 1L)))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void shouldRejectNullBrandId() {
        assertThatThrownBy(() -> validator.validate(new FindApplicablePriceRequest(APPLICATION_DATE, 35455L, null)))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void shouldRejectNonPositiveBrandId() {
        assertThatThrownBy(() -> validator.validate(new FindApplicablePriceRequest(APPLICATION_DATE, 35455L, 0L)))
                .isInstanceOf(InvalidRequestException.class);
    }
}
