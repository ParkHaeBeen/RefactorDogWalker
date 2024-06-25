package com.project.core.common.oauth;

import com.project.core.common.oauth.dto.GoogleRequest;
import com.project.core.common.oauth.dto.GoogleResponse;
import com.project.core.common.oauth.dto.GoogleTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOauth {

    @Value("${google.client.id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${google.client.pw}")
    private String GOOGLE_CLIENT_PW;
    @Value("${google.login.url}")
    private String GOOGLE_API_URL;
    @Value("${google.redirect.url}")
    private String GOOGLE_REDIRECT_URL;

    private final GoogleClient googleClient;

    public String getLoginView(){
        final String requestUrl = GOOGLE_API_URL+"client_id="
                + GOOGLE_CLIENT_ID
                + "&redirect_uri="+GOOGLE_REDIRECT_URL
                +"&response_type=code" +
                "&scope=email%20profile%20openid";

        return requestUrl;
    }

    public GoogleResponse login(final String code){
        final GoogleTokenResponse googleTokenResponse = googleClient.getGoogleToken(GoogleRequest.builder()
                .clientId(GOOGLE_CLIENT_ID)
                .clientSecret(GOOGLE_CLIENT_PW)
                .code(code)
                .redirectUri(GOOGLE_REDIRECT_URL)
                .grantType("authorization_code")
                .build());

        final GoogleResponse response = googleClient.getGoogleDetailInfo(googleTokenResponse.getIdToken());
        response.setIdToken(googleTokenResponse.getIdToken());
        return response;
    }

    public GoogleResponse getUserInfo(final String accessToken){
        return googleClient.getGoogleDetailInfo(accessToken);
    }
}

