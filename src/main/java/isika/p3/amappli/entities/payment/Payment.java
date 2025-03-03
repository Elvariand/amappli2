package isika.p3.amappli.entities.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import isika.p3.amappli.entities.order.Order;
import isika.p3.amappli.entities.tenancy.ServiceSubscription;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private LocalDateTime paymentDate;

    private BigDecimal paymentAmount;
    
    // Optional Relationship
    @ManyToOne
    @JoinColumn( name = "homePageContentId", nullable = true)
    private ServiceSubscription serviceSubscription;
    
    @ManyToOne
    @JoinColumn(name = "orderId", nullable = true)
    private Order order;
}
