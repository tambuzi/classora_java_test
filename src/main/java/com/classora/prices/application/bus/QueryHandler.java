package com.classora.prices.application.bus;

public interface QueryHandler<Q extends Query<R>, R> {

    R handle(Q query);

    Class<Q> queryType();
}
