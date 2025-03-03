package isika.p3.amappli.entities.order;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import isika.p3.amappli.entities.payment.Payment;
import isika.p3.amappli.entities.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Long orderId;

	@Getter @Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	@Getter @Setter
	private double totalAmount;

	@Getter @Setter
	private Integer installmentCount;

	@Getter @Setter
	private double installmentAmount;
	
	@Getter @Setter
	private LocalDate orderDate;
	
	@Getter @Setter
	private OrderStatus orderStatus;
	
	@Getter @Setter
	@Builder.Default
	private boolean orderPaid = false;
	
	@Getter @Setter
	@Builder.Default
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Payment> payments = new ArrayList<Payment>();

	@Getter @Setter
	@Builder.Default
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	public double getOutstandingAmount() {
		return totalAmount - (installmentAmount * installmentCount);
	}

	public boolean isPaidInFull() {
		if (this.getOutstandingAmount() == 0) {
			return true;
		} else
			return false;
	}
	
}
