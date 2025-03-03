package isika.p3.amappli.dto.amap;

import java.math.BigDecimal;
import java.util.List;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.CompanyDetails;
import isika.p3.amappli.entities.user.ContactInfo;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {
	
	private Long userId;

    @Size(max = 70, message = "L'email doit faire 70 caractères maximum.")
    @Email(message = "L\'email est invalide.")
    @NotBlank(message = "L\'email est obligatoire.")
    private String email;
	
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
