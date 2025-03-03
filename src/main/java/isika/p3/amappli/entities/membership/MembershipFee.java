package isika.p3.amappli.entities.membership;

import java.time.LocalDate;

import isika.p3.amappli.entities.order.Shoppable;
import isika.p3.amappli.entities.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipFee extends Shoppable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Long id;
	@Getter @Setter
	private String info;
	@Getter @Setter
	private double price;
	@Getter @Setter
	private int stock;
	@Getter @Setter
	private String image;
	@Getter @Setter
	private LocalDate dateBeginning;
	@Getter @Setter
	private LocalDate dateEnd;

	@Getter @Setter
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;
	
}
