package com.project.customer.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
  TOKEN_EXPIRED("토큰 만료기간이 지났습니다"),
  TOKEN_NOT_EXIST("토큰이 존재하지 않습니다"),

  IMG_UPLOAD_FAIL("이미지 업로드 실패하였습니다"),
  FEIGN_SERVER_API_ERROR("api 에러 발생하였습니다"),
  FEIGN_NOT_FOUND("해당 리소스를 찾지 못했습니다"),
  FEIGN_BAD_REQUEST("잘못된 요청입니다"),
  FEIGN_PARSE_ERROR("파싱 에러가 발생했습니다"),
  FEIGN_ERROR("feign 예외 발생했습니다"),

  NOT_WRITE_SERVICE_PRICE("서비수 수행자는 회원가입시 서비스 수행에 대한 가격을 기입해야합니다"),
  NOT_EXIST_MEMBER("멤버가 존재하지 않습니다. 회원가입해주세요"),
  NOT_WALKER("해당 경로는 서비스 수행자만 가능합니다"),
  NOT_EXIST_DOG("멤버의 강아지가 존재하지 않습니다"),


  RESERVE_REQUEST_NOT_EXIST("해당 요청에 예약관련된 정보가 존재하지 않습니다"),
  RESERVE_NOT_AVAILABLE("락 획득 실패로 예약 불가합니다"),
  NOT_EXIST_RESERVE("해당 예약이 존재하지 않습니다."),

  LOCK_INTERRUPTED_ERROR("인터럽트로 인해 락 획득 실패"),
  ALREADY_UNLOCK("이미 락이 해제되었습니다"),
  RESERVE_ALREAY("해당 날짜는 이미 예약되었습니다"),
  RESERVE_PROCESS("해당 날짜는 예약이 진행되고 있습니다"),
  RESERVE_DATE_NOT_MATCH("예약날짜와 현재날짜와 일치하지 않습니다."),
  RESERVE_CANCEL_UNAVAIL("서비스 취소는 하루전날까지 가능합니다"),

  BATCH_ADJUST_ERROR("서비스 수행자 정산 중 배치 에러"),
  BATCH_RESERVE_ERROR("예약 거절 배치 기능 에러"),

  SSE_ERROR("sse 관련 오류 발생"),
  NOT_FOUND_NOTICE("해당 알림을 찾을 수 없습니다"),

  NOT_FOUND_PAY_HISTORY("결제 내역을 찾을 수 없습니다"),
  NOT_FOUND_ROUTE("해당 경로 내역을 찾을 수 없습니다"),

  REDIS_ERROR("redis data 에러");

  private final String value;

  ErrorCode(final String value) {
    this.value = value;
  }
}
