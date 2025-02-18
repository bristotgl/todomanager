package io.github.siegjor.todomanager.exception;

public record FieldError(
        String field,
        String message,
        String code
) {
}
