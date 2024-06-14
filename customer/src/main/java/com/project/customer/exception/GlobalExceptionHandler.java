package com.project.customer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private static final String LOG_ERROR_MESSAGE = "Class : {}, Code : {}, Message : {}, Extra : {}";

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
    log.info(LOG_ERROR_MESSAGE, e.getClass(), e.getErrorCode(), e.getMessage(), e.getExtraInfo());
    return ResponseEntity.badRequest().body(ExceptionResponse.from(e));
  }
}

