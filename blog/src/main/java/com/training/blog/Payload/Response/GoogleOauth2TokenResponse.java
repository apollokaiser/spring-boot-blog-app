package com.training.blog.Payload.Response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoogleOauth2TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private Long expiresIn;
}
