package isika.p3.amappli.entities.user;

import org.springframework.lang.Nullable;

import isika.p3.amappli.entities.tenancy.Tenancy;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	
    @Size(max=70, message = "Le complément d'adresse doit faire 70 caractères maximum.")
    private String line1;

    @NotBlank( message = "L'adresse est obligatoire.")
	@Size(max=100, message = "L'adresse doit faire 100 caractères maximum.")
    private String line2;

    @Pattern( regexp = "^((0[1-9])|([1-9][0-9]))[0-9]{3}$", message = "Veuillez saisir un code postal français valide.")
    private String postCode;

    @NotBlank( message = "La ville est obligatoire.")
	@Size(max=70, message = "La ville doit faire 70 caractères maximum.")
    private String city;
    
	@OneToOne
	@JoinColumn(name = "userID", nullable = true)
	@Nullable
	@EqualsAndHashCode.Exclude
	private User user;
	
	@OneToOne
	@JoinColumn(name = "tenancyID", nullable = true)
	@Nullable
	@EqualsAndHashCode.Exclude
	private Tenancy tenancy;

	public Address(@Size(max = 70, message = "Le complément d'adresse doit faire 70 caractères maximum.") String line1,
			@NotBlank(message = "L'adresse est obligatoire.") @Size(max = 100, message = "L'adresse doit faire 100 caractères maximum.") String line2,
			@Pattern(regexp = "^((0[1-9])|([1-9][0-9]))[0-9]{3}$", message = "Veuillez saisir un code postal français valide.") String postCode,
			@NotBlank(message = "La ville est obligatoire.") @Size(max = 70, message = "La ville doit faire 70 caractères maximum.") String city) {
		this.line1 = line1;
		this.line2 = line2;
		this.postCode = postCode;
		this.city = city;
	}
    
    
}
