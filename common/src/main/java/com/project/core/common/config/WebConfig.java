package com.project.core.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.core.common.interceptor.AuthInterceptor;
import com.project.core.common.resolver.auth.AuthArgumentResolver;
import com.project.core.common.resolver.queryString.QueryStringArgsResolver;
import com.project.core.common.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(tokenProvider));
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(tokenProvider));
        resolvers.add(new QueryStringArgsResolver(objectMapper));
    }
}
