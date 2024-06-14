package com.project.customer.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

  private final ErrorCode errorCode;
  private final String errorMessage;
  private final String extraInfo;

  public CustomException(final ErrorCode errorCode, final Exception e, final String extraInfo) {
    super(e);
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getValue();
      this.extraInfo = extraInfo;
  }

  public CustomException(final ErrorCode errorCode, final String errorMessage, final String extraInfo) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getValue();
      this.extraInfo = extraInfo;
  }

  public CustomException(final ErrorCode errorCode, final String extraInfo){
    this.errorCode= errorCode;
    this.errorMessage= errorCode.getValue();
      this.extraInfo = extraInfo;
  }

}
