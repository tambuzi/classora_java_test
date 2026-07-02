package com.classora.prices.unit.domain.valueobject;

import com.classora.prices.domain.valueobject.Money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MoneyTest {

    @Test
    void shouldCreateFromAmountAndCurrencyCode() {
        Money money = Money.of(new BigDecimal("35.50"), "EUR");

        assertThat(money.amount()).isEqualByComparingTo("35.50");
        assertThat(money.currency()).isEqualTo(Currency.getInstance("EUR"));
    }

    @Test
    void shouldAllowZeroAmount() {
        assertThat(Money.of(BigDecimal.ZERO, "EUR").amount()).isEqualByComparingTo("0");
    }

    @Test
    void shouldRejectNullAmount() {
        assertThatIllegalArgumentException().isThrownBy(() -> Money.of(null, "EUR"));
    }

    @Test
    void shouldRejectNegativeAmount() {
        assertThatIllegalArgumentException().isThrownBy(() -> Money.of(new BigDecimal("-0.01"), "EUR"));
    }

    @Test
    void shouldRejectNullCurrencyCode() {
        assertThatIllegalArgumentException().isThrownBy(() -> Money.of(BigDecimal.ONE, null));
    }

    @Test
    void shouldRejectNullCurrency() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Money(BigDecimal.ONE, null));
    }

    @Test
    void shouldRejectUnknownCurrencyCode() {
        assertThatIllegalArgumentException().isThrownBy(() -> Money.of(BigDecimal.ONE, "NOPE"));
    }
}
