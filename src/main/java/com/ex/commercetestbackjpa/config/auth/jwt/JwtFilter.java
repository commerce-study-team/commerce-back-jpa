package com.ex.commercetestbackjpa.config.auth.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "x-auth-token";
    private JwtAuthTokenProvider tokenProvider;

    public JwtFilter(JwtAuthTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = resolveToken(request);

        if (token.isPresent()) {
            JwtAuthToken jwtAuthToken = tokenProvider.convertAuthToken(token.get());

            if (jwtAuthToken.validate()) {
                Authentication authentication = tokenProvider.getAuthentication(jwtAuthToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> resolveToken(HttpServletRequest request) {

        String authtoekn = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(authtoekn)) {
            return Optional.of(authtoekn);
        } else {
            return Optional.empty();
        }
    }
}