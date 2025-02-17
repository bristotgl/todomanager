package io.github.siegjor.todomanager.validation;

import io.github.siegjor.todomanager.utils.MessageKeys;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default MessageKeys.PASSWORD_COMPOSITION;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
