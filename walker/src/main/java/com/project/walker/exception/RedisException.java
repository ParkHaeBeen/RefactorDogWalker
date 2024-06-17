package com.project.walker.exception;

import lombok.Getter;

@Getter
public class RedisException extends CustomException{
    public RedisException(final ErrorCode errorCode) {
        super(errorCode, (String) null);
    }
}
