package org.example.jr.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        return encoder.encode(password);
    } //加密

    public static boolean matches(String rawPassword, String encodedPassword) {//验证密文
        return encoder.matches(rawPassword, encodedPassword);
    }

}