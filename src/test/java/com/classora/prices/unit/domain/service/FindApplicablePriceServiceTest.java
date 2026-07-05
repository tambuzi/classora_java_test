package com.classora.prices.unit.domain.service;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.exception.PriceNotFoundException;
import com.classora.prices.domain.model.PriceFinder;
import com.classora.prices.domain.service.FindApplicablePriceService;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.DateRange;
import com.classora.prices.domain.valueobject.Money;
import com.classora.prices.domain.valueobject.PriceId;
import com.classora.prices.domain.valueobject.PriceList;
import com.classora.prices.domain.valueobject.Priority;
import com.classora.prices.domain.valueobject.ProductId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindApplicablePriceServiceTest {

    private static final BrandId BRAND_ID = new BrandId(1L);
    private static final ProductId PRODUCT_ID = new ProductId(35455L);
    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 16, 0, 0);

    @Mock
    private PriceFinder priceFinder;

    @Test
    void shouldSelectTheHighestPriorityAmongTheApplicableTariffs() {
        Price mid = tariff(1, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        Price low = tariff(0, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        Price high = tariff(2, LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30));
        Price notApplicable = tariff(9, LocalDateTime.of(2020, 6, 15, 0, 0), LocalDateTime.of(2020, 6, 15, 11, 0));
        when(priceFinder.findApplicableCandidates(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).thenReturn(List.of(mid, low, high, notApplicable));

        Price result = new FindApplicablePriceService(priceFinder).execute(BRAND_ID, PRODUCT_ID, APPLICATION_DATE);

        assertThat(result).isSameAs(high);
    }

    @Test
    void shouldReturnTheOnlyApplicableTariff() {
        Price only = tariff(0, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        when(priceFinder.findApplicableCandidates(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).thenReturn(List.of(only));

        Price result = new FindApplicablePriceService(priceFinder).execute(BRAND_ID, PRODUCT_ID, APPLICATION_DATE);

        assertThat(result).isSameAs(only);
    }

    @Test
    void shouldFailWhenNoTariffExistsForTheProduct() {
        when(priceFinder.findApplicableCandidates(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).thenReturn(List.of());

        assertThatThrownBy(() -> new FindApplicablePriceService(priceFinder)
                .execute(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("35455");
    }

    @Test
    void shouldFailWhenNoTariffAppliesAtTheRequestedDate() {
        Price future = tariff(0, LocalDateTime.of(2020, 6, 15, 0, 0), LocalDateTime.of(2020, 6, 15, 11, 0));
        when(priceFinder.findApplicableCandidates(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).thenReturn(List.of(future));

        assertThatThrownBy(() -> new FindApplicablePriceService(priceFinder)
                .execute(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                .isInstanceOf(PriceNotFoundException.class);
    }

    @Test
    void shouldRejectNullFinder() {
        assertThatNullPointerException().isThrownBy(() -> new FindApplicablePriceService(null));
    }

    private static Price tariff(int priority, LocalDateTime start, LocalDateTime end) {
        return Price.of(
                PriceId.of(UUID.randomUUID()),
                BRAND_ID,
                PRODUCT_ID,
                new PriceList(1L),
                new Priority(priority),
                new DateRange(start, end),
                Money.of(new BigDecimal("35.50"), "EUR"));
    }
}
