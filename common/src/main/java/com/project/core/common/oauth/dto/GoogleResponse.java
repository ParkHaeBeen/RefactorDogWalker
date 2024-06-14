package com.project.core.common.oauth.dto;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class GoogleResponse {
   private String email;
   private String name;
   @Setter
   private String idToken;

}
