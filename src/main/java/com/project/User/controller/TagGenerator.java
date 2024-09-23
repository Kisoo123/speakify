package com.project.User.controller;

import java.util.Random;

public class TagGenerator {

    public static String generateRandomTag() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder tag = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            tag.append(chars.charAt(random.nextInt(chars.length())));
        }

        return tag.toString();
    }
}
