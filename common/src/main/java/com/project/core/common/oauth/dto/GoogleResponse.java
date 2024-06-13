package com.project.core.common.oauth.dto;

import lombok.*;

@Builder
public record GoogleResponse (
   String email,
   String name,
   String idToken
) {
}
