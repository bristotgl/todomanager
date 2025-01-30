package io.github.siegjor.todomanager.customer;

import io.github.siegjor.todomanager.MessageConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        @NotNull(message = "{validation.username.required}")
        @Size(message = "{validation.username.size}")
        String username,

        @NotNull(message = "{validation.email.required}")
        @Size(message = "{validation.email.size}")
        String email,

        @NotNull(message = "{validation.password.required}")
        @Size(message = "{validation.password.size}")
        String password
) {
}
