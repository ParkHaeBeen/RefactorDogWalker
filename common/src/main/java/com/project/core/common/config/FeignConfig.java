package com.project.core.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.core.common.oauth.feign.FeignErrorDecoder;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableFeignClients(basePackages = "com.project.core.common.oauth")
@RequiredArgsConstructor
public class FeignConfig {

  private final ObjectMapper objectMapper;

  @Bean
  public ErrorDecoder errorDecoder(){
    return new FeignErrorDecoder(objectMapper);
  }
}
