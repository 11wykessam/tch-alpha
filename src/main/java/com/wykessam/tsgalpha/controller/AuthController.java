package com.wykessam.tsgalpha.controller;

import com.wykessam.tsgalpha.api.request.LoginRequestV1;
import com.wykessam.tsgalpha.api.response.LoginResponseV1;
import com.wykessam.tsgalpha.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

/**
 * @author Samuel Wykes.
 * Controller used to log in users.
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint used by users to log in.
     * @param request {@link LoginRequestV1}.
     * @return {@link }
     */
    @MessageMapping("auth.login.v1")
    public Mono<LoginResponseV1> login(@Validated final LoginRequestV1 request) {
        return this.authService.login(request);
    }

}
