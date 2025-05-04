package com.example.comlazadserver.utils;

import com.example.comlazadserver.exceptional.BussinessException;

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
     * Validates a password string against complexity rules and returns a list of error messages.
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
     * @return A list of error messages.  If the password is valid, an empty list is returned.
     */
    public static void getErrorMessages(String password) {

        if (password == null || password.length() < 8) {
            throw new BussinessException("Mật khẩu phải có ít nhất 8 ký tự.");
        }
        if (password != null && !password.matches(".*[0-9].*")) {
            throw new BussinessException("Mật khẩu phải chứa ít nhất một chữ số.");
        }
        if (password != null && !password.matches(".*[a-z].*")) {
            throw new BussinessException("Mật khẩu phải chứa ít nhất một chữ cái thường.");
        }
        if (password != null && !password.matches(".*[A-Z].*")) {
            throw new BussinessException("Mật khẩu phải chứa ít nhất một chữ cái hoa.");
        }
        if (password != null && !password.matches(".*[@#$%^&+=!].*")) {
            throw new BussinessException("Mật khẩu phải chứa ít nhất một ký tự đặc biệt .");
        }
        if (password != null && password.contains(" ")) {
            throw new BussinessException("Mật khẩu không được chứa khoảng trắng.");
        }
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
     * @throws IllegalArgumentException if the password does not meet the criteria
     */
    public static void validate(String password) {
        if (!isValid(password)) {
            getErrorMessages(password);
        }
    }

    /**
     * Validates a password and returns error message
     * @param password
     * @return
     */

}

