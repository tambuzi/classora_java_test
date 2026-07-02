package com.classora.prices.unit.domain.valueobject;

import com.classora.prices.domain.valueobject.PriceId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PriceIdTest {

    @Test
    void shouldExposeItsValue() {
        UUID uuid = UUID.fromString("741dc360-24b0-46cc-98c7-6de0f078d271");

        assertThat(PriceId.of(uuid).value()).isEqualTo(uuid);
    }

    @Test
    void shouldRejectNullValue() {
        assertThatIllegalArgumentException().isThrownBy(() -> new PriceId(null));
    }
}
