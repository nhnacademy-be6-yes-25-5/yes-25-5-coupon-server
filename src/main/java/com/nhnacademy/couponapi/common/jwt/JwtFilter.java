package com.nhnacademy.couponapi.common.jwt;

import com.nhnacademy.couponapi.common.exception.JwtException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.infrastructure.adapter.AuthAdaptor;
import com.nhnacademy.couponapi.presentation.dto.response.LoginUserResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class JwtFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;
    private final AuthAdaptor authAdaptor;

    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/coupons/swagger-ui.html",
            "/coupons/swagger-ui/index.html",
            "/coupons/v3/api-docs",
            "/coupons/swagger-ui",
            "/coupons",
            "/coupons/expired",
            "/coupons/info"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getServletPath();

        if (isExcludedUrl(path) && StringUtils.isEmpty(httpRequest.getHeader("Authorization"))) {
            chain.doFilter(request, response);
            return;
        }

        String token = getToken(httpRequest);

        if (jwtProvider.isValidToken(token)) {
            LoginUserResponse user = jwtProvider.getLoginUserFromToken(token);

            JwtUserDetails jwtUserDetails = JwtUserDetails.of(user.userId(),
                    user.userRole(), token);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    jwtUserDetails, null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.userRole()))
            );
            httpResponse.setHeader("Authorization", "Bearer " + token);
            httpResponse.setHeader("Refresh-Token", ((HttpServletRequest) request).getHeader("Refresh-Token"));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        chain.doFilter(request, response);
    }

    private boolean isExcludedUrl(String path) {
        return EXCLUDE_URLS.stream().anyMatch(path::startsWith);
    }

    private String getToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }

        throw new JwtException(
                ErrorStatus.toErrorStatus("헤더에서 토큰을 찾을 수 없습니다.", 401, LocalDateTime.now())
        );
    }
}
