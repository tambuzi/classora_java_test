package com.classora.prices.unit.domain.entity;

import com.classora.prices.domain.entity.Price;
import com.classora.prices.domain.valueobject.PriceId;
import com.classora.prices.unit.testsupport.PriceMother;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class PriceTest {

    @Test
    void shouldExposeItsAttributes() {
        Price price = PriceMother.defaultPrice();

        assertThat(price.uuid()).isEqualTo(PriceMother.PRICE_ID);
        assertThat(price.brandId().value()).isEqualTo(1L);
        assertThat(price.productId().value()).isEqualTo(35455L);
        assertThat(price.priceList().value()).isEqualTo(1L);
        assertThat(price.priority().value()).isZero();
        assertThat(price.validity().start()).isEqualTo(PriceMother.START);
        assertThat(price.validity().end()).isEqualTo(PriceMother.END);
        assertThat(price.amount().amount()).isEqualByComparingTo("35.50");
    }

    @Test
    void shouldApplyAtDatesWithinItsValidity() {
        Price price = PriceMother.defaultPrice();

        assertThat(price.appliesAt(PriceMother.START.plusHours(10))).isTrue();
        assertThat(price.appliesAt(PriceMother.START.minusSeconds(1))).isFalse();
    }

    @Test
    void shouldCompareItsPriorityAgainstAnotherPrice() {
        assertThat(PriceMother.withPriority(1).hasHigherPriorityThan(PriceMother.withPriority(0))).isTrue();
        assertThat(PriceMother.withPriority(0).hasHigherPriorityThan(PriceMother.withPriority(1))).isFalse();
    }

    @Test
    void shouldBeEqualToAnotherPriceWithTheSameId() {
        Price price = PriceMother.withId(PriceMother.PRICE_ID);
        Price sameId = PriceMother.withId(PriceMother.PRICE_ID);
        Price otherId = PriceMother.withId(PriceId.of(UUID.fromString("525f4ab0-bd0d-4d16-bae5-37d3bc23f477")));

        assertThat(price.equals(price)).isTrue();
        assertThat(price).isEqualTo(sameId).hasSameHashCodeAs(sameId);
        assertThat(price.hashCode()).isEqualTo(PriceMother.PRICE_ID.hashCode());
        assertThat(price).isNotEqualTo(otherId);
        assertThat(price.equals(null)).isFalse();
        assertThat(price.equals("not a price")).isFalse();
    }

    @Test
    void shouldRejectNullAttributes() {
        Price reference = PriceMother.defaultPrice();

        assertThatNullPointerException().isThrownBy(() -> Price.of(null, reference.brandId(), reference.productId(),
                reference.priceList(), reference.priority(), reference.validity(), reference.amount()));
        assertThatNullPointerException().isThrownBy(() -> Price.of(reference.uuid(), null, reference.productId(),
                reference.priceList(), reference.priority(), reference.validity(), reference.amount()));
        assertThatNullPointerException().isThrownBy(() -> Price.of(reference.uuid(), reference.brandId(), null,
                reference.priceList(), reference.priority(), reference.validity(), reference.amount()));
        assertThatNullPointerException().isThrownBy(() -> Price.of(reference.uuid(), reference.brandId(), reference.productId(),
                null, reference.priority(), reference.validity(), reference.amount()));
        assertThatNullPointerException().isThrownBy(() -> Price.of(reference.uuid(), reference.brandId(), reference.productId(),
                reference.priceList(), null, reference.validity(), reference.amount()));
        assertThatNullPointerException().isThrownBy(() -> Price.of(reference.uuid(), reference.brandId(), reference.productId(),
                reference.priceList(), reference.priority(), null, reference.amount()));
        assertThatNullPointerException().isThrownBy(() -> Price.of(reference.uuid(), reference.brandId(), reference.productId(),
                reference.priceList(), reference.priority(), reference.validity(), null));
    }
}
