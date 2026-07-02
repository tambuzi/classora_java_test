package com.classora.prices.infrastructure.config;

import com.classora.prices.application.query.FindApplicablePriceQueryHandler;
import com.classora.prices.domain.service.FindApplicablePriceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryHandlerBeanConfiguration {

    @Bean
    public FindApplicablePriceQueryHandler findApplicablePriceQueryHandler(FindApplicablePriceService findApplicablePriceService) {
        return new FindApplicablePriceQueryHandler(findApplicablePriceService);
    }
}
