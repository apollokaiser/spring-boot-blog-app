package com.training.blog.Payload.Response;

public record UserInfo(
        long id,
        String name,
        String given_name,
        String family_name,
        String picture,
        String email,
        Boolean email_verified,
        String locale
) {
}
