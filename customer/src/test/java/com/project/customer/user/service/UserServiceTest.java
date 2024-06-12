package com.project.customer.user.service;

import com.project.core.common.oauth.GoogleOauth;
import com.project.core.common.token.TokenProvider;
import com.project.customer.common.service.LocationService;
import com.project.customer.user.repository.CustomerDogRepository;
import com.project.customer.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private GoogleOauth googleOauth;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LocationService locationService;

    @Mock
    private CustomerDogRepository customerDogRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getLoginView() {
        //given
        String loginView = "http://google";
        given(googleOauth.getLoginView()).willReturn(loginView);

        //when
        String response = userService.getLoginView();

        //then
        Assertions.assertThat(response).isEqualTo(loginView);
    }

}