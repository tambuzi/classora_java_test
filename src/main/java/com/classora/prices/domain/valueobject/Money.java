package com.classora.prices.domain.valueobject;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {

    public Money {
        if (amount == null) {
            throw new IllegalArgumentException("Money amount must not be null");
        }
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Money amount must not be negative");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Money currency must not be null");
        }
    }

    public static Money of(BigDecimal amount, String currencyCode) {
        if (currencyCode == null) {
            throw new IllegalArgumentException("Money currency must not be null");
        }
        return new Money(amount, Currency.getInstance(currencyCode));
    }
}
