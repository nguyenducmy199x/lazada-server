package com.example.comlazadserver.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    /**
     * Validates a password string against complexity rules.
     *
     * The password must:
     * - Be at least 8 characters long
     * - Contain at least one digit
     * - Contain at least one lowercase letter
     * - Contain at least one uppercase letter
     * - Contain at least one special character from the set @#$%^&+=!
     * - Not contain any whitespace
     *
     * @param password The password string to validate.
     * @return true if the password is valid, false otherwise.
     */
    public static boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Validates a password string against complexity rules and throws an exception if it doesn't
     * meet the criteria
     *
     * The password must:
     * - Be at least 8 characters long
     * - Contain at least one digit
     * - Contain at least one lowercase letter
     * - Contain at least one uppercase letter
     * - Contain at least one special character from the set @#$%^&+=!
     * - Not contain any whitespace
     *
     * @param password The password to validate
     * @param errorMessage The message to use in the exception if the password is not valid
     * @throws IllegalArgumentException if the password does not meet the criteria
     */
    public static void validate(String password, String errorMessage) {
        if (!isValid(password)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}