package com.project.core.common.oauth.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

  private final ObjectMapper objectMapper;

  @Override
  public Exception decode(final String methodKey ,final Response response) {
      return new RuntimeException(parse(response));
  }

  private String parse(final Response response){
    try {
      return objectMapper.readValue(response.body().asInputStream(), FeignResponseError.class).error.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
