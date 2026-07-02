package com.classora.prices.unit.application.query;

import com.classora.prices.application.query.FindApplicablePriceQuery;
import com.classora.prices.application.query.FindApplicablePriceQueryHandler;
import com.classora.prices.application.query.dto.FindApplicablePriceResult;
import com.classora.prices.domain.service.FindApplicablePriceService;
import com.classora.prices.domain.valueobject.BrandId;
import com.classora.prices.domain.valueobject.ProductId;
import com.classora.prices.unit.testsupport.PriceMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindApplicablePriceQueryHandlerTest {

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

    @Mock
    private FindApplicablePriceService findApplicablePriceService;

    @Test
    void shouldReturnTheApplicablePriceAsResponse() {
        when(findApplicablePriceService.execute(new BrandId(1L), new ProductId(35455L), APPLICATION_DATE))
                .thenReturn(PriceMother.defaultPrice());

        FindApplicablePriceResult result = new FindApplicablePriceQueryHandler(findApplicablePriceService)
                .handle(new FindApplicablePriceQuery(APPLICATION_DATE, 35455L, 1L));

        assertThat(result.productId()).isEqualTo(35455L);
        assertThat(result.brandId()).isEqualTo(1L);
        assertThat(result.priceList()).isEqualTo(1L);
        assertThat(result.price()).isEqualByComparingTo("35.50");
        assertThat(result.currency()).isEqualTo("EUR");
    }

    @Test
    void shouldRejectNullService() {
        assertThatNullPointerException().isThrownBy(() -> new FindApplicablePriceQueryHandler(null));
    }
}
