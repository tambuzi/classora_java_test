package com.classora.prices.unit.application.query;

import com.classora.prices.application.query.FindApplicablePriceQuery;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FindApplicablePriceQueryTest {

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

    @Test
    void shouldExposeItsValues() {
        FindApplicablePriceQuery query = new FindApplicablePriceQuery(APPLICATION_DATE, 35455L, 1L);

        assertThat(query.applicationDate()).isEqualTo(APPLICATION_DATE);
        assertThat(query.productId()).isEqualTo(35455L);
        assertThat(query.brandId()).isEqualTo(1L);
    }
}
