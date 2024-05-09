package com.training.blog.Mapper;

import com.training.blog.Entities.Users;
import com.training.blog.Models.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUser(Users user);
    Users toUser(UserDto userDto);
}
