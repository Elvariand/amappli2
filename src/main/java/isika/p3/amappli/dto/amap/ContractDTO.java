package isika.p3.amappli.dto.amap;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import isika.p3.amappli.entities.tenancy.PickUpSchedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractDTO {
	private Long id;
    private String contractName;
    private String contractType;
    private String contractDescription;
    private String contractWeight;
    private BigDecimal contractPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private MultipartFile image; // Champ pour l'image
    private String deliveryRecurrence; // Attribut ajouté
    private PickUpSchedule pickUpSchedule;
    private Integer quantity;
    private Long userId; // ID du producteur
    private boolean shoppable; // Indique si le contrat est disponible ou non
    private String tenancyAlias;
}
