package isika.p3.amappli.validation.validator;

import org.springframework.beans.BeanWrapperImpl;

import isika.p3.amappli.validation.annotation.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object>{

    private String firstFieldName;  // Name of the first field to compare
    private String secondFieldName; // Name of the second field to compare
    private String message;         // Error message to be displayed if fields don't match

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        // Extract the field names and error message from the annotation
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are handled by other validations like @NotNull
        }

        // Use BeanWrapperImpl to get the values of the fields from the model object
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);

        // Get the values of the first and second fields
        Object firstValue = beanWrapper.getPropertyValue(firstFieldName);
        Object secondValue = beanWrapper.getPropertyValue(secondFieldName);

        // Check if the two fields are equal or both null
        boolean isValid = (firstValue == null && secondValue == null)
                || (firstValue != null && firstValue.equals(secondValue));

        // If the fields do not match, add the custom error message to the validation context
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                   .addPropertyNode(secondFieldName)
                   .addConstraintViolation();  // Add the violation to the context
        }

        return isValid;  // Return true if fields match, false if they do not
    }
}
