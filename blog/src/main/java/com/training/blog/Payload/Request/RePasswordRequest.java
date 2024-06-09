package com.training.blog.Payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RePasswordRequest {
    @Email
    String email;
    String oldPassword;
    @NotEmpty
    String newPassword;
    @NotEmpty
    String resetCode;
}
