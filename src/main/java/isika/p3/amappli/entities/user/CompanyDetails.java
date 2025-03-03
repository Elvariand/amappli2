package isika.p3.amappli.entities.user;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDetails {
	
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long companyDetailsId;

	@Column(nullable = true)
    private String companyName;
	
	@Column(nullable = true)
	@Pattern(regexp = "^(\\d{14}|\\d{0})$", message = "Numéro de SIRET invalide. (14 chiffres)")
    private String siretNumber;
	
	@OneToOne
	@JoinColumn(name = "userID")
	private User user;
}
