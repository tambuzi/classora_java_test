package com.classora.prices.infrastructure.mapper;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.DateRange;
import com.classora.prices.domain.valueobject.Money;
import com.classora.prices.domain.valueobject.PriceId;
import com.classora.prices.domain.valueobject.PriceList;
import com.classora.prices.domain.valueobject.Priority;
import com.classora.prices.domain.valueobject.ProductId;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class PriceRowMapper implements RowMapper<Price> {

    @Override
    public Price mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Price.of(
                PriceId.of(rs.getObject("uuid", UUID.class)),
                new BrandId(rs.getLong("brand_id")),
                new ProductId(rs.getLong("product_id")),
                new PriceList(rs.getLong("price_list")),
                new Priority(rs.getInt("priority")),
                new DateRange(
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime()),
                Money.of(rs.getBigDecimal("price"), rs.getString("curr")));
    }
}
