package com.example.demo.constant;

import java.util.Random;

public class Helper {
    public static String generatePassword(int length, boolean specialChars, boolean extraSpecialChars) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        String charsUp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "123456789";
        String special = "";
        String extraSpecial = "";

        if (specialChars) {
            special = "!@#$%^&*()";
            chars += special;
        }
        if (extraSpecialChars) {
            extraSpecial = "-_ []{}<>~`+=,.;:/?|";
            chars += extraSpecial;
        }

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Add 2 uppercase letters
        for (int i = 0; i < 2; i++) {
            //password.append(charsUp.charAt(random.nextInt(charsUp.length())));
        }

        // Add 8 regular or special characters
        for (int i = 0; i < 8; i++) {
            //password.append(chars.charAt(random.nextInt(chars.length())));
        }

        // Add 2 numeric characters
        for (int i = 0; i < length; i++) {
            password.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return password.toString();
    }
}
