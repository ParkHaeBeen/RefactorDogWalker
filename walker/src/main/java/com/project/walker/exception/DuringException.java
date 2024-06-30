package com.project.walker.exception;

import lombok.Getter;

@Getter
public class DuringException extends CustomException{
    public DuringException(final ErrorCode errorCode) {
        super(errorCode, (String) null);
    }

    public DuringException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
