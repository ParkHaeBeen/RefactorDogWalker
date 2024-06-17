package com.project.walker.user.controller;

import com.project.walker.exception.ErrorCode;
import com.project.walker.exception.user.UserException;
import com.project.walker.user.dto.request.UserJoinRequest;
import com.project.walker.user.dto.response.UserJoinResponse;
import com.project.walker.user.dto.response.UserLoginResponse;
import com.project.walker.user.dto.response.UserTokenResponse;
import com.project.walker.user.service.UserService;
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

    @PostMapping( value = "/join")
    public ResponseEntity<UserJoinResponse> join(@RequestBody @Valid final UserJoinRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.join(request));
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
