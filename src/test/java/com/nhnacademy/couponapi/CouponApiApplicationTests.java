package com.nhnacademy.couponapi;

import com.nhnacademy.couponapi.common.jwt.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CouponApiApplicationTests {

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    void contextLoads() {
        assertNotNull(jwtProvider, "JwtProvider should not be null");  // JwtProvider가 주입되었는지 확인
    }
}