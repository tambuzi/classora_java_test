package com.classora.prices.infrastructure.persistence;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.model.PriceFinder;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;
import com.classora.prices.infrastructure.mapper.PriceRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PriceDatabaseFinder implements PriceFinder {

    private static final String FIND_PRICES_FOR = """
            SELECT uuid, brand_id, start_date, end_date, price_list, product_id, priority, price, curr
            FROM prices
            WHERE brand_id = :brandId
              AND product_id = :productId
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PriceRowMapper priceRowMapper;

    public PriceDatabaseFinder(NamedParameterJdbcTemplate jdbcTemplate, PriceRowMapper priceRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.priceRowMapper = priceRowMapper;
    }

    @Override
    public List<Price> findPricesFor(BrandId brandId, ProductId productId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("brandId", brandId.value())
                .addValue("productId", productId.value());
        return jdbcTemplate.query(FIND_PRICES_FOR, parameters, priceRowMapper);
    }
}
