package com.classora.prices.domain.valueobject;

import java.time.LocalDateTime;

public record DateRange(LocalDateTime start, LocalDateTime end) {

    public DateRange {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Date range boundaries must not be null");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Date range end must not be before start");
        }
    }

    public boolean contains(LocalDateTime date) {
        return !date.isBefore(start) && !date.isAfter(end);
    }
}
