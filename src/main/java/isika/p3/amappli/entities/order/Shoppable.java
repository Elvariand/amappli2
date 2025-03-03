package isika.p3.amappli.entities.order;

import java.util.ArrayList;
import java.util.List;

import isika.p3.amappli.entities.contract.Contract;
import isika.p3.amappli.entities.membership.MembershipFee;
import isika.p3.amappli.entities.product.Product;
import isika.p3.amappli.entities.workshop.Workshop;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Shoppable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
	@Getter @Setter
	@OneToMany(mappedBy = "shoppable", cascade = CascadeType.ALL)
	private List<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>();
//	@Getter @Setter
//	@OneToMany(mappedBy = "shoppable")
//	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	
	public abstract int getStock();
	public abstract double getPrice();
	public abstract String getInfo();
	public abstract String getImage();
	
	public boolean isMembershipFee() {
	    return this instanceof MembershipFee;
	}
	
	public String getShoppableType() {
	    if (this instanceof Contract) {
	        return "contract";
	    } else if (this instanceof Product) {
	        return "product";
	    } else if (this instanceof Workshop) {
	        return "workshop";
	    } else if (this instanceof MembershipFee) {
	        return "membershipFee";
	    }
	    return "unknown";
	}


	
}
