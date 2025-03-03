package isika.p3.amappli.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import isika.p3.amappli.validation.validator.PasswordMatchesValidator;

@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {
    String message() default "Les mots de passe ne correspondent pas.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
