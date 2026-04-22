package org.smartSeatAllocation.util;


public class Helper {

    public static boolean isValidEmail(String email) {
        if (isBlank(email)) {
            return false;
        }
        String normalizedEmail = email.trim();
        return normalizedEmail.contains("@") && normalizedEmail.indexOf('@') < normalizedEmail.lastIndexOf('.');
    }

    public static boolean isValidPassword(String password) {
        return !isBlank(password) && password.trim().length() >= 6;
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
