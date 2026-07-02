package com.classora.prices.unit.domain.valueobject;

import com.classora.prices.domain.valueobject.Priority;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PriorityTest {

    @Test
    void shouldExposeItsValue() {
        assertThat(new Priority(1).value()).isEqualTo(1);
    }

    @Test
    void shouldRejectNegativeValue() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Priority(-1));
    }

    @Test
    void shouldCompareByNumericValue() {
        assertThat(new Priority(1)).isGreaterThan(new Priority(0));
        assertThat(new Priority(0)).isLessThan(new Priority(1));
        assertThat(new Priority(1)).isEqualByComparingTo(new Priority(1));
    }

    @Test
    void shouldTellWhenItIsHigherThanAnother() {
        assertThat(new Priority(1).isHigherThan(new Priority(0))).isTrue();
        assertThat(new Priority(0).isHigherThan(new Priority(1))).isFalse();
        assertThat(new Priority(1).isHigherThan(new Priority(1))).isFalse();
    }
}
