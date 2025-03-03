package isika.p3.amappli.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import isika.p3.amappli.validation.validator.FieldMatchValidator;

@Constraint(validatedBy = FieldMatchValidator.class) // Specifies the validator class
@Target({ElementType.TYPE}) // The annotation is applied at the class level
@Retention(RetentionPolicy.RUNTIME) // The annotation will be available at runtime
public @interface FieldMatch {

    String message() default "Fields do not match"; // Default error message

    Class<?>[] groups() default {}; // This is the required groups parameter

    Class<? extends Payload>[] payload() default {}; // Default payload (for additional data)
    
    String first();  // Name of the first field to compare
    String second(); // Name of the second field to compare

}
