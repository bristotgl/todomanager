package io.github.siegjor.todomanager.utils;

public final class MessageKeys {
    private MessageKeys() {
    }

    // Error Messages
    public static final String USERNAME_TAKEN = "error.username.taken";
    public static final String EMAIL_REGISTERED = "error.email.registered";
    public static final String CUSTOMER_NOT_FOUND = "error.customer.not_found";

    // Validation Messages
    public static final String USERNAME_REQUIRED = "validation.username.required";
    public static final String USERNAME_SIZE = "validation.username.size";
    public static final String EMAIL_INVALID = "validation.email.invalid";
    public static final String EMAIL_REQUIRED = "validation.email.required";
    public static final String EMAIL_SIZE = "validation.email.size";
    public static final String PASSWORD_COMPOSITION = "validation.password.composition";
    public static final String PASSWORD_REQUIRED = "validation.password.required";
    public static final String PASSWORD_SIZE = "validation.password.size";
}
