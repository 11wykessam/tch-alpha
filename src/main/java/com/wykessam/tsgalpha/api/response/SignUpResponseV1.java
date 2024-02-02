package com.wykessam.tsgalpha.api.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Samuel Wykes.
 * Represents the response for an attempt to sign-up.
 */
@Getter
@SuperBuilder
@Jacksonized
public class SignUpResponseV1 extends ErrorAwareResponse {

    private final String token;

}