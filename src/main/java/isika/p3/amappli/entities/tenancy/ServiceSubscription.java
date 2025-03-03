package isika.p3.amappli.entities.tenancy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import isika.p3.amappli.entities.payment.BillingFrequency;
import isika.p3.amappli.entities.payment.Payment;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceSubscriptionId;

    @Enumerated(EnumType.STRING)
    private BillingFrequency billingFrequency;

    private BigDecimal amountDuePerBill;

    private LocalDateTime startDate;

    private LocalDateTime nextBillingDate;

    private Boolean isActive;

    @OneToOne
    @JoinColumn( name = "tenancyId")
    private Tenancy tenancy;

    @OneToMany( targetEntity = Payment.class, mappedBy = "serviceSubscription")
    @Builder.Default
    private Set<Payment> payments = new HashSet<Payment>();
    
}
