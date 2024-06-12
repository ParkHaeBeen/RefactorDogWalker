package com.project.core.common.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleTokenResponse {
  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("expires_in")
  private String expiresIn;

  @JsonProperty("refresh_token")
  private String refreshToken;

  private String scope;

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("id_token")
  private String idToken;
}
