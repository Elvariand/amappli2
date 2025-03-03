package isika.p3.amappli.dto.amap;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import isika.p3.amappli.entities.tenancy.PickUpSchedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

	private Long id;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Integer productStock;
    private LocalDate dateCreation;
    private LocalDate fabricationDate;
    private LocalDate expirationDate;
    private MultipartFile image;
    private PickUpSchedule pickUpSchedule;
    private String deliveryRecurrence; 
    private Long userId; // ID du producteur
    private boolean shoppable; // Indique si le contrat est disponible ou non
    private String tenancyAlias;
}
