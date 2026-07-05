package com.classora.prices.infrastructure.config;

import com.classora.prices.application.query.validation.FindApplicablePriceQueryValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorBeanConfiguration {

    @Bean
    public FindApplicablePriceQueryValidator findApplicablePriceQueryValidator() {
        return new FindApplicablePriceQueryValidator();
    }
}
