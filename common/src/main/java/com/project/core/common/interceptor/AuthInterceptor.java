package com.project.core.common.interceptor;

import com.project.core.common.token.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request , final HttpServletResponse response , final Object handler) throws Exception {
        final Auth authAnnotation = getLoginAnnotation(handler);

        if(!(handler instanceof HandlerMethod) || authAnnotation ==null){
            return true;
        }

        if(isAuthExist(request)){
            validateToken(request);
            return true;
        }

        validateTokenRequired(authAnnotation);
        return true;
    }

    private void validateTokenRequired(final Auth authAnnotation) {
        if(authAnnotation !=null && authAnnotation.required()){
            throw new RuntimeException("token not exist");
        }
    }

    private void validateToken(final HttpServletRequest request) {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!tokenProvider.validateAccessToken(authHeader)){
            throw new RuntimeException("token expired");
        }
    }

    private boolean isAuthExist(final HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION) != null;
    }

    private Auth getLoginAnnotation(final Object handler) {
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            return handlerMethod.getMethodAnnotation(Auth.class);
        }
        return null;
    }
}
