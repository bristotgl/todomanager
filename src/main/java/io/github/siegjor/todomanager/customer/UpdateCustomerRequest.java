package io.github.siegjor.todomanager.customer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(
        @Size(message = "{validation.username.size}")
        String username,

        @Size(message = "{validation.email.size}")
        String email
) {
}
