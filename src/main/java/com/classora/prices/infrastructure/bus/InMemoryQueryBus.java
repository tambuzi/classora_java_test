package com.classora.prices.infrastructure.bus;

import com.classora.prices.application.bus.Query;
import com.classora.prices.application.bus.QueryBus;
import com.classora.prices.application.bus.QueryHandler;
import com.classora.prices.application.exception.NoQueryHandlerException;
import com.classora.prices.application.validation.QueryValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class InMemoryQueryBus implements QueryBus {

    private final Map<Class<?>, QueryHandler<?, ?>> handlersByQueryType;
    private final Map<Class<?>, QueryValidator<?>> validatorsByQueryType;

    public InMemoryQueryBus(List<QueryHandler<?, ?>> handlers, List<QueryValidator<?>> validators) {
        this.handlersByQueryType = handlers.stream()
                .collect(Collectors.toMap(QueryHandler::queryType, Function.identity()));
        this.validatorsByQueryType = validators.stream()
                .collect(Collectors.toMap(QueryValidator::queryType, Function.identity()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R handle(Query<R> query) {
        validate(query);
        QueryHandler<Query<R>, R> handler =
                (QueryHandler<Query<R>, R>) handlersByQueryType.get(query.getClass());
        if (handler == null) {
            throw new NoQueryHandlerException(query.getClass());
        }
        return handler.handle(query);
    }

    @SuppressWarnings("unchecked")
    private <R> void validate(Query<R> query) {
        QueryValidator<Query<R>> validator =
                (QueryValidator<Query<R>>) validatorsByQueryType.get(query.getClass());
        if (validator != null) {
            validator.validate(query);
        }
    }
}
