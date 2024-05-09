package com.training.blog.Exception.CustomException;

import com.training.blog.Entities.Users;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotFoundEntityException extends RuntimeException {
    final Class<?> errorEntity;
    String message;
    public NotFoundEntityException(String message, Class<?> errorEntity) {
        super(message);
        this.errorEntity = errorEntity;
    }
    public NotFoundEntityException(String message) {
        super(message);
        // Mac dinh la User.class
        this.errorEntity = Users.class;
    }

}
