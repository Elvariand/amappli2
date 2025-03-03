package isika.p3.amappli.entities.workshop;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import isika.p3.amappli.entities.order.Shoppable;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "workshops")
public class Workshop extends Shoppable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String workshopName;

	private String workshopDescription;

	private LocalDateTime workshopDateTime;

	@DecimalMin(value = "0.10", message = "Le prix doit être supérieur à 0.")
	private BigDecimal workshopPrice;

	private Integer workshopDuration;

	private Integer minimumParticipants;

	private Integer maximumParticipants;
	
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

	@Column(name = "image_type")
	private String imageType;

	@Lob
	@Column(name = "image_data", columnDefinition = "LONGTEXT")
	private String imageData;
	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;

	@Override
	public int getStock() {
		return maximumParticipants != null ? maximumParticipants : 0;
	}

	@Override
	public double getPrice() {
		return workshopPrice != null ? workshopPrice.doubleValue() : 0.0;
	}

	@Override
	public String getInfo() {
		return workshopName != null ? workshopName : "N/A";
		
	}

	@Override
	public String getImage() {
	    if (imageData != null && imageType != null) {
	        return "data:" + imageType + ";base64," + imageData;
	    }
	    return "/resources/images/default-image.png";
	}

}
