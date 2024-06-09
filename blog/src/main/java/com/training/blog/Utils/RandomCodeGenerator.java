package com.training.blog.Utils;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Lazy
public class RandomCodeGenerator {
    public String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
