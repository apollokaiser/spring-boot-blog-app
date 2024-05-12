package com.training.blog.Utils;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class RandomCodeGenerator {
    public String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
    public String generateUUID() {
        String UUIDCode = UUID.randomUUID().toString();
        String code = UUIDCode + "-" + System.currentTimeMillis();
        return code;
    }
}
