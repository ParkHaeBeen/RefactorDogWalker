package com.project.core.common.oauth.dto;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
@Getter
public class GoogleResponse {
  private String email;
  private String name;
  private String idToken;
}
