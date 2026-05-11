package com.example.tea.config;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphqlConfig {

    @Bean
    GraphQLScalarType dateTimeScalar() {
        return GraphQLScalarType.newScalar()
                .name("DateTime")
                .description("ISO-8601 local date-time (e.g. 2025-03-27T10:00:00)")
                .coercing(new DateTimeCoercing())
                .build();
    }

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer(GraphQLScalarType dateTimeScalar) {
        return wiringBuilder -> wiringBuilder.scalar(dateTimeScalar);
    }

    private static final class DateTimeCoercing implements Coercing<LocalDateTime, String> {

        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public String serialize(Object dataFetcherResult) {
            if (dataFetcherResult instanceof LocalDateTime ldt) {
                return ldt.format(FORMATTER);
            }
            throw new CoercingSerializeException(
                    "Expected LocalDateTime but got: " + dataFetcherResult.getClass().getSimpleName());
        }

        @Override
        public LocalDateTime parseValue(Object input) {
            if (input instanceof String s) {
                try {
                    return LocalDateTime.parse(s, FORMATTER);
                } catch (DateTimeParseException e) {
                    throw new CoercingParseValueException("Invalid DateTime value: '" + s + "'", e);
                }
            }
            throw new CoercingParseValueException(
                    "Expected String for DateTime but got: " + input.getClass().getSimpleName());
        }

        @Override
        public LocalDateTime parseLiteral(Object input) {
            if (input instanceof StringValue sv) {
                try {
                    return LocalDateTime.parse(sv.getValue(), FORMATTER);
                } catch (DateTimeParseException e) {
                    throw new CoercingParseLiteralException("Invalid DateTime literal: '" + sv.getValue() + "'", e);
                }
            }
            throw new CoercingParseLiteralException(
                    "Expected StringValue for DateTime but got: " + input.getClass().getSimpleName());
        }
    }
}
