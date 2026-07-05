package com.classora.prices.application.validation;

import com.classora.prices.application.bus.Query;

public interface QueryValidator<Q extends Query<?>> extends Validator<Q> {

    Class<Q> queryType();
}
