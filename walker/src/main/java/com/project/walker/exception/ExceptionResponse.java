package com.project.walker.exception;

public class ExceptionResponse {
    private final String message;
    private final ErrorCode errorCode;
    private String extraInfo;

    public ExceptionResponse(String message, ErrorCode errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.extraInfo = null;
    }

    public ExceptionResponse(String message, ErrorCode errorCode, String extraInfo) {
        this.message = message;
        this.errorCode = errorCode;
        this.extraInfo = extraInfo;
    }

    public static ExceptionResponse from(CustomException e) {
        return new ExceptionResponse(e.getErrorCode().getValue(), e.getErrorCode(), e.getExtraInfo());
    }
}
