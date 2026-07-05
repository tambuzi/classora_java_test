package com.classora.prices.unit.infrastructure.persistence;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;
import com.classora.prices.infrastructure.mapper.PriceRowMapper;
import com.classora.prices.infrastructure.persistence.PriceDatabaseFinder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({PriceDatabaseFinder.class, PriceRowMapper.class})
class PriceDatabaseFinderJdbcTest {

    @Autowired
    private PriceDatabaseFinder priceDatabaseFinder;

    @Test
    void shouldReturnOnlyTheTariffsApplicableAtTheDate() {
        List<Price> prices = priceDatabaseFinder.findApplicableCandidates(
                new BrandId(1L), new ProductId(35455L), LocalDateTime.of(2020, 6, 14, 16, 0, 0));

        assertThat(prices).extracting(price -> price.priceList().value())
                .containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    void shouldReturnEmptyWhenNoTariffAppliesAtTheDate() {
        List<Price> prices = priceDatabaseFinder.findApplicableCandidates(
                new BrandId(1L), new ProductId(35455L), LocalDateTime.of(2020, 6, 13, 10, 0, 0));

        assertThat(prices).isEmpty();
    }

    @Test
    void shouldReturnEmptyForAnUnknownProduct() {
        List<Price> prices = priceDatabaseFinder.findApplicableCandidates(
                new BrandId(1L), new ProductId(99999L), LocalDateTime.of(2020, 6, 14, 16, 0, 0));

        assertThat(prices).isEmpty();
    }
}
