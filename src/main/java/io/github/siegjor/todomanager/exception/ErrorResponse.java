package io.github.siegjor.todomanager.exception;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public record ErrorResponse(
        String message,
        int status,
        String code,
        OffsetDateTime timestamp,
        List<FieldError> errors,
        String path
) {
    public ErrorResponse(String message, int status, String code, String path) {
        this(message, status, code, OffsetDateTime.now(), new ArrayList<>(), path);
    }
}
