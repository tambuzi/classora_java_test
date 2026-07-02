package com.classora.prices.unit.application.query.dto;

import com.classora.prices.application.query.dto.FindApplicablePriceResult;
import com.classora.prices.unit.testsupport.PriceMother;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindApplicablePriceResultTest {

    @Test
    void shouldBeBuiltFromADomainPrice() {
        FindApplicablePriceResult result = FindApplicablePriceResult.from(PriceMother.defaultPrice());

        assertThat(result.productId()).isEqualTo(35455L);
        assertThat(result.brandId()).isEqualTo(1L);
        assertThat(result.priceList()).isEqualTo(1L);
        assertThat(result.startDate()).isEqualTo(PriceMother.START);
        assertThat(result.endDate()).isEqualTo(PriceMother.END);
        assertThat(result.price()).isEqualByComparingTo("35.50");
        assertThat(result.currency()).isEqualTo("EUR");
    }
}
