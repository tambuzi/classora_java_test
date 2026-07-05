package com.classora.prices.application.exception;

public class NoQueryHandlerException extends RuntimeException {

    public NoQueryHandlerException(Class<?> queryType) {
        super("No query handler registered for " + queryType.getName());
    }
}
