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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({PriceDatabaseFinder.class, PriceRowMapper.class})
class PriceDatabaseFinderJdbcTest {

    @Autowired
    private PriceDatabaseFinder priceDatabaseFinder;

    @Test
    void shouldReturnEverySeededTariffForTheBrandAndProduct() {
        List<Price> prices = priceDatabaseFinder.findPricesFor(new BrandId(1L), new ProductId(35455L));

        assertThat(prices).hasSize(4);
        assertThat(prices).allSatisfy(price -> {
            assertThat(price.brandId().value()).isEqualTo(1L);
            assertThat(price.productId().value()).isEqualTo(35455L);
        });
        assertThat(prices).extracting(price -> price.priceList().value())
                .containsExactlyInAnyOrder(1L, 2L, 3L, 4L);
    }

    @Test
    void shouldReturnEmptyForAnUnknownProduct() {
        List<Price> prices = priceDatabaseFinder.findPricesFor(new BrandId(1L), new ProductId(99999L));

        assertThat(prices).isEmpty();
    }
}
