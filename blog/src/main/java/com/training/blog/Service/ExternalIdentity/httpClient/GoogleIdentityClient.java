package com.training.blog.Service.ExternalIdentity.httpClient;

import com.training.blog.Payload.Request.GoogleOauth2TokenRequest;
import com.training.blog.Payload.Response.GoogleOauth2TokenResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="google-identity", url ="https://oauth2.googleapis.com")
public interface GoogleIdentityClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    GoogleOauth2TokenResponse exchangeToken(@QueryMap GoogleOauth2TokenRequest req);
}
