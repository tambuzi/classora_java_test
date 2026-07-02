package com.classora.prices.infrastructure.userinterface.http.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record FindApplicablePriceRequest(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
        Long productId,
        Long brandId) {
}
