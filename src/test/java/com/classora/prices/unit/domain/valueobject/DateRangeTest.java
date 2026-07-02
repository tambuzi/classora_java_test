package com.classora.prices.unit.domain.valueobject;

import com.classora.prices.domain.valueobject.DateRange;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class DateRangeTest {

    private static final LocalDateTime START = LocalDateTime.of(2020, 6, 14, 0, 0, 0);
    private static final LocalDateTime END = LocalDateTime.of(2020, 6, 14, 18, 30, 0);

    @Test
    void shouldContainDatesWithinBoundariesInclusive() {
        DateRange range = new DateRange(START, END);

        assertThat(range.contains(START)).isTrue();
        assertThat(range.contains(END)).isTrue();
        assertThat(range.contains(START.plusHours(10))).isTrue();
    }

    @Test
    void shouldNotContainDatesOutsideBoundaries() {
        DateRange range = new DateRange(START, END);

        assertThat(range.contains(START.minusSeconds(1))).isFalse();
        assertThat(range.contains(END.plusSeconds(1))).isFalse();
    }

    @Test
    void shouldRejectNullStart() {
        assertThatIllegalArgumentException().isThrownBy(() -> new DateRange(null, END));
    }

    @Test
    void shouldRejectNullEnd() {
        assertThatIllegalArgumentException().isThrownBy(() -> new DateRange(START, null));
    }

    @Test
    void shouldRejectEndBeforeStart() {
        assertThatIllegalArgumentException().isThrownBy(() -> new DateRange(END, START));
    }
}
