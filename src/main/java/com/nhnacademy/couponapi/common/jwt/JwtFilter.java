package com.nhnacademy.couponapi.common.jwt;

import com.nhnacademy.couponapi.common.exception.JwtException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.infrastructure.adapter.AuthAdaptor;
import com.nhnacademy.couponapi.presentation.dto.response.JwtAuthResponse;
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

@RequiredArgsConstructor
@Component
public class JwtFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;
    private final AuthAdaptor authAdaptor;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getServletPath();

        if (path.equals("/coupon/modal") || path.matches("/coupons/books/\\d+/coupons")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (path.matches(".*/detail/\\d+") && StringUtils.isEmpty(request.getHeader("Authorization"))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = getToken((HttpServletRequest) servletRequest);

        if (jwtProvider.isValidToken(token)) {
            String uuid = jwtProvider.getUserNameFromToken(token);
            JwtAuthResponse jwtAuthResponse = authAdaptor.getUserInfoByUUID(uuid);

            JwtUserDetails jwtUserDetails = JwtUserDetails.of(jwtAuthResponse.customerId(),
                    jwtAuthResponse.role(), token);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    jwtUserDetails, null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + jwtAuthResponse.role()))
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(servletRequest, servletResponse);
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