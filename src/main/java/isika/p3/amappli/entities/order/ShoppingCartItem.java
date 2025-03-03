package isika.p3.amappli.entities.order;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
	private Long shoppingItemId;
	
	@Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoppingCartId", nullable = false)
	private ShoppingCart shoppingCart;
	
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "shoppableId", nullable = false)
	private Shoppable shoppable;
	
	@Getter @Setter
	private int quantity;

	public double getTotalPrice() {
		return shoppable.getPrice() * quantity;
	}

}
