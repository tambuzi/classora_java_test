package com.classora.prices.unit.infrastructure.persistence;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;
import com.classora.prices.infrastructure.mapper.PriceRowMapper;
import com.classora.prices.infrastructure.persistence.PriceDatabaseFinder;
import com.classora.prices.unit.testsupport.PriceMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceDatabaseFinderTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private PriceRowMapper priceRowMapper;

    @Test
    void shouldReturnTheCandidateTariffsForTheBrandAndProduct() {
        List<Price> candidates = List.of(PriceMother.withPriority(0), PriceMother.withPriority(1));
        when(jdbcTemplate.query(anyString(), any(SqlParameterSource.class), eq(priceRowMapper)))
                .thenReturn(candidates);

        List<Price> result = new PriceDatabaseFinder(jdbcTemplate, priceRowMapper)
                .findPricesFor(new BrandId(1L), new ProductId(35455L));

        assertThat(result).isEqualTo(candidates);
    }
}
