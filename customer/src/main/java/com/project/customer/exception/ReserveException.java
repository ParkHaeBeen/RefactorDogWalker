package com.project.customer.exception;

import lombok.Getter;

@Getter
public class ReserveException extends CustomException{
    public ReserveException(final ErrorCode errorCode) {
        super(errorCode, (String) null);
    }

    public ReserveException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
