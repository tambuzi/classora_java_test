package com.classora.prices.unit.infrastructure.mapper;

import com.classora.prices.application.query.FindApplicablePriceQuery;
import com.classora.prices.infrastructure.mapper.PriceQueryMapper;
import com.classora.prices.infrastructure.userinterface.http.dto.FindApplicablePriceRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceQueryMapperTest {

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

    @Test
    void shouldMapRequestToQuery() {
        FindApplicablePriceRequest request = new FindApplicablePriceRequest(APPLICATION_DATE, 35455L, 1L);

        FindApplicablePriceQuery query = new PriceQueryMapper().toQuery(request);

        assertThat(query.applicationDate()).isEqualTo(APPLICATION_DATE);
        assertThat(query.productId()).isEqualTo(35455L);
        assertThat(query.brandId()).isEqualTo(1L);
    }
}
