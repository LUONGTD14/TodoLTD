package com.example.todoltd.utils;

public class CheckValidates {
    public CheckValidates() {
    }

    //check is validate strong password
    public boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }

    //check is validate email format
    public boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    //check password confirm
    public boolean isValidConfirmPw(String pw1, String pw2) {
        return pw1.equals(pw2);
    }
}
