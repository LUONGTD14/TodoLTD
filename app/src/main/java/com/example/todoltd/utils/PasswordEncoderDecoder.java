package com.example.todoltd.utils;

import android.util.Base64;

public class PasswordEncoderDecoder {

    public PasswordEncoderDecoder(){}
    //decode pasword
    public static String encodePassword(String password) {
        byte[] bytes = password.getBytes();
        String encodedPassword = Base64.encodeToString(bytes, Base64.DEFAULT);
        return encodedPassword;
    }

    //encode password
    public static String decodePassword(String encodedPassword) {
        byte[] bytes = Base64.decode(encodedPassword, Base64.DEFAULT);
        String decodedPassword = new String(bytes);
        return decodedPassword;
    }
}
