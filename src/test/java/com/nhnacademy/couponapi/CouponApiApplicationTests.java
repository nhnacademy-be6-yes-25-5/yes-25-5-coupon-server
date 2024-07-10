package com.nhnacademy.couponapi;

import com.nhnacademy.couponapi.common.jwt.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CouponApiApplicationTests {

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    void contextLoads() {
    }
}