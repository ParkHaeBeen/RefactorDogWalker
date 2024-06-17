package com.project.customer.user.controller;

import com.project.customer.exception.ErrorCode;
import com.project.customer.exception.user.UserException;
import com.project.customer.user.dto.request.UserJoinRequest;
import com.project.customer.user.dto.response.UserJoinResponse;
import com.project.customer.user.dto.response.UserLoginResponse;
import com.project.customer.user.dto.response.UserTokenResponse;
import com.project.customer.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v2/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login/view")
    public ResponseEntity<String> getLoginView() {
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .body(userService.getLoginView());
    }

    @GetMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            @RequestParam final String code
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.login(code));
    }

    @PostMapping( value = "/join", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserJoinResponse> join(
            @RequestPart @Valid final UserJoinRequest request,
            @RequestPart(required = false) final MultipartFile dogImg) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.join(request , dogImg));
    }

    @PostMapping("/access-token")
    public ResponseEntity<UserTokenResponse> generateAccessToken(@RequestBody final String refreshToken) {
        if(refreshToken == null || refreshToken.isEmpty()){
            throw new UserException(ErrorCode.TOKEN_NOT_EXIST);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.generateAccessToken(refreshToken));
    }
}
