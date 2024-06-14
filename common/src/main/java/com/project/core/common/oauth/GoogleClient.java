package com.project.core.common.oauth;


import com.project.core.common.config.FeignConfig;
import com.project.core.common.oauth.dto.GoogleRequest;
import com.project.core.common.oauth.dto.GoogleResponse;
import com.project.core.common.oauth.dto.GoogleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "google",url = "${google.auth.url}",configuration = FeignConfig.class)
public interface GoogleClient {

  @PostMapping("/token")
  GoogleTokenResponse getGoogleToken(final GoogleRequest request);

  @PostMapping("/tokeninfo")
  GoogleResponse getGoogleDetailInfo(@RequestParam("id_token") final String idToken);
}
