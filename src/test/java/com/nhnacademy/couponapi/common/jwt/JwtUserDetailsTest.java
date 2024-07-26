//package com.nhnacademy.couponapi.common.jwt;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class JwtUserDetailsTest {
//
//    private JwtUserDetails jwtUserDetails;
//    private Long userId;
//    private String role;
//    private String accessToken;
//
//    @BeforeEach
//    public void setUp() {
//        userId = 1L;
//        role = "USER";
//        accessToken = "testAccessToken";
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//        jwtUserDetails = JwtUserDetails.builder()
//                .userId(userId)
//                .roles(authorities)
//                .accessToken(accessToken)
//                .build();
//    }
//
//    @Test
//    public void testOfMethod() {
//        JwtUserDetails userDetails = JwtUserDetails.of(userId, role, accessToken);
//        assertNotNull(userDetails);
//        assertEquals(userId, userDetails.userId());
//        assertEquals("ROLE_" + role, userDetails.getAuthorities().iterator().next().getAuthority());
//        assertEquals(accessToken, userDetails.accessToken());
//    }
//
//    @Test
//    public void testGetAuthorities() {
//        Collection<? extends GrantedAuthority> authorities = jwtUserDetails.getAuthorities();
//        assertNotNull(authorities);
//        assertEquals(1, authorities.size());
//        assertEquals("ROLE_" + role, authorities.iterator().next().getAuthority());
//    }
//
//    @Test
//    public void testGetPassword() {
//        assertNull(jwtUserDetails.getPassword());
//    }
//
//    @Test
//    public void testGetUsername() {
//        assertEquals(String.valueOf(userId), jwtUserDetails.getUsername());
//    }
//
//    @Test
//    public void testUserId() {
//        assertEquals(userId, jwtUserDetails.userId());
//    }
//
//    @Test
//    public void testRoles() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//        assertEquals(authorities, jwtUserDetails.roles());
//    }
//
//    @Test
//    public void testAccessToken() {
//        assertEquals(accessToken, jwtUserDetails.accessToken());
//    }
//
//    @Test
//    public void testEqualsAndHashCode() {
//        JwtUserDetails userDetails1 = JwtUserDetails.of(userId, role, accessToken);
//        JwtUserDetails userDetails2 = JwtUserDetails.of(userId, role, accessToken);
//
//        assertEquals(userDetails1, userDetails2);
//        assertEquals(userDetails1.hashCode(), userDetails2.hashCode());
//    }
//
//    @Test
//    public void testToString() {
//        String expected = "JwtUserDetails[userId=" + userId + ", roles=[" +
//                "ROLE_" + role + "], accessToken=" + accessToken + "]";
//        assertEquals(expected, jwtUserDetails.toString());
//    }
//}
