package io.github.siegjor.todomanager.validation;

import io.github.siegjor.todomanager.utils.MessageKeys;
import io.github.siegjor.todomanager.utils.MessageUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_PATTERN = "^(?!\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,30}$\n";

    private final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    private final MessageUtil messageUtil;

    public PasswordValidator(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password.isBlank() || !pattern.matcher(password).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageUtil.getMessage(MessageKeys.PASSWORD_COMPOSITION))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
