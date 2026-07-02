package com.classora.prices.domain.valueobject;

public record Priority(int value) implements Comparable<Priority> {

    public Priority {
        if (value < 0) {
            throw new IllegalArgumentException("Priority must not be negative");
        }
    }

    @Override
    public int compareTo(Priority other) {
        return Integer.compare(value, other.value);
    }

    public boolean isHigherThan(Priority other) {
        return compareTo(other) > 0;
    }
}
