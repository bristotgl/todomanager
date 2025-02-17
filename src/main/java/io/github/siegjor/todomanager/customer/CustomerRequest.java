package io.github.siegjor.todomanager.customer;

import io.github.siegjor.todomanager.utils.MessageKeys;
import io.github.siegjor.todomanager.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        @NotNull(message = MessageKeys.USERNAME_REQUIRED)
        @Size(message = MessageKeys.USERNAME_SIZE, min = 3, max = 50)
        String username,

        @NotNull(message = MessageKeys.EMAIL_REQUIRED)
        @Size(message = MessageKeys.EMAIL_SIZE, min = 3, max = 40)
        @Email(message = MessageKeys.EMAIL_INVALID)
        String email,

        @NotNull(message = MessageKeys.PASSWORD_REQUIRED)
        @Size(message = MessageKeys.PASSWORD_SIZE, min = 8, max = 30)
        @ValidPassword
        String password
) {
}
