package com.classora.prices.application.validation;

public interface Validator<T> {

    void validate(T target);
}
