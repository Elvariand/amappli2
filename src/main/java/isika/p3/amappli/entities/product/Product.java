package isika.p3.amappli.entities.product;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import isika.p3.amappli.entities.contract.DeliveryRecurrence;
import isika.p3.amappli.entities.order.Shoppable;
import isika.p3.amappli.entities.tenancy.PickUpSchedule;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.User;
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
@Table(name = "products")
public class Product extends Shoppable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    
    @Column(length = 1000)
    private String productDescription;

    @DecimalMin(value = "0.10", message = "Le prix doit être supérieur à 0.")
    private BigDecimal productPrice;

    private Integer productStock;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;

    @Column(name = "image_type")
    private String imageType;

    @Lob
    @Column(name = "image_data", columnDefinition = "LONGTEXT")
    private String imageData;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fabricationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    
    @Embedded
    private PickUpSchedule pickUpSchedule;
	
	@Enumerated(EnumType.STRING)
	private DeliveryRecurrence deliveryRecurrence;
	
    @ManyToOne
    @JoinColumn(name = "addressId", nullable = true)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "tenancyId", nullable = false)
    private Tenancy tenancy;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name="userId", nullable = true)
    private User user;


    @Column(nullable = false)
    @Builder.Default
    private boolean shoppable = true;


	@Override
	public int getStock() {
		return productStock != null ? productStock : 0;
	}


	@Override
	public double getPrice() {
		return productPrice != null ? productPrice.doubleValue() : 0.0;
	}


	@Override
	public String getInfo() {
		return productName != null ? productName : "N/A";
	}


	@Override
	public String getImage() {
	    if (imageData != null && imageType != null) {
	        return "data:" + imageType + ";base64," + imageData;
	    }
	    return "/resources/images/default-image.png"; // Chemin de l'image par défaut si aucune donnée n'est disponible
	}
}
