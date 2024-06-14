package com.project.customer.exception;

import lombok.Getter;

@Getter
public class RedisException extends CustomException{
    public RedisException(final ErrorCode errorCode) {
        super(errorCode, null);
    }
}
