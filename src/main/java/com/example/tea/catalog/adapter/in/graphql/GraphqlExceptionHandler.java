package com.example.tea.catalog.adapter.in.graphql;

import com.example.tea.shared.error.BaseException;
import com.example.tea.shared.error.ErrorCode;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice //TeaGraphqlApplication.java -> @SpringBootApplication -> com.example.tea 하위 패키지인 이곳도 스캔 대상 (resolver/usecase 예외 => spring이 handler 메소드를 고르고 graphql 에러로 변환
//BaseException을 이용. 서비스 계층의 에러(비즈니스 로직 에러)를 graphql 에러 분류로 매핑하는 메소드
public class GraphqlExceptionHandler {

    @GraphQlExceptionHandler //graphql용 @ExceptionHandler @ControllerAdvice 하위에 두면 graphql 컨트롤러에 공통 적용
    GraphQLError handleBaseException(BaseException exception, DataFetchingEnvironment environment) {
        ErrorType errorType = resolveErrorType(exception.getErrorCode());
        return buildError(exception.getMessage(), errorType, environment);
    }

    @GraphQlExceptionHandler
    GraphQLError handleConstraintViolation(
            ConstraintViolationException exception,
            DataFetchingEnvironment environment
    ) {
        String message = exception.getConstraintViolations().stream()
                .map(violation -> violation.getMessage())
                .distinct()
                .sorted()
                .collect(Collectors.joining("; "));
        return buildError(message, ErrorType.BAD_REQUEST, environment);
    }

    @GraphQlExceptionHandler
    GraphQLError handleBindException(BindException exception, DataFetchingEnvironment environment) {
        String message = exception.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .filter(value -> value != null && !value.isBlank())
                .distinct()
                .collect(Collectors.joining("; "));
        return buildError(message, ErrorType.BAD_REQUEST, environment);
    }

    private ErrorType resolveErrorType(ErrorCode errorCode) {
        return switch (errorCode) {
            case TEA_NOT_FOUND, BRAND_NOT_FOUND -> ErrorType.NOT_FOUND;
            case BRAND_ALREADY_EXISTS, FIELD_VALIDATION -> ErrorType.BAD_REQUEST;
            // Future contexts will add their ErrorCodes here
        };
    }

    private GraphQLError buildError(String message, ErrorType errorType, DataFetchingEnvironment environment) {
        return GraphqlErrorBuilder.newError(environment)
                .errorType(errorType)
                .message(message != null && !message.isBlank() ? message : "Validation failed.")
                .build();
    }
}
