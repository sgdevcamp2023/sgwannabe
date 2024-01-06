package spring.auth.common.exception;

public interface BaseErrorCode {

    ErrorReason getErrorReason();

    String getExplainError() throws NoSuchFieldException;
}
