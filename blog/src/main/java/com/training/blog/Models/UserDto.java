package com.training.blog.Models;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
     Long id;
     String name;
     String email;
     String intro;
     String avatar;
     String profile;
}
