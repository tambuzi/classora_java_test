package com.classora.prices.unit.domain.valueobject;

import com.classora.prices.domain.valueobject.PriceList;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PriceListTest {

    @Test
    void shouldExposeItsValue() {
        assertThat(new PriceList(2L).value()).isEqualTo(2L);
    }

    @Test
    void shouldRejectNullValue() {
        assertThatIllegalArgumentException().isThrownBy(() -> new PriceList(null));
    }

    @Test
    void shouldRejectNonPositiveValue() {
        assertThatIllegalArgumentException().isThrownBy(() -> new PriceList(0L));
    }
}
