package com.project.walker.exception.user;

import com.project.walker.exception.CustomException;
import com.project.walker.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends CustomException {

    public UserException(final ErrorCode errorCode) {
        super(errorCode, (String) null);
    }

    public UserException(final Exception exception) {
        super(ErrorCode.IMG_UPLOAD_FAIL, exception);
    }

    public UserException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
