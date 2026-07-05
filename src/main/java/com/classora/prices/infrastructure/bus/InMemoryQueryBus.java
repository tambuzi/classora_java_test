package com.classora.prices.infrastructure.bus;

import com.classora.prices.application.exception.NoQueryHandlerException;
import com.classora.prices.application.bus.Query;
import com.classora.prices.application.bus.QueryBus;
import com.classora.prices.application.bus.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class InMemoryQueryBus implements QueryBus {

    private final Map<Class<?>, QueryHandler<?, ?>> handlersByQueryType;

    public InMemoryQueryBus(List<QueryHandler<?, ?>> handlers) {
        this.handlersByQueryType = handlers.stream()
                .collect(Collectors.toMap(QueryHandler::queryType, Function.identity()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R handle(Query<R> query) {
        QueryHandler<Query<R>, R> handler =
                (QueryHandler<Query<R>, R>) handlersByQueryType.get(query.getClass());
        if (handler == null) {
            throw new NoQueryHandlerException(query.getClass());
        }
        return handler.handle(query);
    }
}
