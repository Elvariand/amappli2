package isika.p3.amappli.dto.amap;

import java.math.BigDecimal;
import java.util.List;

import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.CompanyDetails;
import isika.p3.amappli.entities.user.ContactInfo;
import isika.p3.amappli.validation.annotation.PasswordMatches;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
public class UserDTO {
	
	

	private Long userId;

    @NotBlank(message = "L'email est obligatoire.")
    @Size(max = 70, message = "L'email doit faire 70 caractères maximum.")
    @Email(message = "L'email est invalide.")
    private String email;

	 @NotBlank( message = "Champ obligatoire")
	    @Pattern(
	    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
	    message = "Le mot de passe doit avoir au moins un chiffre, une minuscule, une majuscule, un caractère spécial, pas d'espaces et au moins huit caractères de long."
	    )
    private String password;

	 @NotBlank(message = "Champ obligatoire.")
	    @Pattern(
	    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
	    message = "Le mot de passe doit avoir au moins un chiffre, une minuscule, une majuscule, un caractère spécial, pas d'espaces et au moins huit caractères de long."
	    )
    private String confirmPassword;

    private BigDecimal creditBalance;

    @Column(nullable = false)
    private List<Long> roles;

    @Valid
    private Address address;

    @Valid
    private ContactInfo contactInfo;

    @Valid
    private CompanyDetails companyDetails;
}
