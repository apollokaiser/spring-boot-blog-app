package com.training.blog.Service.ExternalIdentity;

import com.training.blog.Payload.Response.GoogleOauth2TokenResponse;
import com.training.blog.Payload.Response.UserInfo;

public interface  GoogleIdentityService {
    UserInfo exchangeUserInfo(String authentication_code);
}
