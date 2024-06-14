package com.project.core.common.oauth.feign;

import java.util.Map;

public class FeignResponseError {
  public Object data;
  public ErrorData error;

  public static class ErrorData {
    public String code;
    public String message;
    public Map<String, Object> data;

    @Override
    public String toString() {
      return "ErrorData{" +
          "code='" + code + '\'' +
          ", message='" + message ;
    }
  }
}
