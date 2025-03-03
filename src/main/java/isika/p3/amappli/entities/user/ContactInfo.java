package isika.p3.amappli.entities.user;

import jakarta.persistence.Column;
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
public class ContactInfo {
	
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contactInfoId;
	
    @NotBlank( message = "Le nom est obligatoire.")
	@Size(max=70, message = "Le nom doit faire 70 caractères maximum.")
    private String name;

    @NotBlank( message = "Le prénom est obligatoire.")
	@Size(max=70, message = "Le prénom doit faire 70 caractères maximum.")
    private String firstName;

    @Pattern(regexp = "^(0|\\+33)[1-9](\\d{8})$", message = "Numéro de téléphone français invalide.")
    private String phoneNumber;

	@OneToOne
	@JoinColumn(name = "userID")
	@EqualsAndHashCode.Exclude
	private User user;
	
}
