package com.classora.prices.unit.infrastructure.mapper;

import com.classora.prices.application.query.dto.FindApplicablePriceResult;
import com.classora.prices.infrastructure.mapper.PriceRestMapper;
import com.classora.prices.infrastructure.userinterface.http.dto.FindApplicablePriceResponse;
import com.classora.prices.unit.testsupport.PriceMother;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceRestMapperTest {

    @Test
    void shouldMapResultToRestResponse() {
        FindApplicablePriceResult result = FindApplicablePriceResult.from(PriceMother.defaultPrice());

        FindApplicablePriceResponse response = new PriceRestMapper().toResponse(result);

        assertThat(response.productId()).isEqualTo(result.productId());
        assertThat(response.brandId()).isEqualTo(result.brandId());
        assertThat(response.priceList()).isEqualTo(result.priceList());
        assertThat(response.startDate()).isEqualTo(result.startDate());
        assertThat(response.endDate()).isEqualTo(result.endDate());
        assertThat(response.price()).isEqualByComparingTo(result.price());
        assertThat(response.currency()).isEqualTo(result.currency());
    }
}
