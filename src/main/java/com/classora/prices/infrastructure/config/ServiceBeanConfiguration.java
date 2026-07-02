package com.classora.prices.infrastructure.config;

import com.classora.prices.domain.model.PriceFinder;
import com.classora.prices.domain.service.FindApplicablePriceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfiguration {

    @Bean
    public FindApplicablePriceService findApplicablePriceService(PriceFinder priceFinder) {
        return new FindApplicablePriceService(priceFinder);
    }
}
