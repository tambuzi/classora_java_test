package com.classora.prices.infrastructure.persistence;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.model.PriceFinder;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;
import com.classora.prices.infrastructure.mapper.PriceRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PriceDatabaseFinder implements PriceFinder {

    private static final String FIND_APPLICABLE_CANDIDATES = """
            SELECT uuid, brand_id, start_date, end_date, price_list, product_id, priority, price, curr
            FROM prices
            WHERE brand_id = :brandId
              AND product_id = :productId
              AND start_date <= :applicationDate
              AND end_date >= :applicationDate
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PriceRowMapper priceRowMapper;

    public PriceDatabaseFinder(NamedParameterJdbcTemplate jdbcTemplate, PriceRowMapper priceRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.priceRowMapper = priceRowMapper;
    }

    @Override
    public List<Price> findApplicableCandidates(BrandId brandId, ProductId productId, LocalDateTime applicationDate) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("brandId", brandId.value())
                .addValue("productId", productId.value())
                .addValue("applicationDate", applicationDate);
        return jdbcTemplate.query(FIND_APPLICABLE_CANDIDATES, parameters, priceRowMapper);
    }
}
