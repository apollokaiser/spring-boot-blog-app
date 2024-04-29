package com.training.blog.Utils;

import lombok.Getter;

@Getter
public enum EmailTemplateEngine {
    ACTIVATION_ACCOUNT("activation_account"),
    RESET_PASSWORD("reset_password");
    private String name;
    EmailTemplateEngine( String name){
        this.name = name;
    }
}
