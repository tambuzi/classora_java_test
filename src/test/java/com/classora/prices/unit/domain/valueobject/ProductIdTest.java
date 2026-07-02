package com.classora.prices.unit.domain.valueobject;

import com.classora.prices.domain.valueobject.ProductId;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ProductIdTest {

    @Test
    void shouldExposeItsValue() {
        assertThat(new ProductId(35455L).value()).isEqualTo(35455L);
    }

    @Test
    void shouldRejectNullValue() {
        assertThatIllegalArgumentException().isThrownBy(() -> new ProductId(null));
    }

    @Test
    void shouldRejectNonPositiveValue() {
        assertThatIllegalArgumentException().isThrownBy(() -> new ProductId(-1L));
    }
}
