package com.training.blog.Mapper;

import com.training.blog.Entities.Users;
import com.training.blog.Models.UserDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (BellSoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUser(Users user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.name( user.getName() );
        userDto.email( user.getEmail() );
        userDto.intro( user.getIntro() );
        userDto.avatar( user.getAvatar() );
        userDto.profile( user.getProfile() );

        return userDto.build();
    }

    @Override
    public Users toUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        Users.UsersBuilder users = Users.builder();

        users.id( userDto.getId() );
        users.email( userDto.getEmail() );
        users.avatar( userDto.getAvatar() );
        users.intro( userDto.getIntro() );
        users.profile( userDto.getProfile() );
        users.name( userDto.getName() );

        return users.build();
    }
}
