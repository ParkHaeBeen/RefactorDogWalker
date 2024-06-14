package com.project.core.common.oauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleRequest {
  private String clientId;
  private String redirectUri;
  private String clientSecret;
  private String code;
  private String accessType;
  private String grantType;
}
