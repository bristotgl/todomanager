package io.github.siegjor.todomanager.customer;

import io.github.siegjor.todomanager.utils.MessageKeys;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(
        @NotNull(message = MessageKeys.USERNAME_REQUIRED)
        @Size(message = MessageKeys.USERNAME_SIZE, min = 3, max = 50)
        String username,

        @NotNull(message = MessageKeys.EMAIL_REQUIRED)
        @Size(message = MessageKeys.EMAIL_SIZE, min = 3, max = 40)
        @Email(message = MessageKeys.EMAIL_INVALID)
        String email
) {
}
