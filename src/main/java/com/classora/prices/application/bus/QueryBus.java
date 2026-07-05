package com.classora.prices.application.bus;

public interface QueryBus {

    <R> R handle(Query<R> query);
}
