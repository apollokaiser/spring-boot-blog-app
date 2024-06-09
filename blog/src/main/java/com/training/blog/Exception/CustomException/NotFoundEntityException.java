package com.training.blog.Exception.CustomException;

import com.training.blog.Entities.Users;

public class NotFoundEntityException extends RuntimeException {

    private final Class<?> errorEntity;
    /**
     *
     * @param errorEntity The entity that was not found
     * @param message The error message
     */
    public NotFoundEntityException(String message, Class<?> errorEntity) {
        super(message);
        this.errorEntity = errorEntity;
    }
    public NotFoundEntityException(String message) {
        super(message);
        // Default is User.class
        this.errorEntity = Users.class;
    }
    public Class<?> getErrorEntity() {
        return errorEntity;
    }

}
