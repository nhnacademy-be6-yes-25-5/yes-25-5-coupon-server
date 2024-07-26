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
            "/coupons/expired",
            "/coupons/info"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getServletPath();

        //토큰이 있으면 받고 없으면 바로 리턴
        if (path.matches("/coupons")) {
            try {
                String accessToken = getToken(httpRequest);
                String refreshToken = httpRequest.getHeader("Refresh-Token");
                String uuid = jwtProvider.getUserNameFromToken(accessToken);
                JwtAuthResponse jwtAuthResponse = authAdaptor.getUserInfoByUUID(uuid);

                JwtUserDetails jwtUserDetails = JwtUserDetails.of(jwtAuthResponse.customerId(),
                        jwtAuthResponse.role(), accessToken, refreshToken);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        jwtUserDetails, null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + jwtAuthResponse.role()))
                );

                httpResponse.setHeader("Authorization", "Bearer " + accessToken);
                httpResponse.setHeader("Refresh-Token", refreshToken);

                SecurityContextHolder.getContext().setAuthentication(authToken);
                chain.doFilter(request, response);
                return;

            } catch (Exception e) {
                chain.doFilter(request, response);
                return;
            }
        }

        if (isExcludedUrl(path) && StringUtils.isEmpty(httpRequest.getHeader("Authorization"))) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = getToken(httpRequest);
        String refreshToken = httpRequest.getHeader("Refresh-Token");

        if (jwtProvider.isValidToken(accessToken)) {
            String uuid = jwtProvider.getUserNameFromToken(accessToken);
            JwtAuthResponse jwtAuthResponse = authAdaptor.getUserInfoByUUID(uuid);

            JwtUserDetails jwtUserDetails = JwtUserDetails.of(jwtAuthResponse.customerId(),
                    jwtAuthResponse.role(), accessToken, refreshToken);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    jwtUserDetails, null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + jwtAuthResponse.role()))
            );
            httpResponse.setHeader("Authorization", "Bearer " + accessToken);
            httpResponse.setHeader("Refresh-Token", refreshToken);
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
