package com.classora.prices.unit.application.query;

import com.classora.prices.application.exception.InvalidQueryException;
import com.classora.prices.application.query.FindApplicablePriceQuery;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FindApplicablePriceQueryTest {

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

    @Test
    void shouldExposeItsValues() {
        FindApplicablePriceQuery query = new FindApplicablePriceQuery(APPLICATION_DATE, 35455L, 1L);

        assertThat(query.applicationDate()).isEqualTo(APPLICATION_DATE);
        assertThat(query.productId()).isEqualTo(35455L);
        assertThat(query.brandId()).isEqualTo(1L);
    }

    @Test
    void shouldRejectNullApplicationDate() {
        assertThatThrownBy(() -> new FindApplicablePriceQuery(null, 35455L, 1L))
                .isInstanceOf(InvalidQueryException.class);
    }

    @Test
    void shouldRejectNullProductId() {
        assertThatThrownBy(() -> new FindApplicablePriceQuery(APPLICATION_DATE, null, 1L))
                .isInstanceOf(InvalidQueryException.class);
    }

    @Test
    void shouldRejectNonPositiveProductId() {
        assertThatThrownBy(() -> new FindApplicablePriceQuery(APPLICATION_DATE, 0L, 1L))
                .isInstanceOf(InvalidQueryException.class);
    }

    @Test
    void shouldRejectNullBrandId() {
        assertThatThrownBy(() -> new FindApplicablePriceQuery(APPLICATION_DATE, 35455L, null))
                .isInstanceOf(InvalidQueryException.class);
    }

    @Test
    void shouldRejectNonPositiveBrandId() {
        assertThatThrownBy(() -> new FindApplicablePriceQuery(APPLICATION_DATE, 35455L, -1L))
                .isInstanceOf(InvalidQueryException.class);
    }
}
