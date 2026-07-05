package com.classora.prices.unit.application.query.validation;

import com.classora.prices.application.exception.InvalidQueryException;
import com.classora.prices.application.query.FindApplicablePriceQuery;
import com.classora.prices.application.query.validation.FindApplicablePriceQueryValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FindApplicablePriceQueryValidatorTest {

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

    private final FindApplicablePriceQueryValidator validator = new FindApplicablePriceQueryValidator();

    @Test
    void shouldAcceptAValidQuery() {
        assertThatNoException().isThrownBy(() ->
                validator.validate(new FindApplicablePriceQuery(APPLICATION_DATE, 35455L, 1L)));
    }

    @Test
    void shouldRejectNullApplicationDate() {
        assertThatThrownBy(() -> validator.validate(new FindApplicablePriceQuery(null, 35455L, 1L)))
                .isInstanceOf(InvalidQueryException.class);
    }

    @Test
    void shouldRejectNullProductId() {
        assertThatThrownBy(() -> validator.validate(new FindApplicablePriceQuery(APPLICATION_DATE, null, 1L)))
                .isInstanceOf(InvalidQueryException.class);
    }

    @Test
    void shouldRejectNonPositiveProductId() {
        assertThatThrownBy(() -> validator.validate(new FindApplicablePriceQuery(APPLICATION_DATE, 0L, 1L)))
                .isInstanceOf(InvalidQueryException.class);
    }

    @Test
    void shouldRejectNullBrandId() {
        assertThatThrownBy(() -> validator.validate(new FindApplicablePriceQuery(APPLICATION_DATE, 35455L, null)))
                .isInstanceOf(InvalidQueryException.class);
    }

    @Test
    void shouldRejectNonPositiveBrandId() {
        assertThatThrownBy(() -> validator.validate(new FindApplicablePriceQuery(APPLICATION_DATE, 35455L, 0L)))
                .isInstanceOf(InvalidQueryException.class);
    }

    @Test
    void shouldExposeTheQueryTypeItValidates() {
        assertThat(validator.queryType()).isEqualTo(FindApplicablePriceQuery.class);
    }
}
