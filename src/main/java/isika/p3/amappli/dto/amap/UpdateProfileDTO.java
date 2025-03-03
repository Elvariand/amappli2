package isika.p3.amappli.dto.amap;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.ContactInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UpdateProfileDTO {
	
	   @Valid
	    private Address address;

	    @Valid
	    private ContactInfo contactInfo;
	    
	    @NotBlank( message = "L''email est obligatoire.")
		@Size(max=70, message = "L''email doit faire 70 caractères maximum.")
	    @Email( message = "L''email est invalide.")
	    private String email;

}
