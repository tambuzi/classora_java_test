package com.classora.prices.unit.infrastructure.mapper;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.infrastructure.mapper.PriceRowMapper;
import com.classora.prices.unit.testsupport.PriceMother;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceRowMapperTest {

    @Test
    void shouldMapResultSetRowToDomainPrice() throws SQLException {
        UUID uuid = PriceMother.PRICE_ID.value();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getObject("uuid", UUID.class)).thenReturn(uuid);
        when(resultSet.getLong("brand_id")).thenReturn(1L);
        when(resultSet.getLong("product_id")).thenReturn(35455L);
        when(resultSet.getLong("price_list")).thenReturn(1L);
        when(resultSet.getInt("priority")).thenReturn(0);
        when(resultSet.getTimestamp("start_date")).thenReturn(Timestamp.valueOf(PriceMother.START));
        when(resultSet.getTimestamp("end_date")).thenReturn(Timestamp.valueOf(PriceMother.END));
        when(resultSet.getBigDecimal("price")).thenReturn(new BigDecimal("35.50"));
        when(resultSet.getString("curr")).thenReturn("EUR");

        Price price = new PriceRowMapper().mapRow(resultSet, 1);

        assertThat(price.uuid().value()).isEqualTo(uuid);
        assertThat(price.brandId().value()).isEqualTo(1L);
        assertThat(price.productId().value()).isEqualTo(35455L);
        assertThat(price.priceList().value()).isEqualTo(1L);
        assertThat(price.priority().value()).isZero();
        assertThat(price.validity().start()).isEqualTo(PriceMother.START);
        assertThat(price.validity().end()).isEqualTo(PriceMother.END);
        assertThat(price.amount().amount()).isEqualByComparingTo("35.50");
        assertThat(price.amount().currency().getCurrencyCode()).isEqualTo("EUR");
    }
}
