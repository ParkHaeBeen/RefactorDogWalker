package com.project.core.common.token;

import com.project.core.domain.user.Role;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public record AuthMember(
        String email,
        Role role
) {
}