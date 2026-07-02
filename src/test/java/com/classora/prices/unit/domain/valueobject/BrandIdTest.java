package com.classora.prices.unit.domain.valueobject;

import com.classora.prices.domain.valueobject.BrandId;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class BrandIdTest {

    @Test
    void shouldExposeItsValue() {
        assertThat(new BrandId(1L).value()).isEqualTo(1L);
    }

    @Test
    void shouldRejectNullValue() {
        assertThatIllegalArgumentException().isThrownBy(() -> new BrandId(null));
    }

    @Test
    void shouldRejectNonPositiveValue() {
        assertThatIllegalArgumentException().isThrownBy(() -> new BrandId(0L));
    }
}
