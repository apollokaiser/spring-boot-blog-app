package com.training.blog.Service.ExternalIdentity;

import com.training.blog.Payload.Request.GoogleOauth2TokenRequest;
import com.training.blog.Payload.Response.GoogleOauth2TokenResponse;
import com.training.blog.Payload.Response.UserInfo;
import com.training.blog.Security.ClientInfo.GoogleClient;
import com.training.blog.Service.ExternalIdentity.httpClient.GoogleIdentityClient;
import com.training.blog.Service.ExternalIdentity.httpClient.GoogleUserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleIdentityServiceImpl implements GoogleIdentityService{
    private final GoogleIdentityClient googleIdentityClient;
    private final GoogleClient googleClient;
    private final GoogleUserClient googleUserClient;
    @Override
    public UserInfo exchangeUserInfo(String authentication_code) {
        GoogleOauth2TokenRequest request = GoogleOauth2TokenRequest.builder()
                .code(authentication_code)
                .clientId(googleClient.getClientId())
                .clientSecret(googleClient.getClientSecret())
                .redirectUri(googleClient.getRedirectUri())
                .grantType("authorization_code")
                .build();
        GoogleOauth2TokenResponse response = googleIdentityClient.exchangeToken(request);
        UserInfo userInfo = googleUserClient.getUserInfo("json", response.getAccessToken());
        return userInfo;
    }
}
