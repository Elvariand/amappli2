package isika.p3.amappli.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import isika.p3.amappli.dto.amap.UserDTO;
import isika.p3.amappli.validation.annotation.PasswordMatches;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDTO> {

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
        return userDTO.getPassword() != null && userDTO.getPassword().equals(userDTO.getConfirmPassword());
    }
}
