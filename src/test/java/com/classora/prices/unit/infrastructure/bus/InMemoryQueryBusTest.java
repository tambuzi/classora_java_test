package com.classora.prices.unit.infrastructure.bus;

import com.classora.prices.application.bus.Query;
import com.classora.prices.application.bus.QueryBus;
import com.classora.prices.application.bus.QueryHandler;
import com.classora.prices.application.exception.NoQueryHandlerException;
import com.classora.prices.application.validation.QueryValidator;
import com.classora.prices.infrastructure.bus.InMemoryQueryBus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryQueryBusTest {

    @Test
    void shouldDispatchToTheHandlerWhenNoValidatorIsRegistered() {
        QueryBus queryBus = new InMemoryQueryBus(List.of(new GreetingHandler()), List.of());

        String result = queryBus.handle(new GreetingQuery("world"));

        assertThat(result).isEqualTo("hello world");
    }

    @Test
    void shouldValidateBeforeDispatching() {
        QueryBus queryBus = new InMemoryQueryBus(List.of(new GreetingHandler()), List.of(new PassingValidator()));

        String result = queryBus.handle(new GreetingQuery("world"));

        assertThat(result).isEqualTo("hello world");
    }

    @Test
    void shouldPropagateAValidationFailureWithoutDispatching() {
        QueryBus queryBus = new InMemoryQueryBus(List.of(new GreetingHandler()), List.of(new RejectingValidator()));

        assertThatThrownBy(() -> queryBus.handle(new GreetingQuery("world")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("invalid");
    }

    @Test
    void shouldFailWhenNoHandlerIsRegisteredForTheQuery() {
        QueryBus queryBus = new InMemoryQueryBus(List.of(), List.of());

        assertThatThrownBy(() -> queryBus.handle(new GreetingQuery("world")))
                .isInstanceOf(NoQueryHandlerException.class)
                .hasMessageContaining(GreetingQuery.class.getName());
    }

    private record GreetingQuery(String name) implements Query<String> {
    }

    private static final class GreetingHandler implements QueryHandler<GreetingQuery, String> {

        @Override
        public String handle(GreetingQuery query) {
            return "hello " + query.name();
        }

        @Override
        public Class<GreetingQuery> queryType() {
            return GreetingQuery.class;
        }
    }

    private static final class PassingValidator implements QueryValidator<GreetingQuery> {

        @Override
        public void validate(GreetingQuery query) {
        }

        @Override
        public Class<GreetingQuery> queryType() {
            return GreetingQuery.class;
        }
    }

    private static final class RejectingValidator implements QueryValidator<GreetingQuery> {

        @Override
        public void validate(GreetingQuery query) {
            throw new IllegalStateException("invalid");
        }

        @Override
        public Class<GreetingQuery> queryType() {
            return GreetingQuery.class;
        }
    }
}
