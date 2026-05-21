package com.example.tea.global.exception;

import com.example.tea.global.exception.code.ErrorCode;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof BizException e)
            return buildError(e.getErrorCode(), e.getMessage(), ErrorType.BAD_REQUEST, "BIZ", env);
        if (ex instanceof DomainException e)
            return buildError(e.getErrorCode(), e.getMessage(), ErrorType.BAD_REQUEST, "DOMAIN", env);
        if (ex instanceof PersistenceException e)
            return buildError(e.getErrorCode(), e.getMessage(), ErrorType.INTERNAL_ERROR, "PERSISTENCE", env);
        return null;
    }

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof ConstraintViolationException e) {
            String message = e.getConstraintViolations().stream()
                    .map(v -> v.getMessage())
                    .distinct()
                    .sorted()
                    .collect(Collectors.joining("; "));
            return List.of(buildError(ErrorCode.FIELD_VALIDATION, message, ErrorType.BAD_REQUEST, "ADAPTER", env));
        }
        if (ex instanceof BindException e) {
            String message = e.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .filter(msg -> msg != null && !msg.isBlank())
                    .distinct()
                    .collect(Collectors.joining("; "));
            return List.of(buildError(ErrorCode.FIELD_VALIDATION, message, ErrorType.BAD_REQUEST, "ADAPTER", env));
        }
        return null;
    }

    private GraphQLError buildError(ErrorCode code, String message, ErrorType type, String exceptionType, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(type)
                .message(message)
                .extensions(Map.of("code", code.getCode(), "type", exceptionType))
                .build();
    }
}
