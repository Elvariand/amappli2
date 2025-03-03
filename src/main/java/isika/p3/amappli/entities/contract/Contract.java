package isika.p3.amappli.entities.contract;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import isika.p3.amappli.entities.tenancy.PickUpSchedule;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.User;

import isika.p3.amappli.entities.order.Shoppable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "contracts")
public class Contract extends Shoppable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String contractName;

	@Enumerated(EnumType.STRING)
	public ContractType contractType;

	@Column(length = 1000)
	private String contractDescription;

	@Enumerated(EnumType.STRING)
	private ContractWeight contractWeight;

	@DecimalMin(value = "0.10", message = "Le prix doit être supérieur à 0.")
	private BigDecimal contractPrice;

	@Column(name = "image_type")
	private String imageType;

	@Lob
	@Column(name = "image_data", columnDefinition = "LONGTEXT")
	private String imageData; // Stockera les données de l'image encodées en Base64

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateCreation;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	@Enumerated(EnumType.STRING)
	private DeliveryRecurrence deliveryRecurrence;
	
    @Embedded
    private PickUpSchedule pickUpSchedule;
	
	private Integer quantity;
	
    @ManyToOne
    @JoinColumn(name = "addressId", nullable = true)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "tenancy_id", nullable = false)
    private Tenancy tenancy;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name="userId", nullable = true)
    private User user;


    @Column(nullable = false)
    @Builder.Default
    private boolean shoppable = true;


    // Implémentation des méthodes abstraites de Shoppable
    @Override
    public int getStock() {
        return quantity != null ? quantity : 0;
    }

    @Override
    public double getPrice() {
        return contractPrice != null ? contractPrice.doubleValue() : 0.0;
    }

    @Override
    public String getInfo() {
        return contractName != null ? contractName : "N/A";
    }

    @Override
    public String getImage() {
        if (imageData != null && imageType != null) {
            return "data:" + imageType + ";base64," + imageData;
        }
        return "/resources/images/default-image.png"; // Chemin de l'image par défaut si aucune donnée n'est disponible
    }
   
}