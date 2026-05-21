package com.example.tea.global.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.example.tea.global.exception.code.ErrorCode;
import graphql.GraphQLError;
import graphql.execution.ExecutionStepInfo;
import graphql.execution.ResultPath;
import graphql.language.Field;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.graphql.execution.ErrorType;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    static class TestableGlobalExceptionHandler extends GlobalExceptionHandler {
        GraphQLError resolve(Throwable ex, DataFetchingEnvironment env) {
            return resolveToSingleError(ex, env);
        }

        List<GraphQLError> resolveMultiple(Throwable ex, DataFetchingEnvironment env) {
            return resolveToMultipleErrors(ex, env);
        }
    }

    private TestableGlobalExceptionHandler handler;

    @Mock private DataFetchingEnvironment env;
    @Mock private ExecutionStepInfo stepInfo;
    @Mock private Field field;

    @BeforeEach
    void setUp() {
        handler = new TestableGlobalExceptionHandler();
        lenient().when(env.getExecutionStepInfo()).thenReturn(stepInfo);
        lenient().when(stepInfo.getPath()).thenReturn(ResultPath.rootPath());
        lenient().when(env.getField()).thenReturn(field);
        lenient().when(field.getSourceLocation()).thenReturn(null);
    }

    // BizException은 BAD_REQUEST로 처리된다
    @Test
    void bizExceptionResolvedAsBadRequestTest() {
        BizException ex = new BizException(ErrorCode.BRAND_ALREADY_EXISTS);

        GraphQLError error = handler.resolve(ex, env);

        assertThat(error).isNotNull();
        assertThat(error.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        assertThat(error.getExtensions()).containsEntry("code", "BRAND_ALREADY_EXISTS");
        assertThat(error.getExtensions()).containsEntry("type", "BIZ");
        assertThat(error.getMessage()).isEqualTo(ErrorCode.BRAND_ALREADY_EXISTS.getMessage());
    }

    // DomainException은 BAD_REQUEST로 처리된다
    @Test
    void domainExceptionResolvedAsBadRequestTest() {
        DomainException ex = new DomainException(ErrorCode.TEA_NOT_FOUND);

        GraphQLError error = handler.resolve(ex, env);

        assertThat(error).isNotNull();
        assertThat(error.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        assertThat(error.getExtensions()).containsEntry("code", "TEA_NOT_FOUND");
        assertThat(error.getExtensions()).containsEntry("type", "DOMAIN");
        assertThat(error.getMessage()).isEqualTo(ErrorCode.TEA_NOT_FOUND.getMessage());
    }

    // PersistenceException은 INTERNAL_ERROR로 처리된다
    @Test
    void persistenceExceptionResolvedAsInternalErrorTest() {
        PersistenceException ex = new PersistenceException(ErrorCode.TEA_NOT_FOUND);

        GraphQLError error = handler.resolve(ex, env);

        assertThat(error).isNotNull();
        assertThat(error.getErrorType()).isEqualTo(ErrorType.INTERNAL_ERROR);
        assertThat(error.getExtensions()).containsEntry("code", "TEA_NOT_FOUND");
        assertThat(error.getExtensions()).containsEntry("type", "PERSISTENCE");
    }

    // 처리되지 않는 예외는 null을 반환한다
    @Test
    void unknownExceptionReturnsNullTest() {
        RuntimeException ex = new RuntimeException("알 수 없는 오류");

        GraphQLError error = handler.resolve(ex, env);

        assertThat(error).isNull();
    }

    // BizException에 커스텀 메세지가 있으면 해당 메세지로 응답한다
    @Test
    void bizExceptionCustomMessageRespondedTest() {
        BizException ex = new BizException(ErrorCode.BRAND_ALREADY_EXISTS, "Osulloc 브랜드가 이미 존재합니다");

        GraphQLError error = handler.resolve(ex, env);

        assertThat(error).isNotNull();
        assertThat(error.getMessage()).isEqualTo("Osulloc 브랜드가 이미 존재합니다");
    }

    // ConstraintViolationException은 Adapter 레이어에서 발생하므로 type: ADAPTER로 응답한다
    @Test
    void constraintViolationExceptionResolvedAsAdapterTypeTest() {
        ConstraintViolationException ex = new ConstraintViolationException(Set.of());

        List<GraphQLError> errors = handler.resolveMultiple(ex, env);

        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getExtensions()).containsEntry("type", "ADAPTER");
    }

}
