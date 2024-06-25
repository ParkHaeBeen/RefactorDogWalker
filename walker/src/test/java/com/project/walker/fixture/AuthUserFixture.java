package com.project.walker.fixture;

import com.project.core.common.token.AuthUser;
import com.project.core.domain.user.Role;

public enum AuthUserFixture {
    AUTH_USER_ONE("authUser@gmail.com", Role.USER),
    AUTH_USER_TWO("authUSer2@gmail.com", Role.WALKER);
    private final String email;
    private final Role role;

    AuthUserFixture(final String email, final Role role) {
        this.email = email;
        this.role = role;
    }

    public AuthUser 생성() {
        return new AuthUser(email, role);
    }
}
