package com.project.core.common.token;

import com.project.core.domain.user.Role;
import lombok.Builder;

@Builder
public record AuthUser(
        String email,
        Role role
) {
}